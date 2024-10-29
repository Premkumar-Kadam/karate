pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myapp-karate"
        CONTAINER_NAME = "myapp-container"
        IMAGE_TAG = "latest" // or "${env.BUILD_NUMBER}"
        SONAR_HOST_URL = "http://172.24.2.184:9001"
        DEPENDENCY_CHECK_DIR = "${WORKSPACE}/target"
    }

    options {
        timestamps() // Add timestamps to log output
        skipStagesAfterUnstable() // Skip further stages if one fails
    }

    stages {
        stage('Code Compile and Unit Tests') {
            parallel {
                stage('Code Compile') {
                    steps {
                        sh "mvn clean compile"
                    }
                }
                stage('Unit Tests') {
                    steps {
                        sh "mvn test"
                    }
                }
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                dir("${WORKSPACE}") {
                    dependencyCheck additionalArguments: "--scan ${DEPENDENCY_CHECK_DIR}", odcInstallation: 'DC'
                }
            }
            post {
                always {
                    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        script {
                            def sonarResult = sh(
                                script: '''
                                mvn sonar:sonar \
                                    -Dsonar.projectKey=ama:karate \
                                    -Dsonar.projectName="Karate Backend" \
                                    -Dsonar.host.url=${SONAR_HOST_URL} \
                                    -Dsonar.login=${SONAR_TOKEN}
                                ''',
                                returnStatus: true
                            )
                            if (sonarResult != 0) {
                                error("SonarQube analysis failed!")
                            }
                        }
                    }
                }
            }
        }

        stage('Code Build') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}")
                }
            }
        }

        stage('Run Trivy Security Scan') {
            steps {
                script {
                    // Run Trivy command to scan for vulnerabilities
                    sh 'trivy image --format json --output trivy-report.json ${DOCKER_IMAGE}:${IMAGE_TAG}'
                }
            }
        }

        stage('Manage Old Container') {
            steps {
                script {
                    echo "Checking for existing container: ${CONTAINER_NAME}"

                    def containerExists = sh(script: "docker ps -a --filter name=${CONTAINER_NAME} -q", returnStdout: true).trim()

                    if (containerExists) {
                        echo "Stopping and removing existing container: ${CONTAINER_NAME}"
                        sh "docker stop ${CONTAINER_NAME} || true"
                        sh "docker rm ${CONTAINER_NAME} || true"
                    } else {
                        echo "No existing container found."
                    }
                }
            }
        }

        stage('Deploy New Container') {
            steps {
                script {
                    // Run a new container with the latest image
                    docker.image("${DOCKER_IMAGE}:${IMAGE_TAG}").run("-d --name ${CONTAINER_NAME} -p 8085:8085")
                }
            }
        }
    }

    post {
        always {
            script {
                def jobName = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def branchName = env.BRANCH_NAME ?: 'main'
                def pipelineStatus = currentBuild.result ?: 'SUCCESS'
                def bannerColor = (pipelineStatus == 'SUCCESS') ? 'green' : 'red'
                def buildDuration = currentBuild.durationString

                def body = """<html>
                    <body>
                        <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                            <h2>${jobName} - Build ${buildNumber}</h2>
                            <div style="background-color: ${bannerColor}; padding: 10px;">
                                <h3 style="color: white;">Pipeline Status: ${pipelineStatus.toUpperCase()}</h3>
                                <p>Branch: ${branchName}</p>
                                <p>Duration: ${buildDuration}</p>
                                <p>Check the <a href="${env.BUILD_URL}">console output</a>.</p>
                            </div>
                        </div>
                    </body>
                    </html>"""

                emailext (
                    subject: "${jobName} - Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
                    body: body,
                    to: 'premkadam35@gmail.com',
                    from: 'premkumar.kadam@etpgroup.com',
                    replyTo: 'premkumar.kadam@etpgroup.com',
                    mimeType: 'text/html',
                    attachmentsPattern: '**/dependency-check-report.xml',
                    attachLog: true
                )
            }
        }
    }
}
