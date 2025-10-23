pipeline {
  agent { label 'Jenkins-Agent' }

  tools {
    jdk 'Java17'
    maven 'Maven3'
  }

  environment {
    APP_NAME = "text-editor"
    RELEASE = "1.0.0"
    DOCKER_USER = "neelbishnoi"
    DOCKER_PASS = credentials('dockerhub')  // store DockerHub password/token in Jenkins credentials
    DOCKER_BUILDKIT = 1
    IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
  }

  stages {
    stage("Cleanup Workspace") {
      steps {
        cleanWs()
      }
    }

    stage("Checkout from SCM") {
      steps {
        git branch: 'main', credentialsId: 'github', url: 'https://github.com/Neel-29/Comprehensive-Text-Editor'
      }
    }

    stage("Verify Files") {
      steps {
        echo "Checking workspace structure..."
        sh 'ls -la'
      }
    }

    stage("Build Application") {
      steps {
        sh "mvn clean package -DskipTests"
      }
    }

    stage("Test Application") {
      steps {
        sh "mvn test"
      }
    }

    stage("SonarQube Analysis") {
      steps {
        script {
          withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
            sh "mvn sonar:sonar"
          }
        }
      }
    }

    stage("Quality Gate") {
      steps {
        script {
          waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token'
        }
      }
    }

    stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }

                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
    }

    stage("Deploy") {
      steps {
        echo "Deploying the application..."
      }
    }
  }

  post {
    success {
      echo "✅ Build succeeded and image pushed successfully."
    }
    failure {
      echo "❌ Build failed. Check logs for details."
    }
  }
}
