def call() {
    echo "Linting YAML files in ${MANIFEST_FILE}..."
    sh """
        # Install yamllint if not present
        if ! command -v yamllint &> /dev/null; then
            pip install --user yamllint
            export PATH=\$PATH:\$HOME/.local/bin
        fi

        # Lint all YAML files in the specified folder
        yamllint ${MANIFEST_FILE}
    """
}