import org.codehaus.groovy.control.CompilationFailedException

import eu.indigo.compose.parser.ConfigParser
import eu.indigo.compose.ProjectConfiguration
import eu.indigo.compose.ComposeFactory
import eu.indigo.compose.ComposeFactoryBuilder
import eu.indigo.compose.DockerCompose
import eu.indigo.Tox
import eu.indigo.scm.Git
import eu.indigo.scm.GitLocalBranch

def call(
    Map configs=[
        configFile: './.sqa/config.yml',
        baseRepository: null,
        baseBranch: null,
        credentialsId: null,
        validatorDockerImage: 'eoscsynergy/jpl-validator:1.1.0'
        ],
    Map scmConfigs = [
        localBranch: false
        ]
    ) {

    configsDefault = [
        configFile: './.sqa/config.yml',
        baseRepository: null,
        baseBranch: null,
        credentialsId: null,
        validatorDockerImage: 'eoscsynergy/jpl-validator:1.1.0'
        ]

    scmConfigsDefault = [
        localBranch: false
        ]

    configs = configsDefault + configs
    scmConfigs = scmConfigsDefault + scmConfigs

    def scmCheckout = { ->
        if (baseRepository) {
            checkoutRepository(configs?.baseRepository, configs?.baseBranch, configs?.credentialsId)
        }
        else {
            checkoutRepository()
        }
    }
    scmCheckout.resolveStrategy = Closure.DELEGATE_FIRST

    if (scmConfigs?.localBranch) {
        scmCheckout.delegate = new GitLocalBranch(this)
    }
    else {
        scmCheckout.delegate = new Git(this)
    }
    scmCheckout()

    def yaml = readYaml file: configFile
    def buildNumber = Integer.parseInt(env.BUILD_ID)
    ProjectConfiguration projectConfig = null

    try {
        invalidMessages = validate(configs?.configFile, configs?.validatorDockerImage)
    } catch (GroovyRuntimeException e) {
        error "ConfigValidation have a runtime exception with status:\n$e"
    }
    if (invalidMessages) {
        error("Validation exit code): $invalidMessages")
    }

    projectConfig = new ConfigParser(this).parse(yaml, env)
    switch (projectConfig.nodeAgentAux) {
        case 'DockerCompose':
            projectConfig.nodeAgent = new ComposeFactoryBuilder()
                .setFactory(new DockerCompose(this))
                .setTox(new Tox(this))
                .build()
            break
        default:
            error "pipelineConfig: Node agent ${projectConfig.nodeAgentAux} not defined!"
            break
    }
    return projectConfig
}

def validate(String configFile, String validatorDockerImage) {
    def cmd = "docker pull $validatorDockerImage &&" +
              'docker run --rm -v "$PWD:/sqa" ' + "$validatorDockerImage /sqa/${configFile}"
    return sh(returnStatus: true, script: cmd)
}
