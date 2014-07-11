#!/usr/bin/python2

from __future__ import print_function

__DESCRIPTION="""Generates boilerplate jCUTE test drivers
                 for all the public static methods in a class.

                 Usage: javap Benchmark.class | make-jcute-drivers.py
              """

import re
import sys
import textwrap

from contextlib import closing


CLASS_RE=re.compile(r"\s*public class (?P<class_name>[a-zA-Z][\w.]*) \{")
ENTRY_METHOD_RE=re.compile(r"\s*public static [a-zA-Z]\w*\s+(?P<method_name>[a-zA-Z]\w*)\s*\((?P<argument_type_list>.*)\);")

JCUTE_DRIVER_PREFIX="JCuteTarget"
JCUTE_DRIVER_TEMPLATE=textwrap.dedent("""
  import cute.Cute;
  
  public class {CLASS_NAME} {{
    public static void main(String[] args) {{
      {BODY}
    }}
  }}
""")

INPUT_TYPES = {
    "boolean": "Boolean",
    "byte": "Byte",
    "char": "Char",
    "short": "Short",
    "int": "Integer",
    "long": "Long",
    "float": "Float",
    "double": "Double"
}


def declareArg(i, argType):
    if argType in INPUT_TYPES:
        cuteType = "{}()".format(INPUT_TYPES[argType])
    else:
        cuteType = "Object(\"{}\");".format(argType)
    return "{} a{} = cute.Cute.input.{};".format(argType, i, cuteType)


className = "UNKNOWN"
for line in sys.stdin:
    if CLASS_RE.match(line):
        className = CLASS_RE.match(line).group('class_name')
        print(">>> " + className);

    elif ENTRY_METHOD_RE.match(line):
        m = ENTRY_METHOD_RE.match(line)
        targetMethod = m.group('method_name')
        argumentTypes = map(str.strip, m.group('argument_type_list').split(","))

        driverClass = "{}{}".format(JCUTE_DRIVER_PREFIX,
                                    targetMethod.capitalize())
        driverFileName = "{}.java".format(driverClass)

        
        body = [declareArg(i, t) for i, t in enumerate(argumentTypes)]
        arguments = ", ".join(["a{}".format(i) for i in range(len(body))])
        body.append("{}.{}({});".format(className, targetMethod, arguments))

        with closing(open(driverFileName, "w")) as driverFile:
            print(JCUTE_DRIVER_TEMPLATE.format(CLASS_NAME=driverClass,
                                               BODY="\n    ".join(body)),
                  file=driverFile)
