#!/usr/bin/groovy

/**
 * Runs OWASP dependency checker.
 *
 * @param  src_path The path to the source code [mandatory]
 * @param  project Project name [optional]
 * @see https://plugins.jenkins.io/dependency-check-jenkins-plugin
 */
def call(String src_path, String project="My Project") {
    def OWASP_DIR=[env.WORKSPACE, 'OWASP'].join('/')
    def OWASP_DATA_DIR=[env.WORKSPACE, 'OWASP', 'data'].join('/')
    def OWASP_REPORT_DIR=[env.WORKSPACE, 'OWASP', 'report'].join('/')
	
    sh "mkdir -p $OWASP_DATA_DIR"
    sh "mkdir -p $OWASP_REPORT_DIR"
    sh "chmod -R 777 $OWASP_DIR"
	
    dir("$src_path") {
        timeout(time: 10, unit: "MINUTES") {
            withDockerContainer(
                image: 'owasp/dependency-check',
                args: "--entrypoint '' --volume ${src_path}:/src --volume ${OWASP_DATA_DIR}:/usr/share/dependency-check/data --volume ${OWASP_REPORT_DIR}:/report") {
                sh "/usr/share/dependency-check/bin/dependency-check.sh --project 'OWASP Dependency Check for $project' --scan /src --format 'ALL' --enableExperimental"
            }
        }
    }
}

