def call() {
    script {
        def serviceName = env.JOB_NAME.split('/')[0]
        echo "ServiceName Name: ${serviceName}"
        def version = env.GIT_BRANCH == 'main' ? "dev-${env.BUILD_NUMBER}" : "rel_${env.GIT_BRANCH.split('/')[1]}-${new Date().format("MM.dd")}-${env.BUILD_NUMBER}"
        sh "echo $version > build_version.txt"
        sh "docker build -t ${DO_REGISTRY_HOST}/${serviceName}:${version} ."
    }
}