#!/bin/bash

NUMBER_OF_RUNS=5
experiment=${1:-var-$(date +"%y%m%d-%H%M")}

function disableNonCompilingTests() {
    # From ant's javac output, extract the "file:line" part of error
    # messages (grep and first sed call), and then disable the line in
    # the file (while and second sed call).
    grep '\[javac\].* error: ' \
	| sed -e 's/^\s*\[javac\] //' -e 's/: error: .*$/:/' \
	| sort --unique \
	| while IFS=":" read -r file line; do sed "${line} s/^/\/\//" -i.orig ${file}; done
}

function fixRaytraceFloatZeros() {
    # Adds the missing "f" suffix to the "0.0" literals that the JPF
    # raytrace tests use by default.
    for jpfVariantGenDir in gen/jpf-*; do
	sed 's/0\.0\(,\|)\)/0\.0f\1/g' -i.orig ${jpfVariantGenDir}/raytrace/*.java
    done
}

echo ">>> Running experiment \"${experiment}\" ($NUMBER_OF_RUNS iterations)"
for runNumber in $(seq ${NUMBER_OF_RUNS}); do
    ant clean-all
    if ant generate-tests-jpf-gradient-variations && fixRaytraceFloatZeros; then
	until ant compile-tests-jpf-gradient-variations; do
	    ant compile-tests-jpf-gradient-variations | disableNonCompilingTests
	done
	if ant run-tests-jpf-gradient-variations report; then
	    mv build.log coverage/
	    mv gen/ gen-${experiment}-${runNumber}/
	    mv coverage/ coverage-${experiment}-${runNumber}/
	else
	    echo ">>> ERROR: Collecting the coverage failed.  Aborting."
	    exit 2
	fi
    else
	echo ">>> ERROR: Generating the tests failed.  Aborting."
	exit 1
    fi
done

echo ">>> Creating experiment archive gen_coverage_${experiment}.tar.gz"
tar czf gen_coverage_${experiment}.tar.gz gen-${experiment}-*/ coverage-${experiment}-*/
