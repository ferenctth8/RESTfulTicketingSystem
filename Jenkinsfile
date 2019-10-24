/*pipeline {
    //agent { docker { image 'maven:3.6.2' } }
    //agent any
    agent { label '!windows' }

    environment {
       DISABLE_AUTH = 'true'
       DB_ENGINE    = 'sqlite'
    }

    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                echo "Database engine is ${DB_ENGINE}"
                echo "DISABLE_AUTH is ${DISABLE_AUTH}"
                sh 'printenv'
            }
        }
    }
    post {
      always {
         echo 'This will always run'
      }
      success {
         echo 'This will run only if successful'
      }
      failure {
         echo 'This will run only if failed'
      }
      unstable {
         echo 'This will run only if the run was marked as unstable'
      }
      changed {
         echo 'This will run only if the state of the Pipeline has changed'
         echo 'For example, if the Pipeline was previously failing but is now successful'
      }
    }
}*/

pipeline {
    agent {
        docker { image 'node:7-alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'node --version'
            }
        }
    }
}