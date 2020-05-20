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

    License getLicenseData(String licenseId) {
       def jsonSlurper = new JsonSlurper()
       String url = "https://raw.githubusercontent.com/spdx/license-list-data/master/json/licenses.json"
       def jsonText = url.toURL().getText()
       def data = new JsonSlurper().parseText(jsonText)
       return  data.licenses.findAll { it.licenseId == licenseId } as License
     }   
}

