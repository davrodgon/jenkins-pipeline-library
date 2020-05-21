#!/usr/bin/groovy

/**
 * Builds a Docker image.
 * 
 * Takes as first argument a Map of optional parameters that can contain the following named ones:
 *  - tag Tag for the Docker image (aligned with SCM branch) [List, String] [named, optional]
 *  - build_args Build args for Docker image creation [List] [named, optional]
 *  - build_dir Directory containing the context files for the build [String] [named, optional]
 *  - dockerfile_path Dockerfile location (defaults to $build_dir/Dockerfile) [String] [named, optional]
 *
 * @param  docker a Map of optional parameters for  building the image
 * @param  repository Docker registry's repository name [mandatory]
 * @return List of Docker image IDs being built
 */
def call(Map docker=[:], String repository) {
    ids = [] 
    if (docker.tag instanceof List) {
        docker.tag.each {
            ids.push((repository + ':' + it).toLowerCase())
        }
    }
    else if (docker.tag in ['master', 'latest', 'null', null]) {
        ids.push((repository + ':latest').toLowerCase())
    }
    else {
        ids.push((repository + ':' + docker.tag).toLowerCase())
    }
    id_str = ids.join(' -t ')

    build_args = ''
    docker.build_args.each {
        build_args += "--build-arg ${it} "
    }

    dockerfile_path = ''
    if (docker.dockerfile_path) {
        dockerfile_path += "-f ${docker.dockerfile_path}"
    }

    cmd = "docker build --no-cache --force-rm -t $id_str $build_args $dockerfile_path"
    cmd = cmd.trim()

    if (docker.build_dir == null) {
        docker.build_dir = "."
    }
    sh "$cmd $docker.build_dir"

    return ids
}

