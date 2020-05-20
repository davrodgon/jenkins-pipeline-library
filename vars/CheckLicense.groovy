#!/usr/bin/groovy
import eu.indigo.sqa.*
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
    try {
       def jsonText = url.toURL().getText()
       def json = new JsonSlurper().parseText(jsonText)
       def spdx_id = json.license.spdx_id
       def license = License.getLicenseData(spdx_id)
       println "Is OSI approved ${license.isOsiApproved}"
    } catch(FileNotFoundException e) {
      println "License not found!"
   }
}
