# Simple code example to test github, Jenkins, dockerhub, Kubernetes, Spring Boot, and H2O.
The basic idea was to see if I can create a CI/CD pipeline that allows me to deploy a scoring model in a micro service with the following requirements:
  * Always on, even through new code deployments
  * Resitent to failures during deployments and other possible issues
  * Arbitrarily scalable allowing auto scaling with increased load
  * Fast enough to allow for real-time on the fly scoring
  * Integrated testing making sure that the model and the code passes all tests before it is deployed

## Prequisites to run it:
  * A running docker machine
  * A Jenkins instance. You can use the image "docker pull jonasberlin/my-jenkins"
  * A functioning Kubernetes cluster (either minikube or your favorite)
  * An image repository for docker images. You can use Docker Hub or a private one

## The root directory contains the following important files:
  * _Jenkinsfile_ this file contains the Jenkins pipeline script used to build and deploy the application
  * _Dockerfile_ this file is used by Jenkins to build the final docker image that contains the service
  * _deploy.yaml_ this file contains the Kubernetes deployment instruction. Note that there is a reference to one of my docker hub repositories. You have to change this one to your own. See below
  * _pom.xml_ this file contains the maven build instructions for the Spring Boot application
  * _deeplearning.zip_ this file contains an H2O Mojo deplyment file which was created off the H2O deep learning example using the MNIST dataset. The basic idea of the model is to recognize hand written images of the number 0 through 9
  
## This is what the application does
