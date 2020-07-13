import eu.indigo.compose.ProjectConfiguration
import eu.indigo.compose.ComposeFactory

def call(ProjectConfiguration projectConfig) {
    def timeoutClosure = { Closure block ->
        if (projectConfig.timeout) {
            timeout(time: projectConfig.timeout, activity: true, unit: 'SECONDS') {
                block()
            }
        } else {
            block()
        }
    }
    def environmentClosure = { Closure block ->
        if (projectConfig.environment) {
            withEnv(projectConfig.nodeAgent.envToStep(projectConfig.environment)) {
                block()
            }
        } else {
            block()
        }
    }
    
    timeoutClosure {
        environmentClosure {
            projectConfig.nodeAgent.processStages(projectConfig)
        }
    }
}
