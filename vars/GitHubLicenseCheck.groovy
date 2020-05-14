#!/usr/bin/groovy

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
    def utils = new eu.indigo.sqa.Utils()
    def json = utils.urlStringToJson(url)
    def bodyText = json.body
    return bodyText
}
