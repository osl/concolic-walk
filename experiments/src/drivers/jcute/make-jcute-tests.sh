#!/bin/bash

# Generate a single unit test from a driver

if [[ $# != 3 ]]; then
    echo "Usage: ${0} CLASS_PATH OUTPUT_DIR DRIVER_CLASS"
    exit 1
fi

CLASS_PATH=${1}
OUTPUT_DIR=${2}
DRIVER_CLASS=${3}

TEST_TEMPLATE="./UnitTestTemplate.java"
TEST_TEMPLATE_ABSOLUTE=$(cd $(dirname ${0}) && realpath ${TEST_TEMPLATE})

TEMP_DIR=$(mktemp -d)

JAVA="java"

JCUTE_HOME="../../../../tools/jcute"
JCUTE_HOME_ABSOLUTE=$(cd $(dirname ${0}) && realpath ${JCUTE_HOME})
JCUTE_MAX_ITERATIONS=50
JCUTE_ARGUMENTS="-d:0:-p:1:-j:-r"

JCUTE_MODE_NEXT_PATH=0
JCUTE_MODE_REPLAY_PATH=1
JCUTE_MODE_IGNORE_PATH_HISTORY=2


function instrumentDriver() {
    # Based on jCUTE's `jcutec` shell script

    echo ">>> Instrumenting ${DRIVER_CLASS}"

    cd ${TEMP_DIR} \
	&& rm -rf * \
	&& ${JAVA} -cp ${CLASS_PATH} \
	        -Dcute.sequential=true \
	        cute.instrument.CuteInstrumenter \
	        -keep-line-number \
	        -d ${TEMP_DIR}/classes \
	        -x cute \
                -x lpsolve \
	        --app ${DRIVER_CLASS}
}


function generateInputFiles() {
    # Based on jCUTE's `jcute` shell script

    echo ">>> Running jCUTE [${DRIVER_CLASS}]"

    export LD_LIBRARY_PATH=${JCUTE_HOME_ABSOLUTE};
    cd ${TEMP_DIR}

    for i in $(seq ${JCUTE_MAX_ITERATIONS}); do
	mode=${JCUTE_MODE_NEXT_PATH};
	if [[ $$i == 1 ]]; then
	    export mode=${JCUTE_MODE_IGNORE_PATH_HISTORY};
	fi;

#	echo -n "."
	${JAVA} -cp ${TEMP_DIR}/classes:${CLASS_PATH} \
	        -ea -Xmx512m -Xms512m \
	        -Dcute.args="${JCUTE_ARGUMENTS}:-m:${mode}" \
	        -Djava.library.path=${JCUTE_HOME_ABSOLUTE} \
	        ${DRIVER_CLASS}
	if [[ $(( $? & 2)) == 2 ]]; then
	    break;
	elif [ -f cuteJUnitInput ]; then
	    mv cuteJUnitInput cuteJUnitInput.$i
	fi
    done
}


function mergeInputFilesIntoTest() {
    # jCUTE generates unit tests only from within the GUI.  However,
    # it stores the code for setting up the test inputs in a file.
    # This method wraps the inputs into test methods and inserts the
    # test methods into a unit test template (after removing duplicate
    # inputs).
    
    testClass=${DRIVER_CLASS}Test
    
    echo ">>> Generating unit test [${DRIVER_CLASS}]"
    removeDuplicateInputFiles

    for inputFile in $(ls ${TEMP_DIR}/cuteJUnitInput*); do
	testNumber=$(( ${testNumber} + 1 ));
	testMethods=$(cat <<-EOF
	    ${testMethods}
	
	    public void test${testNumber}() {
	        i=0;
		$(cat ${inputFile} | sed 's/^/        /')
	        cute.Cute.input = this;
	        i=0;
	        ${DRIVER_CLASS}.main(null);
	    }
	EOF
	)
    done
    mkdir -p ${OUTPUT_DIR}
    m4 -DTARGET_CLASS="${DRIVER_CLASS}" \
       -DTEST_CLASS="${testClass}" \
       -DTEST_METHODS="${testMethods}" \
       ${TEST_TEMPLATE_ABSOLUTE} > ${OUTPUT_DIR}/${testClass}.java
}


function removeDuplicateInputFiles() {
    duplicates=$(find ${TEMP_DIR} -type f \
	                          -name "cuteJUnitInput*" \
                                  -exec md5sum {} \; \
		 | sort -k 1,32 \
		 | awk '{ if ($1 == lastMd5) print $2; lastMd5 = $1 }')
    if [ -z ${duplicates} ]; then
	rm -f ${duplicates};
    fi
}

instrumentDriver
generateInputFiles
mergeInputFilesIntoTest

rm -rf ${TEMP_DIR}
