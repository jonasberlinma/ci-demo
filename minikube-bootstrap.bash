#!/bin/bash

minikube delete
minikube start -p minikube

kubectl create --save-config deployment image-recognizer-deployment --image=docker.io/jonasberlin/ci-demo:125

kubectl expose deployment image-recognizer-deployment --type=LoadBalancer --port=8080

kubectl apply -f ingress.yaml
