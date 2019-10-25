pipeline {
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
            /*post {
              success {
                 archiveArtifacts 'target/*.hpi,target/*.jpi'
              }
              always {
                 //junit '
              }
            }*/
        }

        stage('No-op'){
            steps {
                sh 'ls'
            }
        }
    }
    post {
      always {
         echo 'This will always run, one way or another'
         deleteDir() /* General workspace cleanup */
      }
      success {
         echo 'This will run only if successful - SUCCESS!'
         mail to: 'ferenc.toth@cgi.com',
         subject: "Successful Pipeline: ${currentBuild.fullDisplayName}",
         body: "Everything OK with ${env.BUILD_URL}"
      }
      failure {
         echo 'This will run only if failed - FAILURE!'
         mail to: 'ferenc.toth@cgi.com',
         subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
         body: "Something is wrong with ${env.BUILD_URL}"
      }
      unstable {
         echo 'This will run only if the run was marked as unstable - UNSTABLE!'
      }
      changed {
         echo 'This will run only if the state of the Pipeline has changed'
         echo 'For example, if the Pipeline was previously failing but is now successful'
      }
    }
}