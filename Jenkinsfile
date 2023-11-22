pipeline{
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIAL')
        VERSION = "${env.BUILD_ID}"
    }
    tools {
        maven "Maven"
    }
    stages {
        stage('Maven Build') {
            steps {
                sh 'mvn clean package  -DskipTests'
            }
        }
        stage('Run Tests'){
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis'){
    steps {
         sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://3.236.45.147:9000/ -Dsonar.login=squ_3e3dc49632c557975b32ce7256884b42d1a5b17c'
    }
        }
        stage('Check code coverage'){
            steps{
                script{
                    def token = "squ_3e3dc49632c557975b32ce7256884b42d1a5b17c"
                    def sonarQubeUrl = "http://3.236.45.147:9000/api"
                    def componentKey = "com.cotiviti:restaurentlisting"
                    def coverageThreshold = 80.0
                    def response = sh (
                                            script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                                            returnStdout: true
                                        ).trim()
                    def coverage = sh (
                                            script: "echo '${response}' | jq -r '.component.measures[0].value'",
                                            returnStdout: true
                                        ).trim()
                    def cov = 0.0
                                        if (coverage.isFloat()) {
                                            cov = coverage.toDouble()
                                        } else {
                                            println "Coverage value is not a valid number: $coverage"
                                            // Handle the case when coverageString is not a valid number
                                        }
                    echo "Coverage: ${cov}"

                    if (cov < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }
        stage('Docker Build and Push'){
            steps {
                      sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                      sh 'docker build -t chelseanirajan/restaurantlisting-service:${VERSION} .'
                      sh 'docker push chelseanirajan/restaurantlisting-service:${VERSION}'
                  }
        }
        stage('Cleanup Workspace') {
              steps {
                deleteDir()
              }
        }

        stage('Update Image Tag in GitOps') {
              steps {
                 checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:chelseanirajan/deployment-folder.git']])
                script {
               sh '''
                  sed -i "s/image:.*/image: chelseanirajan\\/restaurantlisting-service:${VERSION}/" aws/restaurant-manifest.yml
                '''
                  sh 'git checkout master'
                  sh 'git add .'
                  sh 'git commit -m "Update image tag"'
                sshagent(['git-ssh'])
                    {
                          sh('git push')
                    }
                }
              }
        }


    }

}