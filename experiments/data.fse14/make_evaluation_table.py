#!/usr/bin/python3

import argparse
import numpy
import os.path
import re
import sys

from contextlib import closing
from scipy import stats
from xml.etree import ElementTree

TOOLS = "brutefuzz randoop jcute jpf-mixed pex jpf-coral jpf-gradient".split()
VARIATIONS = [ "jpf-gradient-" + v for v in "tabu=0-0 tabu=1-5 nobisect iter=10 iter=30 iter=75 iter=300 nb=3 nb=25 nb=50 nb=100".split() ]
TOOLS.extend(VARIATIONS)
COMBINATIONS = [ "jpf-gradient+" + t for t in "pex jpf-coral".split() ]
TOOLS.extend(COMBINATIONS)

BENCHMARKS = "coral early ej_hash optimization".split()
PROGRAMS = "dart power raytrace sine statcalc tcas tsafe".split()

BENCHMARK_METHOD_BLACKLIST = re.compile(r"<init>|<clinit>|main|theta|hashCode")
TOOL_LABELS = {"brutefuzz": r"\brutefuzz",
               "randoop": r"\randoop",
               "jcute": r"\jcute",
               "pex": r"\pex",
               "jpf-coral": r"\spfcoral",
               "jpf-mixed": r"\spfmixed",
               "jpf-gradient": r"\cw",
               "jpf-gradient-tabu=0-0": r"Tabu disabled ($\tabuIterations=0$)",
               "jpf-gradient-tabu=1-5": r"Tabu extended ($\tabuIterations\geq5$)",
               "jpf-gradient-nobisect": r"Bisection disabled",
               "jpf-gradient-iter=10": r"10 steps ($\iterationLimit=10$)",
               "jpf-gradient-iter=30": r"30 steps ($\iterationLimit=30$)",
               "jpf-gradient-iter=75": r"75 steps ($\iterationLimit=75$)",
               "jpf-gradient-iter=300": r"300 steps ($\iterationLimit=300$)",
               "jpf-gradient-nb=3": r"3 neighbors ($\neighborCount=3$)",
               "jpf-gradient-nb=25": r"25 neighbors ($\neighborCount=25$)",
               "jpf-gradient-nb=50": r"50 neighbors ($\neighborCount=50$)",
               "jpf-gradient-nb=100": r"100 neighbors ($\neighborCount=100$)",
}


PROGRAM_LABELS = {
    "avg": r"Avg.",
    "coral": r"\coralbench",
    "dart": r"\dartbench",
    "early": r"\early",
    "ej_hash": r"\hash",
    "optimization": r"\optimization",
    "apollo": r"\apollo",
    "power": r"\power",
    "raytrace": r"\raytrace",
    "sine": r"\sine",
    "statcalc": r"\statcalc",
    "tcas": r"\tcas",
    "tsafe": r"\tsafe"
}

PROGRAM_WEIGHTS = {
    "coral": 335,
    "dart": 18,
    "early": 17,
    "ej_hash": 54,
    "optimization": 48,
    "apollo": 1558,
    "power": 31,
    "raytrace": 304,
    "sine": 289,
    "statcalc": 113,
    "tcas": 157,
    "tsafe": 88,
}

NA = -2


def benchmark_coverage(report_root, benchmark):
    solved, total = 0, 0
    benchmark_methods = ".//group[@name='{}']//method".format(benchmark)
    for method in report_root.findall(benchmark_methods):
        name = method.get("name")
        if BENCHMARK_METHOD_BLACKLIST.match(name):
            continue
        total += 1
        if method.find('counter[@type="INSTRUCTION"]').get('missed') == '0':
            solved += 1
    return solved / total if total > 0 else 0


def program_coverage(report_root, program):
    branch_counter = ".//group[@name='{}']/counter[@type='BRANCH']"
    program_branch_counter = report_root.find(branch_counter.format(program))
    if program_branch_counter is None:
        return 0
    covered = int(program_branch_counter.get("covered"))
    missed = int(program_branch_counter.get("missed"))
    return covered / (covered + missed)


def format_program(program):
    return PROGRAM_LABELS.get(program, r"\textit{" + program + "}") if program else ""

def format_tool(tool):
    return TOOL_LABELS.get(tool, tool) + " & "


def format_entry_as_percent(entry):
    if entry == NA:
        return ""
    return str(int(round(entry * 100)))

