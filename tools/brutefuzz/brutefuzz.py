#!/usr/bin/python2

from __future__ import print_function

__DESCRIPTION=r'''
    BruteFuzz generates JUnit tests that execute a static target method
    on uniformly randomized inputs.
    
    For each line of the METHOD_LIST file, such as `C.m(int, double):100`,
    BruteFuzz creates 100 test methods of the form `@Test test1() { C.m(23, 42.0); }`,
    where 23 and 42.0 are uniformly random input values.  The test methods are
    collected in a class named TEST_CLASS_NAME and the `.java` file defining
    the class is written to the OUTPUT_DIRECTORY.
    '''

import argparse
import collections
import os.path
import random
import sys
import textwrap

from contextlib import closing

TESTS_PER_FILE = 500

INTEGER_MIN_VALUE = -(1 << 31)
INTEGER_MAX_VALUE = (1 << 31) - 1
LONG_MIN_VALUE = -(1 << 63)
LONG_MAX_VALUE = (1 << 63) - 1

HEX_DIGITS = "0123456789abcdef"

JUNIT_CLASS_TEMPLATE = textwrap.dedent(r'''
    import org.junit.Test;

    public class {CLASS_NAME} {{
        {TEST_METHODS}
    }}''')

JUNIT_METHOD_TEMPLATE = textwrap.dedent(r'''
    @Test
    public void {METHOD_NAME}() {{
      {METHOD_BODY}
    }}''')


class TargetMethod:
    def __init__(self, name, primitive_args, test_count):
        self.name = name
        self.primitive_args = primitive_args
        self.test_count = test_count
        
    def instantiate_randomized_test(self):
        randomized_args = ", ".join(map(str, map(next_random, self.primitive_args)))
        return "{m!s}({args!s});".format(m=self.name, args=randomized_args)
    

def parse_method_list(method_list_file):
    target_methods = []
    for line in method_list_file:
        if line.startswith("#"):
            continue
        line, test_count = line.split(":")
        arg_start = line.find("(")
        method = line[:arg_start]
        # Copy the argument list. Otherwise the map object will be empty
        # after one round of randomization.
        args = list(map(str.strip, line[arg_start+1:-1].split(",")))
        target_methods.append(TargetMethod(method.strip(), args, int(test_count)))
    return target_methods


def random_float(mantissa_bits, exponent_bits):
    # Simply scaling the value range with a random factor between 0 and 1
    # does not work because small factors will be normalized away.
    # Hence, we generate a random binary representation and parse it as
    # floating point number.
    sign = random.choice(["", "-"])
    # NOTE: Dividing the bits by 4 will swallow (up to) the 3 least significant bits
    mantissa = "{}.{}".format(random.choice(["0", "1"]),
                              "".join([random.choice(HEX_DIGITS) for _ in range(int(mantissa_bits/4))]))
    min_exponent = -(1 << (exponent_bits - 1)) + 2
    max_exponent = 1 << (exponent_bits - 1) - 1
    exponent = random.randint(min_exponent, max_exponent)
    return float.fromhex("{s}0x{m}P{e}".format(s=sign, m=mantissa, e=exponent))
    

def next_random(type_):
    if type_ == "int":
        return str(random.randint(INTEGER_MIN_VALUE, INTEGER_MAX_VALUE))
    elif type_ == "double":
        return str(random_float(53, 10))
    elif type_ == "float":
        return "{}f".format(random_float(23, 8))
    elif type_ == "boolean":
        return random.choice(["true", "false"]);
    elif type_ == "long":
        return "{}L".format(random.randint(LONG_MIN_VALUE, LONG_MAX_VALUE))
    else:
        raise ValueError("Unknown type_ {}".format(type_))


def make_argument_parser():
    argument_parser = argparse.ArgumentParser(description=__DESCRIPTION)
    argument_parser.add_argument("test_class", metavar="TEST_CLASS_NAME", type=str,
                                 help="name of the generated test class")
    argument_parser.add_argument("method_list", metavar="METHOD_LIST", type=str,
                                 help="file that lists the methods under test")
    argument_parser.add_argument("output_dir", metavar="OUTPUT_DIRECTORY", type=str,
                                 help="directory into which to write the test classes")
    return argument_parser


def indent(text, prefix):
    return "{}{}".format(prefix, text.replace("\n", "\n{}".format(prefix)))

def write_test_class(output_dir, class_name, test_cases):
    class_file_path = os.path.join(output_dir, "{}.java".format(class_name))
    with closing(open(class_file_path, "w")) as class_file:
        class_body = indent("\n".join(test_cases), "  ")
        print(JUNIT_CLASS_TEMPLATE.format(CLASS_NAME=class_name,
                                          TEST_METHODS=class_body),
                                          file=class_file)


def main(args):
    arguments = make_argument_parser().parse_args(args)
    with closing(open(arguments.method_list, "r")) as method_list_file:
        target_methods = parse_method_list(method_list_file)
        
        test_class_index = 1
        test_cases = collections.deque()
        for target_method in target_methods:
            for i in range(target_method.test_count):
                test_name = "test{}".format(i)
                test_body = target_method.instantiate_randomized_test()
                test_cases.append(JUNIT_METHOD_TEMPLATE.format(METHOD_NAME=test_name,
                                                               METHOD_BODY=test_body))

                if i % TESTS_PER_FILE == TESTS_PER_FILE - 1:
                    test_class_name = "{}{}".format(arguments.test_class, test_class_index)
                    write_test_class(arguments.output_dir, test_class_name, test_cases)
                    test_cases.clear()
                    test_class_index += 1
        
        if (test_cases):
            test_class_name = "{}{}".format(arguments.test_class, test_class_index)
            write_test_class(arguments.output_dir, test_class_name, test_cases)


if __name__ == '__main__':
    main(sys.argv[1:])  # Cut off script name
