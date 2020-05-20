#!/usr/bin/groovy
import eu.indigo.sqa.*

/**
 * Checks the license of a GitHub repository.
 *
 * @param owner The owner of the repository [mandatory]
 * @param repository [mandatory]
 */
def call(String owner, String repository) {
    
    boolean citation = GitHub.isPathInRepository(owner,repository,"CITATION,.json")
   
}
