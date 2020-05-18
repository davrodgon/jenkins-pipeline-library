#!/usr/bin/groovy
import groovy.json.JsonSlurper 
/**
 * Checks the license of a GitHub repository.
 *
 * @param owner The owner of the repository [mandatory]
 * @param repository [mandatory]
 */
def call(String owner, String repository) {
    String GITHUB_API = 'https://api.github.com/repos'
    String url = "${GITHUB_API}/${owner}/${repository}/license"
    println "Querying ${url}"
    def jsonText = url.toURL().getText()
    def json = new JsonSlurper().parseText(jsonText)
    println "JSON ${json}"
    
}
