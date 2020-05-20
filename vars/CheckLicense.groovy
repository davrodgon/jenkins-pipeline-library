#!/usr/bin/groovy
import eu.indigo.sqa.*

import groovy.json.JsonSlurper 


License retrieveFromSpdx(java.lang.String licenseId) {
    String url = "https://raw.githubusercontent.com/spdx/license-list-data/master/json/licenses.json"
    def jsonText = url.toURL().getText()
    def data = new JsonSlurper().parseText(jsonText)
    return  data.licenses.findAll { it.licenseId == licenseId } as License
} 

/**
 * Checks the license of a GitHub repository.
 *
 * @param owner The owner of the repository [mandatory]
 * @param repository [mandatory]
 */
def call(String owner, String repository) {
    String url = "${GitHub.repository_url}/${owner}/${repository}/license"
    try {
        def jsonText = url.toURL().getText()
        def json = new JsonSlurper().parseText(jsonText)
        def license = retrieveFromSpdx(json.license.spdx_id)
        println "License ${license.name}. Is OSI approved ${license.isOsiApproved}"
    } catch(FileNotFoundException e) {
        println "License not found!"
    }
}
