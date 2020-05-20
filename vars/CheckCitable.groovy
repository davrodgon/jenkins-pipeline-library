#!/usr/bin/groovy


/**
 * Checks the license of a GitHub repository.
 *
 * @param owner The owner of the repository [mandatory]
 * @param repository [mandatory]
 */
def call(String owner, String repository) {
    
    boolean citation = eu.indigo.sqa.GitHub.isPathInRepository(owner,repository,"CITATION.json")
   
}
