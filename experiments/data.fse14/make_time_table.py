#!/usr/bin/python3

import argparse
import numpy
import os
import re
import sys

from contextlib import closing
from datetime import (datetime, timedelta)
from scipy import stats

from make_evaluation_table import (BENCHMARKS, PROGRAMS,
                                   PROGRAM_WEIGHTS, format_table,
                                   format_entry_as_mannwhitney_p_value)

TIMESTAMP_FORMAT = r"\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d{3}"
TOOL_NAME_FORMAT = r"[\w\d=-]+"
PROGRAM_NAME_FORMAT = r"[\w-]+"
EVENT_FORMAT = r"begin|end"

EVENT_LINE_FORMAT = r"^\s*\[echo\] (?P<timestamp>{}) -- (?P<tool>{})/(?P<program>{}): (?P<event>{}) test generation$"
EVENT_LINE_RE = re.compile(EVENT_LINE_FORMAT.format(TIMESTAMP_FORMAT, TOOL_NAME_FORMAT, PROGRAM_NAME_FORMAT, EVENT_FORMAT))


def load_data(build_logs):
    data = {}
    for build_log in build_logs: 
        if not os.path.isfile(build_log):
            msg = ">>> WARNING: Missing report {}".format(report_path)
            print(msg, file=sys.stderr)
            continue

        with closing(open(build_log, "r")) as logFile:
            lastEvent, lastTool, lastProgram, lastTimestamp = None, None, None, None
            for line in logFile:
                match = EVENT_LINE_RE.match(line)
                if not match:
                    continue

                event = match.group("event")
                tool = match.group("tool")
                program = match.group("program")

                # Cut off milli seconds, parse, add miliseconds using a timedelta object
                major, millis = match.group("timestamp").split(".")
                timestamp = datetime.strptime(major, "%Y-%m-%d %H:%M:%S") + timedelta(milliseconds=int(millis))
            
                if event == "begin":
                    assert lastEvent is None
                    lastEvent, lastTool, lastProgram, lastTimestamp = event, tool, program, timestamp
                else:
                    assert lastEvent == "begin"
                    assert lastTool == tool
                    assert lastProgram == program
                    duration_in_ms = (timestamp - lastTimestamp) / timedelta(milliseconds=1)
                    data.setdefault(tool, {}).setdefault(program, []).append(duration_in_ms)
                    lastEvent = None
    return data

def format_entry_as_seconds(entry):
    if entry == -1:
        return ""
    entry_in_sec = entry / 1000
    if entry_in_sec < 60:
        return r"{}".format(round(entry_in_sec, 1))
    else:
        return r"{}~min.".format(round(entry_in_sec / 60, 1))


def make_argument_parser():
    p = argparse.ArgumentParser(description="Concolic Walk time table generator")
    p.add_argument("build_logs", metavar="BUILD_LOG", type=str, nargs="+")
    return p


def add_average(data):
    for tool_data in data.values():
        tool_data["avg"] = numpy.mean([ tool_data.get(p, -100000) for p in BENCHMARKS + PROGRAMS ])
    

def main(args):
    arguments = make_argument_parser().parse_args(args)
    data = load_data(arguments.build_logs)

    seconds = {}
    for tool, tool_data in data.items():
        for program, data_set in tool_data.items():
            seconds.setdefault(tool, {})[program] = numpy.median(data_set)
    add_average(seconds)
    print("Time Medians")
    print("============")
    print(format_table(seconds, format_entry_as_seconds))
    print()

    deviations = {}
    for tool, tool_data in data.items():
        for program, data_set in tool_data.items():
            deviations.setdefault(tool, {})[program] = numpy.std(data_set)
    add_average(deviations)
    print("Time Deviations")
    print("===============")
    print(format_table(deviations, format_entry_as_seconds))
    print()

    treatment_differences = {}
    gradient_baseline = data.get("jpf-gradient")
    for tool, tool_data in data.items():
        for program, data_set in tool_data.items():
            baseline_data_set = gradient_baseline.get(program)
            if data_set == baseline_data_set:
                continue
            treatment_differences.setdefault(tool, {})[program] = stats.mannwhitneyu(data_set, baseline_data_set)
    print("Mann--Whitney Rank Test against Concolic Walk")
    print("=============================================")
    print(format_table(treatment_differences, format_entry_as_mannwhitney_p_value))
    print()


if __name__ == "__main__":
    main(sys.argv[1:])
