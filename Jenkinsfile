pipeline {
	agent any
	tools {
		maven 'apache-maven-3.5.4'
	}
	node {
	def app
	stages {
		stage('Build') {
			steps {
				sh 'mvn test'
				sh 'mvn package'
				app=docker.build("jonasberlin/ci-demo:${BUILD_NUMBER}")
			}
		}
	}
	}
}
