pipeline {
        agent any
        tools {
                maven 'apache-maven-3.5.4'
        }
        stages {
                stage('Build') {
                        steps {
                                sh 'mvn test'
                                sh 'mvn package'
                                withDockerServer([credentialsId: "DockerMachine", uri: "tcp://192.168.99.100:2376" ]){
                                        sh 'docker build -t jonasberlin/ci-demo:${BUILD_NUMBER} $WORKSPACE'
                                        withDockerRegistry([credentialsId: "DockerHub", url: "https://docker.io/jonasberlin/ci-demo/"]){
                                            sh 'docker push jonasberlin/ci-demo:${BUILD_NUMBER}'    
                                        }
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

