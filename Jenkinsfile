@Library('common-libraries') _
pipeline {
    agent {
        kubernetes {
            label "jenkins-agent-axowfna"
            defaultContainer 'jnlp'
            yaml '''
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins
  containers:
  - name: dind
    image: docker:24.0.6-dind
    securityContext:
      privileged: true
  - name: docker
    env:
    - name: DOCKER_HOST
      value: 127.0.0.1
    image: docker:24.0.6
    command:
    - cat
    tty: true
  - name: kubectl
    image: alpine/kubectl:latest
    command:
      - sh
      - -c
      - |
        apk add --no-cache gettext > /dev/null
        cat
    tty: true
  - name: trivy
    image: aquasec/trivy:latest
    command: ["cat"]
    tty: true
  - name: gitleaks
    image: zricethezav/gitleaks:latest 
    command: ["cat"]
    tty: true
  - name: yamllint
    image: cytopia/yamllint:latest
    command: ["cat"]
    tty: true
'''
        }
    }

    options {
        disableConcurrentBuilds()
    }

    parameters {
        string(
            name: 'K8S_NAMESPACE',
            defaultValue: 'default',
            description: 'The target Kubernetes namespace for deployment.'
        )
        string( 
            name: 'MANIFEST_PATH',
            defaultValue: 'k8s/deployment.yaml',
            description: 'The path to the Kubernetes manifest file (e.g., deployment.yaml).'
        )
        string( 
            name: 'INGRESS_URL',
            defaultValue: 'demo.anmol.site',
            description: 'Ingress URL'
        )
    }

    environment {
        IMAGE_NAME = 'flask-app'
        IMAGE_VERSION = '1.0.${BUILD_NUMBER}'
        //DO_TOKEN = credentials('do-token')
        KUBECONFIG = credentials('kubeconfig')
        DO_REGISTRY_HOST = credentials('digitalocean_registry')
        SERVICE_URL = 'http://flask-app.example.com'
        TARGET_NAMESPACE = "${params.K8S_NAMESPACE}"
        MANIFEST_FILE = "${params.MANIFEST_PATH}" 
        INGRESS_URL = "${INGRESS_URL}"

    }
    stages {

        stage('Secret Scan') {
            steps {
                container('gitleaks') {
                    script {
                        secretScan()
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                container('dind') {
                    script {
                        dockerBuild()
                    }
                }
            }
        }

        stage('Scan Docker Image') {
            steps {
                container('dind') {
                    script {
                        dockerScan()
                    }
                }
            }
        }


        stage('Push Docker Image') {
            steps {
                container('dind') {
                    script {
                        dockerPush()
                    }
                }
            }
        }

        stage('YAML Lint') {
            steps {
                container('yamllint') {
                    script {
                       yamlLint()
                    }
                }
            }
        }

        stage('Deploy to k8s') {
            steps {
                container('kubectl') {
                    script {
                       appDeploy()
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                container('kubectl') {
                    script {
                       healthCheck()
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}