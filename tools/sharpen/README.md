Sharpen
=======

### Installation

* Follow the instructions in the `sharpen-guide.html`, which is a
  snapshot of
  [Paul Du Bois guide to Sharpen](http://pauldb.tumblr.com/post/14916717048/a-guide-to-sharpen-a-great-tool-for-converting-java).
  
  The `sharpen.core_1.0.0.jar` and `sharpen-config-files.zip` files in
  this directory are snapshots of the archives used in the guide.
  
* In the Eclipse Java project that will be converted, create a
  subdirectory called `sharpen/`.  Otherwise, the `clean` target of
  `run-sharpen.xml` will fail.


### Project Conversion

For each program in `experiments/src/programs/`:

* Create a new Java project `sharpen-${program}` using the Java6 JDK
  and default values for everything else.

* In a terminal, delete the new project's `src/` directory.  Replace
  it with a symlink to the program's directory in the
  `experiments/src/programs/` directory:
  
~~~~
$ cd workspace/src/sharpen-${program}
$ rm -r src/
$ ln -s ../../experiments/src/programs/${program}/ src
~~~~

* Create the `sharpen/` directory that will receive the converted
  program.
  
~~~~
$ mkdir sharpen
~~~~

* Copy the Sharpen configuration files to the project's root
  directory.
  
~~~~
$ cp ../../tools/sharpen/config/* .
~~~~

* In Eclipse, refresh the project, right-click on the
  `run-sharpen.xml`, and select `Run As > Ant Build`.  If the build
  succeeds, it writes the C# files to the `sharpen/sharpened.net/src`
  directory.
