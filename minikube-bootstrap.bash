#!/bin/bash

jenkins_container=df9149f08bf8

# Start docker

docker-machine start dev

docker start ${jenkins_container}

# Wipe the current cluster and create a new one
minikube stop

minikube delete
minikube start -p minikube --vm-driver=virtualbox

# Create the initial deployment
kubectl create --save-config deployment image-recognizer-deployment --image=docker.io/jonasberlin/ci-demo:125

# Expose the deployment in a new service
kubectl expose deployment image-recognizer-deployment --type=NodePort --port=8080

# Enamble and configure ingress
minikube addons enable ingress

kubectl apply -f ingress.yaml

# Figure out the running docker-machine (not minikube's) which is running Jenkins
eval $(docker-machine env dev)

# Get the Jenkins container ID. This should be doable dynamically
jenkins_container=df9149f08bf8

# Copy the access keys to the docker
docker exec -it -u 0 $jenkins_container mkdir -p /Users/jonas/.minikube

docker cp ~/.minikube/ca.crt ${jenkins_container}:/Users/jonas/.minikube/
docker cp ~/.minikube/client.crt ${jenkins_container}:/Users/jonas/.minikube/
docker cp ~/.minikube/client.key ${jenkins_container}:/Users/jonas/.minikube/

docker exec -it -u 0 $jenkins_container chown -R 1000:1000 '/Users/jonas/.minikube/'

# Get the cluster IP and set it in /etc/hosts to make sure clients can connect through a predicable URL

minikube status

minikube dashboard &


