#!/bin/bash

sourceExperiment=${1}

if [ -z "${sourceExperiment}" ]; then
    echo ">>> ERROR: Specify the name of the experiment whose traces should be combined."
    exit 1
fi

for runCoverageDir in $(ls -d coverage-${sourceExperiment}-*); do
    ant clean-all compile-programs
    for otherTool in pex jpf-coral; do
	combinationDir="build/tests/jpf-gradient+${otherTool}/"
	mkdir -p ${combinationDir}
	mkdir -p ${combinationDir}/{coral,early,ej_hash,optimization,dart,power,raytrace,sine,statcalc,tcas,tsafe}
	cp ${runCoverageDir}/merged-jpf-gradient.exec ${combinationDir}
	cp ${runCoverageDir}/merged-${otherTool}.exec ${combinationDir}
    done
    ant report
    mv coverage/ ${runCoverageDir}-combinations/
done
