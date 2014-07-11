#!/bin/bash

if [ "${1}" == "--cumulative" ]; then
    CUMULATIVE_MODE="true"
    shift
fi

TEST_BUILD_DIR=${1:-./build/tests}
REPORT_TASK_FILE=${2:-./build/jacoco-reports.xml}

TOOLS=$(cd ${TEST_BUILD_DIR} && ls)

# Header
reportTargetList=$(echo ${TOOLS} | sed -e 's/\b\s/, /g' -e 's/\(\S*\)/report-\1/g')
cat > ${REPORT_TASK_FILE} <<-EOF
<?xml version="1.0" encoding="UTF-8"?>
<project default="report" xmlns:jacoco="antlib:org.jacoco.ant" >
    <!--
       Path to the JaCoCo JARs; should be set by calling Ant.

       Note: The caller can set the reference only if it is defined here.
    -->
    <path id="classpath.jacoco" />
    <taskdef uri="antlib:org.jacoco.ant"
             resource="org/jacoco/ant/antlib.xml"
             classpathref="classpath.jacoco" />

    <target name="report" depends="${reportTargetList}" />
EOF


for tool in ${TOOLS}; do
    cat >> ${REPORT_TASK_FILE} <<-EOF
    <target name="report-${tool}">
      <jacoco:report>
        <executiondata>
EOF
    if [ -z ${CUMULATIVE_MODE} ]; then
	cat >> ${REPORT_TASK_FILE} <<-EOF
          <fileset dir="\${build.tests.dir}/${tool}"
                   includes="**/*.exec" />
EOF
    else
	cat >> ${REPORT_TASK_FILE} <<-EOF
          <fileset dir="\${experiments.dir}/"
                   includes="**/merged-${tool}.exec" />
EOF
    fi
    cat >> ${REPORT_TASK_FILE} <<-EOF
        </executiondata>

        <structure name="${tool} Coverage Evaluation">
EOF

    testedPrograms=$(find ${TEST_BUILD_DIR}/${tool} -mindepth 1 -maxdepth 1 -type d -exec basename {} \;)
    for program in ${testedPrograms}; do
	cat >> ${REPORT_TASK_FILE} <<-EOF
          <group name="${program}">
            <classfiles>
              <fileset dir="\${build.programs.dir}/${program}" />
            </classfiles>
            <sourcefiles encoding="UTF-8">
              <fileset dir="\${src.programs.dir}/${program}" />
            </sourcefiles>
          </group>
EOF
    done

    cat >> ${REPORT_TASK_FILE} <<-EOF
        </structure>

        <html destdir="\${report.dir}/${tool}" />
        <csv destfile="\${report.dir}/report-${tool}.csv" />
        <xml destfile="\${report.dir}/report-${tool}.xml" />
      </jacoco:report>
EOF
    if [ -z ${CUMULATIVE_MODE} ]; then
	cat >> ${REPORT_TASK_FILE} <<-EOF
      <jacoco:merge destfile="\${report.dir}/merged-${tool}.exec">
        <fileset dir="\${build.tests.dir}/${tool}"
                 includes="**/jacoco.exec" />
      </jacoco:merge>
EOF
    fi
    cat >> ${REPORT_TASK_FILE} <<-EOF
    </target>
EOF
done

# Footer
cat >> ${REPORT_TASK_FILE} <<-EOF
</project>
EOF
