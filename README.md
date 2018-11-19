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
  
You can run the client in your favorite way with three arguments:

```
java org.theberlins.citest.ImageRecognitionClientApplication http://192.168.99.101:32700 test.csv 2
```
The third argument is the number of threads. If you need a copy of the test data it is available here:

https://s3.amazonaws.com/h2o-public-test-data/bigdata/laptop/mnist/test.csv.gz

Once you get it to run it should look something like this:

```
15:11:53.001 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9949 Actual=0 Predicted=0 in 5 ms
15:11:53.004 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9950 Actual=2 Predicted=2 in 3 ms
15:11:53.007 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9951 Actual=8 Predicted=8 in 3 ms
15:11:53.011 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9952 Actual=4 Predicted=4 in 4 ms
15:11:53.016 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9953 Actual=3 Predicted=3 in 5 ms
15:11:53.019 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9954 Actual=3 Predicted=3 in 3 ms
15:11:53.023 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9955 Actual=7 Predicted=7 in 4 ms
15:11:53.027 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9956 Actual=1 Predicted=1 in 4 ms
15:11:53.032 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9957 Actual=1 Predicted=1 in 4 ms
15:11:53.036 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9958 Actual=0 Predicted=0 in 3 ms
15:11:53.041 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9959 Actual=7 Predicted=7 in 4 ms
15:11:53.045 [main] INFO org.theberlins.citest.ImageRecognitionClientApplication - Sample=9960 Actual=2 Predicted=2 in 4 ms
```

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

### GitHub pipeline
Create a new Jenkins pipeline and set up the connection to the Git repository. To do this you also have to setup up credentials to connect to your repository. My setup looks like this: 
![Alt text](https://github.com/jonasberlinma/ci-demo/blob/master/images/Pipeline.png)

### Configure Kubernetes credentials
The Kubernetes configuration is a little bit more tricky as you have to make sure the Jenkins build machine has access to the Kubernetes certs. My setup looks like this:
![Alt text](https://github.com/jonasberlinma/ci-demo/blob/master/images/KubeCreds.png)
