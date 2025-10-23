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
    DOCKER_PASS = 'dockerhub'
    DOCKER_BUILDKIT = '0'
    IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
    IMAGE_TAG  = "${RELEASE}"
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
        sh 'ls -la target || true'
      }
    }

    stage("Build Application") {
      steps {
        sh "mvn -B clean package -DskipTests"
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
          sh '''
            if ls target/*.war 1> /dev/null 2>&1; then
              echo "WAR found: $(ls target/*.war)"
            else
              echo "ERROR: WAR not found in target/"
              ls -la target || true
              exit 1
            fi
          '''
          docker.withRegistry('',DOCKER_PASS) {
            docker_image = docker.build "${IMAGE_NAME}:${IMAGE_TAG}"
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
        steps {
          echo "Deploying the application..."
          script {
          // Use the IMAGE_NAME defined in your environment block
          def containerName = "${APP_NAME}"
          def imageName = "${IMAGE_NAME}:${IMAGE_TAG}"

          sh """
            # Stop and remove the old container if it exists
            docker stop ${containerName} || true
            docker rm ${containerName} || true
            
            # Pull the new image from Docker Hub
            docker pull ${imageName}
            
            # Run the new container
            # -d = run in detached mode
            # -p 8080:8080 = map host port 8080 to container port 8080
            # --name = give the container a static name for easy management
            docker run -d -p 8080:8080 --name ${containerName} ${imageName}
          """
          }
        }
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