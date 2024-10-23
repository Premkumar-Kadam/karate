pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myapp-karate"
        CONTAINER_NAME = "myapp-container"
        IMAGE_TAG = "${env.BUILD_NUMBER}" // Tag the Docker image with the build number
    }

    options {
        timestamps() // Add timestamps to log output
        skipStagesAfterUnstable() // Skip further stages if one fails
    }

    stages {

        stage('Build with Maven') {
            steps {
                // Build the Java project using Maven, skipping tests for faster build
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Trivy Security Scan') {
            steps {
                // Trivy command to scan for vulnerabilities
                sh 'trivy image --format html --output trivy-report.html ${DOCKER_IMAGE}:${IMAGE_TAG}'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image with versioned tag
                    docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}")
                }
            }
        }

        stage('Stop and Remove Old Container') {
            steps {
                script {
                    // Stop and remove the old container if it exists
                    def containerExists = sh(script: "docker ps -q -f name=${CONTAINER_NAME}", returnStdout: true).trim()
                    if (containerExists) {
                        sh "docker stop ${CONTAINER_NAME}"
                        sh "docker rm ${CONTAINER_NAME}"
                    } else {
                        echo "No existing container with name ${CONTAINER_NAME} found."
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

                // Determine the status of the pipeline (SUCCESS/FAILURE)
                def pipelineStatus = currentBuild.result ?: 'UNKNOWN'

                def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'

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
                    attachmentsPattern: '**/trivy-report.html',
                    attachLog: true
                )
            }
        }
    }
}
