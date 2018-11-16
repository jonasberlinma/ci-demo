Simple code example to test github, Jenkins, dockerhub, and Kubernetes.

Prequisites to run it:
	- A running docker machine
	- A Jenkins instance. You can use the image "docker pull jonasberlin/my-jenkins"
	- A functioning Kubernetes cluster (either minikube or your favorite)

The root directory contains the following important files:
	- "Jenkinsfile" this file contains the Jenkins pipeline used to build the application
	- "Dockerfile" this file is used by Jenkins to build the final docker image that contains the service
	- "deploy.yaml" this file contains the Kubernetes deployment instruction. Note that there is a reference to one of my docker hub repositories. You have to change this one to your own. See below

