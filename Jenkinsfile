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
				sh 'docker build -t jonasberlin/ci-demo:${BUILD_NUMBER} --pull=true ${WORKSPACE}'
			}
		}
	}

}
