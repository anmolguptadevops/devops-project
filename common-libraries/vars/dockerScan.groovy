def call() {
    def serviceName = env.JOB_NAME.split('/')[0]
    def version = env.GIT_BRANCH == 'main' 
        ? "dev-${env.BUILD_NUMBER}" 
        : "rel_${env.GIT_BRANCH.split('/')[1]}-${new Date().format("MM.dd")}-${env.BUILD_NUMBER}"

    def imageName = "${env.DO_REGISTRY_HOST}/${serviceName}:${version}"
    echo "🔍 Scanning local Docker image with Trivy: ${imageName}"
    sh "apk add --no-cache curl"

    sh '''
    if ! command -v trivy > /dev/null; then
        echo "Installing Trivy..."
        curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b /usr/local/bin
    fi
    trivy --version
    '''
    def status = sh(
        script: "trivy image --severity CRITICAL ${imageName}",
        returnStatus: true
    )

    if (status == 0) {
        echo "Trivy scan passed. No CRITICAL vulnerabilities found."
    } else {
        error("Trivy scan failed! CRITICAL vulnerabilities found in image ${imageName}")
    }
}
