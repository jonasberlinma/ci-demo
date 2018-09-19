pipeline {
	agent any
	tools {
		maven 'DefaultMaven'
	}

	stages {
		stage('Build') {
			steps {
				sh 'mvn test'
			}
		}
	}

}
