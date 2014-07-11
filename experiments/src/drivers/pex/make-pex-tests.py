#!/usr/bin/python2

from __future__ import print_function

__DESCRIPTION="""Pex TestDetails to jUnit Test Converter"""

import argparse
import collections
import os
import re
import sys
import textwrap

from contextlib import closing


TEST_METHOD_RE = re.compile(r"\[TestMethod\]\n(\[[^\n]+\]\n)*public void (?P<method_name>[a-zA-Z]\w*)\(\)\n\{(?P<method_body>.*)\}\s*", re.DOTALL)


JUNIT_CLASS_TEMPLATE = textwrap.dedent(r'''
    import org.junit.Test;
    {IMPORTS}

    public class {CLASS_NAME} {{
        {TEST_METHODS}
    }}''')

JUNIT_METHOD_TEMPLATE = textwrap.dedent(r'''
    @Test
    public void {METHOD_NAME}() {{
      {METHOD_BODY}
    }}''')


def make_argument_parser():
    argument_parser = argparse.ArgumentParser(description=__DESCRIPTION)
    argument_parser.add_argument("--import", dest="imports",
                                 metavar="TEST_IMPORT", type=str, action="append", default=[],
                                 help="add a package to the list of imports in the generated file. "
                                 "Can be specified multiple times.")
    argument_parser.add_argument("test_details", metavar="PEX_TEST_DETAILS_TXT", type=str)
    argument_parser.add_argument("test_class", metavar="TEST_CLASS_NAME", type=str)
    argument_parser.add_argument("output_dir", metavar="OUTPUT_DIRECTORY", type=str)
    return argument_parser


def indent(text, prefix):
    return "{}{}".format(prefix, text.replace("\n", "\n{}".format(prefix)))


def write_test_class(output_dir, class_name, imports, test_cases):
    class_file_path = os.path.join(output_dir, "{}.java".format(class_name))
    with closing(open(class_file_path, "w")) as class_file:
        imports_block = "\n".join(map(lambda i: "import {};".format(i), imports))
        class_body = indent("\n".join(test_cases), "  ")
        print(JUNIT_CLASS_TEMPLATE.format(IMPORTS=imports_block,
                                          CLASS_NAME=class_name,
                                          TEST_METHODS=class_body),
                                          file=class_file)

def main(args):
    arguments = make_argument_parser().parse_args(args)
    with closing(open(arguments.test_details, "r")) as test_details_file:
        converted_tests = collections.deque()

        # Pex (intentionally?) prints "\n \n" before and after each test method
        chunks = test_details_file.read().replace("\r\n", "\n").split("\n \n")
        test_methods = filter(lambda t: t.startswith("[TestMethod]"), chunks)

        for test_method in test_methods:
            match = TEST_METHOD_RE.match(test_method)
            if not match:
                continue
            method_name = match.group("method_name")
            method_body = match.group("method_body").strip()

            # Remove global namespace qualifier
            method_body = method_body.replace("global::", "")

            # Fix constant names
            method_body = method_body.replace("int.MaxValue", "Integer.MAX_VALUE")
            method_body = method_body.replace("int.MinValue", "Integer.MIN_VALUE")
            method_body = method_body.replace("double.MaxValue", "Double.MAX_VALUE")
            method_body = method_body.replace("double.MinValue", "Double.MIN_VALUE")
            method_body = method_body.replace("double.PositiveInfinity", "Double.POSITIVE_INFINITY")
            method_body = method_body.replace("double.NegativeInfinity", "Double.NEGATIVE_INFINITY")
            method_body = method_body.replace("double.Epsilon", "Double.MIN_NORMAL")
            method_body = method_body.replace("double.NaN", "Double.NaN")

            method_body_lines = []
            for line in method_body.split("\n"):
                # Remove assertions
                if line.lstrip().startswith("Assert"):
                    continue

                # Do not unmangle continued arguments lines
                if line.lstrip()[0].isdigit():
                    method_body_lines.append(line)
                    continue

                # Unmangle the called method's name
                # HACKY: Assumes that the first parenthesis belongs to a method call(!)
                line_parts = line.split("(");
                call_parts = line_parts[0].split(".")
                called_method = call_parts[-1]
                call_parts[-1] = called_method[0].lower() + called_method[1:]
                line_parts[0] = ".".join(call_parts)
                
                method_body_lines.append("(".join(line_parts))

            method_body = "\n".join(method_body_lines)
            converted_tests.append(JUNIT_METHOD_TEMPLATE.format(METHOD_NAME=method_name,
                                                                METHOD_BODY=method_body))
        
        write_test_class(arguments.output_dir,
                         arguments.test_class,
                         arguments.imports,
                         converted_tests)


if __name__ == '__main__':
    main(sys.argv[1:])  # Cut off script name
