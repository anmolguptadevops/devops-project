
def call() {
    withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')]) {
        withEnv(["KUBECONFIG=${KUBECONFIG_FILE}"]) {
            def serviceName = env.JOB_NAME.split('/')[0]

            echo "Waiting for deployment '${serviceName}-deployment' rollout..."
            sh "kubectl rollout status deployment/${serviceName}-deployment -n  ${TARGET_NAMESPACE} --timeout=120s"

            echo "Curling Ingress endpoint '${INGRESS_URL}'..."
            def httpCode = sh(script: "curl -s -o /dev/null -w \"%{http_code}\" https://${INGRESS_URL}", returnStdout: true).trim()

            if (httpCode == "200") {
                echo "Ingress returned HTTP 200. Deployment is healthy!"
            } else {
                error("Ingress check failed! HTTP code: ${httpCode}")
            }
        }
    }
}
