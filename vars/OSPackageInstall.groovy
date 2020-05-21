#!/usr/bin/groovy

/**
 * Uses APT/YUM package managers to install packages.
 *
 * @param  packages List of required packages [mandatory]
 * @param  package_manager Name of the package manager to be used [mandatory]
 */
def call(List packages, String package_manager) {
    package_str = packages.join(' ')

    if (package_manager.toLowerCase() == 'yum') {
        cmd_prefix = 'yum -y install'
    }
    else if (package_manager.toLowerCase() == 'apt') {
        cmd_prefix = 'apt install -y --no-install-recommends'
    }
    else {
        println("Package manager '${package_manager}' not supported")
        System.exit(1)
    }

    l_cmd = [cmd_prefix, package_str]
    cmd = l_cmd.join(' ')
    sh(script: cmd)
}
