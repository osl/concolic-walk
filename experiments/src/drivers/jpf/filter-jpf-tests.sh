#!/bin/bash

# Extract the unit test class from JPF's output

if [[ $# != 1 ]]; then
    echo "Usage: ${0} JPF_OUTPUT_FILE"
    echo "JPF_OUTPUT_FILE should be of form 'FooTest.java.log'."
    echo "The script will derive the class name from the file name'"
    exit 1
fi

JPF_OUTPUT_FILE=${1}

if [ ! -f ${JPF_OUTPUT_FILE} ]; then
    echo "File not found: ${JPF_OUTPUT_FILE}"
    exit 2
fi

# In JPF's output, the method summaries follow after the line
# '====================================================== Method Summaries'
# and ends before the next line starting with '===...' (54 times '=').
if grep -q '^=\{54\} Method Summaries$' ${JPF_OUTPUT_FILE}; then
    testClass=$(echo ${JPF_OUTPUT_FILE} | sed -e 's/^\.\?\/\?//' -e 's/\//./g' -e 's/\.java\.log$//')
    testFile=$(basename ${JPF_OUTPUT_FILE} .log)
    cat >${testFile} <<-EOF
	import org.junit.Test;

	public class ${testClass} {
EOF
    sed -e '0,/^=\{54\} Method Summaries$/d' \
	-e '/^=\{54\}/,$d' \
	-e '/^Inputs:/d' \
	-e '/^$/d' \
	-e '/()/d' \
	-e 's/  --> .*$//' \
	-e "s/-2147483648(don't care)/0/g" \
	-e 's/^No path conditions for //' \
	-e 's/$/;/' \
	${JPF_OUTPUT_FILE} \
    | grep -iv '\bERROR\b\|\bWARNING\b' \
    | sort -u \
    | awk '{ printf("\n  @Test\n  public void test%i() {\n    %s\n  }\n", i, $0); i = i + 1; }' \
    >> ${testFile}
    cat >> ${testFile} <<-EOF
	}
EOF
else
    echo "${JPF_OUTPUT_FILE} does not seem to contain JPF generated method summaries.  Aborting."
    exit 3
fi
