#!/usr/bin/env bash

echo "deploying the application to Kubernetes"
cd $(dirname $0 ) 
HERE=$( pwd )

NS=cnj
APP_NAME=actuator
MANIFESTS_DIR=$HERE/k8s/manifests
IMAGE_NAME=gcr.io/bootiful/cnj-${APP_NAME}:latest
mkdir -p $MANIFESTS_DIR
kubectl delete ns/cnj
./mvnw -DskipTests=true clean package spring-boot:build-image -Dspring-boot.build-image.imageName=${IMAGE_NAME}
docker push $IMAGE_NAME
sleep 5
kubectl apply -f "$MANIFESTS_DIR/namespace.yaml"
kubectl apply -f "$MANIFESTS_DIR/deployment.yaml"
kubectl apply -f "$MANIFESTS_DIR/service.yaml"
sleep 5
