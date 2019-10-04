#!/bin/bash

minikube delete
minikube start -p minikube

kubectl create --save-config deployment image-recognizer-deployment --image=docker.io/jonasberlin/ci-demo:125

kubectl expose deployment image-recognizer-deployment --type=LoadBalancer --port=8080

kubectl apply -f ingress.yaml

docker cp ~/.minikube/ca.crt df9149f08bf8:/Users/jonas/.minikube/
docker cp ~/.minikube/client.crt df9149f08bf8:/Users/jonas/.minikube/
docker cp ~/.minikube/client.key df9149f08bf8:/Users/jonas/.minikube/

docker exec -it -u 0 df9149f08bf8 chown -R 1000:1000 '/Users/jonas/.minikube/'


