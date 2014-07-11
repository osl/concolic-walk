BruteFuzz
=========

BruteFuzz generates [JUnit][junit] tests that execute static methods
on uniformly randomized inputs.


Usage
-----

~~~~
brutefuzz.py [-h] TEST_CLASS_NAME METHOD_LIST OUTPUT_DIRECTORY
~~~~

For each line of the `METHOD_LIST` file, such as `C.m(int,double):100`,
BruteFuzz creates the specified number (here: 100) of test methods of
the form
~~~~
@Test
test1() {
  C.m(23, 42.0);
}
~~~~
where 23 and 42.0 are uniformly random input values.  The test methods
are collected in a class named `TEST_CLASS_NAME` whose defining
`.java` file is written to the `OUTPUT_DIRECTORY`.


### Method List

Each line in the method list file specifies the following:
1. The fully qualified name and the parameter types of a target
   method.  The method must be static and all its parameters must be
   of primitive types (`int`, `double`, etc.).
2. The number of tests to generate for this method.
A colon (`:`) seperates the two entries.  Lines starting with a hash
symbol (`#`) will be ignored.

For example, below method list requests 5000 tests for the method
`mthd` that takes a single `int` argument and belongs to the class
`package.Cls`.
~~~~
# method(type,type,type):num_tests
package.Cls.mthd(int):5000
~~~~

A fast way to generate method lists is reading the public interface of
a target class with `javap` and then adjusting the output via
search-and-replace.


Installation
------------

BruteFuzz is a script written in version 2 of the
[Python programming language][python].  It only requires a working
Python installation to run.  Simply download `brutefuzz.py` from this
repository and execute it: `python3 brutefuzz.py`


License
-------

The BruteFuzz tool is available under the MIT license as described in
the `LICENSE` file.  Copyright (c) 2014 The University of Illinois
Board of Trustees.  All Rights Reserved.


Acknowledgments
---------------

Work on BruteFuzz was supported in part by sponsorships from the Army
Research Office under award W911NF-09-1-0273 and a fellowship from the
Graduate College of the University of Illinois at Urbana--Champaign.


[junit]: http://junit.org "Homepage of the JUnit Testing Framework for Java"
[python]: http://python.org "Homepage of the Python Programming Language"
