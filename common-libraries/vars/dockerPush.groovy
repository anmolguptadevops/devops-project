def call() {
    def credId = 'container_registry_creds'
    echo "Logging in to DigitalOcean Container Registry"
    def serviceName = env.JOB_NAME.split('/')[0]
    echo "ServiceName Name: ${serviceName}"
    withCredentials([usernamePassword(
        credentialsId: credId,          
        usernameVariable: 'DOCKER_USER',           
        passwordVariable: 'DOCKER_PASS'           
    )]) {
        sh "echo \"${DOCKER_PASS}\" | docker login -u \"${DOCKER_USER}\" --password-stdin ${DO_REGISTRY_HOST}"
    }
    echo "Pushing Docker image to registry"
    def version = env.GIT_BRANCH == 'main' ? "dev-${env.BUILD_NUMBER}" : "rel_${env.GIT_BRANCH.split('/')[1]}-${new Date().format("MM.dd")}-${env.BUILD_NUMBER}"
    sh "docker push ${DO_REGISTRY_HOST}/${serviceName}:${version}"
}