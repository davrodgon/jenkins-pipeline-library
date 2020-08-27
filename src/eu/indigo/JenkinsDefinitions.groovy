package eu.indigo

/**
 * Definitions for Docker Compose integration in Jenkins
 * @see: https://docs.docker.com/compose/compose-file/
 */
class JenkinsDefinitions implements Serializable {

    private static final long serialVersionUID = 0L

    def steps

    protected final boolean _DEBUG_ = false
    protected final boolean _WORKSPACEDEBUG_ = false
    protected final int _WORKSPACEDEBUGTIMEOUT_ = 5
    protected final String _WORKSPACEDEBUGUNIT_ = 'MINUTES'
    protected int logLevel = 0
    protected final int _LOGLEVELMAX_ = -1

    /**
    * Define constructor to import definitions from Jenkins context
    * @see https://www.jenkins.io/doc/book/pipeline/shared-libraries/#accessing-steps
    */
    JenkinsDefinitions(steps) {
        this.steps = steps
    }

    void setLogLevel(int level) {
        if(_DEBUG_) {
            logLevel = _LOGLEVELMAX_
        }
        else {
            logLevel = steps.env.JPL_DEBUG ? steps.env.JPL_DEBUG : logLevel
        }
    }

    boolean logTest(int level) {
        logLevel >= level || logLevel == _LOGLEVELMAX_
    }

}
