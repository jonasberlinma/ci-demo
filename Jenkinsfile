pipeline {
	agent any
	tools {
		maven 'apache-maven-3.5.4'
	}
	def app
	stages {
		stage('Build') {
			steps {
				sh 'mvn test'
				sh 'mvn package'
				docker.build("jonasberlin/ci-demo:${BUILD_NUMBER}")
			}
		}
	}
}
