#!/usr/bin/groovy
import groovy.json.JsonSlurper 

class License {
    String reference
    Boolean  isDeprecatedLicenseId
    String  detailsUrl
    String  referenceNumber
    String  name
    String  licenseId
    String[]  seeAlso
    Boolean isOsiApproved
    Boolean isFsfLibre
}

License getLicenseData(String licenseId) {
    try { 
       def jsonSlurper = new JsonSlurper()
       def data = jsonSlurper.parseText(new File("licenses.json").text)
       return  data.licenses.findAll { it.licenseId == licenseId } as License
    } catch(FileNotFoundException e) {
      println "Licenses reference list not found!"
   }
}

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
    try {
       def jsonText = url.toURL().getText()
       def json = new JsonSlurper().parseText(jsonText)
       def spdx_id = json.license.spdx_id
       println "SPDX ID ${spdx_id}"
       def license = getLicenseData(spdx_id)
       println "Is OSI approved ${license.isOsiApproved}"
    } catch(FileNotFoundException e) {
      println "License not found!"
   }
    
}


