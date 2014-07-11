The `Sample*.java` files in the `coral/tests/samples/` directory are
part of CORAL's benchmark package.  The corresponding `.jpf` files
were generated with

~~~~
$ for javaFile in coral/tests/samples/*.java; do \
    targetClass=$(basename -s .java ${javaFile}); \
	echo -e "@include common-coral.properties\ntarget = coral.tests.samples.${targetClass}" > ${targetClass}.jpf; \
  done
~~~~
