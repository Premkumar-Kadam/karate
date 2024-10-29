pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myapp-karate"
        CONTAINER_NAME = "myapp-container"
       // IMAGE_TAG = "${env.BUILD_NUMBER}" // Tag the Docker image with the build number
        IMAGE_TAG = "latest"
        SONAR_HOST_URL = "http://172.24.2.184:9001"
    }

    options {
        timestamps() // Add timestamps to log output
        skipStagesAfterUnstable() // Skip further stages if one fails
    }

    stages {
        stage('Code-Compile') {
            steps {
               sh "mvn clean compile"
            }
        }
        
        stage('Unit Tests') {
            steps {
               sh "mvn test"
            }
        }
        
        stage('OWASP Dependency Check') {
            steps {
                // Set the working directory to the root of the project if needed
                dir("${WORKSPACE}") {
                    // Run the OWASP Dependency Check
                    dependencyCheck additionalArguments: '--scan ${WORKSPACE}/target', odcInstallation: 'DC'
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
                // Fetch SonarQube token from Jenkins credentials
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        sh '''
                        mvn sonar:sonar \
                            -Dsonar.projectKey=ama:karate \
                            -Dsonar.projectName="Karate Backend" \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for the quality gate result and fail the build if the gate is not met
                waitForQualityGate abortPipeline: true
            }
        }
        
        stage('Code-Build') {
            steps {
               sh "mvn clean package"
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
        /*
        stage('Run Trivy Security Scan') {
            steps {
                // Trivy command to scan for vulnerabilities
                sh 'trivy image --format json --output trivy-report.json ${DOCKER_IMAGE}:${IMAGE_TAG}'
            }
        }
        */
        
        stage('Stop and Remove Old Container') {
            steps {
                script {
                    echo "Checking for existing container with name: ${CONTAINER_NAME}"

                    def containerExists = sh(script: "docker ps -a --format '{{.Names}}' | grep -w \"${CONTAINER_NAME}\" || true", returnStdout: true).trim()

                    // Check if the container exists
                    if (containerExists) {
                        echo "Container found. Stopping and removing: ${CONTAINER_NAME}"
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
                    attachmentsPattern: '**/dependency-check-report.xml',
                    attachLog: true
                )
            }
        }
    }
}