def format_entry_as_percent_deviation(entry):
    if entry == NA:
        return ""
    entry = round(entry * 100, 2)
    if entry == 0:
        return "--"
    return str(entry)

def format_entry_as_fraction(entry):
    if entry == NA:
        return ""
    entry = round(entry, 2)
    if entry == 1:
        return r"$\cdot$"
    return str(entry)

def format_entry_as_mannwhitney_p_value(entry):
    if isinstance(entry, int):
        return ""
    return "{} ({})".format(round(entry[1] * 2, 3), int(entry[0]))


def format_table(data, format_entry_func):
    columns = ["avg", ""] + BENCHMARKS + [""] + PROGRAMS

    rows = [r"\toprule"]
    header = r"\textit{Tool} & " + " & ".join(map(format_program, columns))
    rows.append(header + r" \\")
    rows.append(r"\midrule")
    for tool in TOOLS:  # Iterate in the right order
        if tool not in data:
            continue
        tool_data = data[tool]
        entries = [ tool_data.get(c, NA) for c in columns ]
        row = format_tool(tool) + " & ".join(map(format_entry_func, entries))
        rows.append(row + r" \\")
    rows.append(r"\bottomrule")
    return "\n".join(rows)


def make_argument_parser():
    p = argparse.ArgumentParser(description="Concolic Walk evaluation table generator")
    p.add_argument("report_dirs", metavar="XML_REPORT_DIR", type=str, nargs="+")
    return p


def load_data(report_dirs):
    data = {}
    for tool in TOOLS:
        tool_data = data.setdefault(tool, {})
        for report_dir in report_dirs:

            report = "report-{}.xml".format(tool)
            report_path = os.path.join(report_dir, report)

            if os.path.isfile(report_path):
                tree = ElementTree.parse(report_path)
                root = tree.getroot()
                for b in BENCHMARKS:
                    tool_data.setdefault(b, []).append(benchmark_coverage(root, b))
                for p in PROGRAMS:
                    tool_data.setdefault(p, []).append(program_coverage(root, p))
            else:
                msg = ">>> WARNING: Missing report {}".format(report_path)
                print(msg, file=sys.stderr)

    return data


def add_weighted_average(data):
    # Compute weighted average
    total_weight = sum([ PROGRAM_WEIGHTS[p] for p in BENCHMARKS + PROGRAMS ])
    for tool_data in data.values():
        if not tool_data:
            continue
        weighted_sum = 0
        for program, coverage in tool_data.items():
            weighted_sum += PROGRAM_WEIGHTS[program] * coverage
        tool_data["avg"] = weighted_sum / total_weight


def main(args):
    arguments = make_argument_parser().parse_args(args)
    data = load_data(arguments.report_dirs)

    # Compute average of (tool, program) data sets
    percentages = {}
    for tool, tool_data in data.items():
        for program, data_set in tool_data.items():
            percentages.setdefault(tool, {})[program] = numpy.median(data_set)
    add_weighted_average(percentages)
    print("Percentage Medians")
    print("==================")
    print(format_table(percentages, format_entry_as_percent))
    print()

    deviations = {}
    for tool, tool_data in data.items():
        for program, data_set in tool_data.items():
            deviations.setdefault(tool, {})[program] = numpy.std(data_set)
    add_weighted_average(deviations)
    print("Percentage Deviations")
    print("=====================")
    print(format_table(deviations, format_entry_as_percent_deviation))
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

    combination_differences = {}
    for tool, tool_data in data.items():
        if tool in COMBINATIONS:
            tool_baseline = data.get(tool.split("+", 1)[1])
            for program, data_set in tool_data.items():
                baseline_data_set = tool_baseline[program]
                if data_set == baseline_data_set:
                    continue
                combination_differences.setdefault(tool, {})[program] = stats.mannwhitneyu(data_set, baseline_data_set)
    print("Mann--Whitney Rank Test against Combination Base")
    print("================================================")
    print(format_table(combination_differences, format_entry_as_mannwhitney_p_value))
    print()

    fractions = {}
    baseline_percentages = percentages.get("jpf-gradient", {})
    for tool, tool_data in percentages.items():
        if tool in VARIATIONS:
            for program, percentage in tool_data.items():
                fractions.setdefault(tool, {})[program] = percentage / baseline_percentages.get(program, 1)

    print("Algorithm Variation Baseline Fractions")
    print("======================================")
    print(format_table(fractions, format_entry_as_fraction))
    print()

if __name__ == "__main__":
    main(sys.argv[1:])
