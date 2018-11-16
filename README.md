# Simple code example to test GitHub, Jenkins, Docker, Docker Hub, Kubernetes, Spring Boot, and H2O.
The basic idea was to see if I can create a CI/CD pipeline that allows me to deploy a scoring model in a micro service with the following requirements:
  * Always on, even through new code deployments
  * Resitent to failures during deployments and other possible issues
  * Arbitrarily scalable allowing auto scaling with increased load
  * Fast enough to allow for real-time on the fly scoring
  * Integrated testing making sure that the model and the code passes all tests before it is deployed

So, the point of the exercise is more about studying the build and deployment process rather than the deployed service or machine learning.

## Prequisites to run it:
  * A Git repository that you can mess around with. You may just want to clone this one
  * A running docker machine
  * A Jenkins instance. You can use the image "docker pull jonasberlin/my-jenkins" or yor favorite one
  * A functioning Kubernetes cluster (either minikube or your favorite)
  * An image repository for docker images. You can use Docker Hub or a private one

## The root directory contains the following important files:
  * _Jenkinsfile_ this file contains the Jenkins pipeline script used to build and deploy the application
  * _Dockerfile_ this file is used by Jenkins to build the final docker image that contains the service
  * _deploy.yaml_ this file contains the Kubernetes deployment instruction. Note that there is a reference to one of my docker hub repositories. You have to change this one to your own. See below
  * _pom.xml_ this file contains the maven build instructions for the Spring Boot application
  * _deeplearning.zip_ this file contains an H2O Mojo deplyment file which was created off the H2O deep learning example using the MNIST dataset. The basic idea of the model is to recognize hand written images of the number 0 through 9
  
## The server application
The server application which gets deployed on the Kubernetes cluster exposes a RESTful web services which on startup loads the _deeplearning.zip_ Mojo model file and then waits for requests. Once a request is received it:
  * Parses the incoming JSON
  * Creates an H2O ROWData object
  * Scores the row through the model
  * Returns a JSON object representing the result
  
## The client application
A separate client application is available which can be used to test either your local copy of the application or one deployed on a Kubernetes cluster. This application simply:
  * Reads a test data file
  * Creates a JSON object for each record
  * Calls the scoring web service (server application)
  * Receives the result
  * Writes out the result

## The build process
The Jenkins build process does the following:
  * Waits for a change to happen to the configured repository (see configuration below)
  * Pulls down the _Jenkinsfile_ file from the repository which contains the following build process steps
  * Checks out the code from the Git repository
  * Uses Maven to build the Java Spring Boot application that runs the service
  * Uses the configured Docker machine and the _Dockerfile_ instructions to build a new docker image containing the application and the _deeplearning.zip_ Mojo model file
  * Uploads the new docker image to Docker Hub (you can use your favorite repository)
  * Creates a new deployment configuration file from the template _deploy.yaml_ and uploads it to the configured Kubernetes cluster
  
## Added configuration
To get it all to work you have to set up a few things in Jenkins.

### GitHub connection
