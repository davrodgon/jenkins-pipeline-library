package eu.indigo.compose

import groovy.transform.ToString

/**
 * Project configuration
 */
@ToString
class ProjectConfiguration implements Serializable {

    private static final long serialVersionUID = 0L

    def nodeAgent
    def nodeAgentAux
    def config = [
        deploy_template: '.sqa/docker-compose.yml',
        project_repos: [
            deepaas: [
                repo: 'https://github.com/indigo-dc/DEEPaaS.git',
                branch: 'master'
            ]
        ]
    ]
    def stagesList = [
        [
            stage: 'qc-style deepaas',
            repo: 'deepaas',
            container: 'deepaas',
            tox: [
                testenv: ['pep8'],
                tox_file: './tox.ini'
            ]
        ],
        [
            stage: 'qc-coverage deepaas',
            repo: 'deepaas',
            container: 'deepaas',
            tox: [
                testenv: ['cover', 'cobertura'],
                tox_file: './tox.ini'
            ]
        ],
        [
            stage: 'qc-security deepaas',
            repo: 'deepaas',
            container: 'deepaas',
            tox: [
                testenv: ['bandit'],
                tox_file: './tox.ini'
            ]
        ]
    ]
    def environment
    def projectName
    def buildNumber
    def timeout

}
