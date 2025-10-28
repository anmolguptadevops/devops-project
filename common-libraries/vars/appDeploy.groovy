def call() {
    def serviceName = env.JOB_NAME.split('/')[0]
    def version = env.GIT_BRANCH == 'main'
        ? "dev-${env.BUILD_NUMBER}"
        : "rel_${env.GIT_BRANCH.split('/')[1]}-${new Date().format('MM.dd')}-${env.BUILD_NUMBER}"

    withCredentials([
        file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE'),
        file(credentialsId: 'tls_crt', variable: 'TLS_CRT_FILE'),
        file(credentialsId: 'tls_key', variable: 'TLS_KEY_FILE')
    ]) {
        withEnv([
            "KUBECONFIG=${KUBECONFIG_FILE}",
            "DO_REGISTRY_HOST=${env.DO_REGISTRY_HOST}",
            "serviceName=${serviceName}",
            "version=${version}",
            "TARGET_NAMESPACE=${TARGET_NAMESPACE}"
        ]) {

            def tlsCrtBase64 = sh(script: "base64 -w0 ${TLS_CRT_FILE}", returnStdout: true).trim()
            def tlsKeyBase64 = sh(script: "base64 -w0 ${TLS_KEY_FILE}", returnStdout: true).trim()

            env.TLS_CRT_BASE64 = tlsCrtBase64
            env.TLS_KEY_BASE64 = tlsKeyBase64

            sh '''
                echo "Rendering manifest..."
                envsubst < ${MANIFEST_FILE} > updated.yaml
                kubectl apply -f updated.yaml -n ${TARGET_NAMESPACE}
            '''
        }
    }

    echo "Deployment done successfully."
}
