def call() {
    script {
        echo "Scanning repository for secrets using GitLeaks..."
        sh """
          gitleaks detect --source=. --exit-code=1
        """
    }
}