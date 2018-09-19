pipeline {
	agent any
	tools {
		maven 'apache-maven-3.5.4'
	}

	stages {
		stage('Build') {
			steps {
				sh 'mvn test'
			}
		}
	}

}
