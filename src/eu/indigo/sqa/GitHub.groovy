/*
 * Copyright 2020 David Rodridguez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.indigo.sqa

class GitHub {
    
    static String repository_url = 'https://api.github.com/repos'
    
    def boolean isPathInRepository(String owner,String repository,String path)
    {
        String repository_url = 'https://api.github.com/repos'
        String url = "${repository_url}/${owner}/${repository}/contents/${path}"
        def connection = (HttpURLConnection)url.openConnection()
        connection.setRequestMethod("GET")
        code = connection.getResponseCode()
        //        def code = new URL(url).openConnection().with {
        //            requestMethod = 'GET'
        //            connect()
        //            responseCode
        //        }
        return code == 200
    }

}
