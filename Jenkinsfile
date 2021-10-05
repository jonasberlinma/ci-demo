pipeline {
        agent any
        tools {
                maven 'apache-maven-3.5.4'
        }
        stages {
                stage('Build') {
                        steps {
                                sh 'mvn package'
                                withDockerServer([credentialsId: "DockerMachine", uri: "tcp://192.168.99.103:2376" ]){
                                        sh 'docker build -t jonasberlin/ci-demo:${BUILD_NUMBER} $WORKSPACE'
                                        withDockerRegistry([credentialsId: "DockerHub", url: "https://docker.io/jonasberlin/ci-demo/"]){
                                            sh 'docker push jonasberlin/ci-demo:${BUILD_NUMBER}'    
                                        }
                                }
                        }
			post {
      			          success {
                    			junit 'target/surefire-reports/**/*.xml' 
                		  }
           		}
                }
                stage('Deploy'){
                    steps {
                        kubernetesDeploy configs: 'deploy.yaml',  kubeconfigId: 'KubernetesConfig'
                    }
                }
        }
}

