#!/bin/bash

NUMBER_OF_RUNS=7
experiment=${1:-$(date +"%y%m%d-%H%M")}

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
    rm -f res/pex/current_run && ln -s run${runNumber} res/pex/current_run
    ant clean-all
    if ant generate-tests && fixRaytraceFloatZeros; then
	until ant compile-tests; do
	    ant compile-tests | disableNonCompilingTests
	done
	if ant run-tests report; then
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
