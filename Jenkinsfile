pipeline {
  agent { label 'Jenkins-Agent' }
  tools {
    jdk 'Java17'
    maven 'Maven3'
  }
  stages {
    stage("Cleanup Workspace"){
      steps {
        cleanWs()
      }
    }
    stage("Checkout from SCM"){
      steps {
        git branch: 'main', credentialsId: 'github', url: 'https://github.com/Neel-29/Comprehensive-Text-Editor'
      }
    }
    stage("Build Application"){
      steps {
        sh "mvn clean package"
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }
    stage("Test Application"){
      steps {
        sh "mvn test"
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
    stage("Deploy") {
      steps {
        echo "Deploying the application..."
      }
    }
  }
}
