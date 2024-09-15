#!/bin/bash

# RUN THIS FILE TO BUILD AND UPLOAD THE DOCKER IMAGE FOR THE SPRING BOOT APP

# Define variables
IMAGE_NAME="hoversprite-backend"
DOCKER_USERNAME="phucdoan2003"
DOCKER_REGISTRY="docker.io"
TAG="latest"

# Build the Spring Boot app JAR
echo "Building the Spring Boot app JAR..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
  echo "Failed to build the Spring Boot app. Exiting."
  exit 1
fi

# Build the Docker image
echo "Building the Docker image..."
docker build -t ${IMAGE_NAME}:${TAG} .

if [ $? -ne 0 ]; then
  echo "Failed to build the Docker image. Exiting."
  exit 1
fi

# Tag the Docker image for the registry
echo "Tagging the Docker image for the registry..."
docker tag ${IMAGE_NAME}:${TAG} ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}

# Log in to Docker (if needed, this can be skipped if already logged in)
echo "Logging in to Docker..."
docker login ${DOCKER_REGISTRY}

if [ $? -ne 0 ]; then
  echo "Failed to login to Docker. Exiting."
  exit 1
fi

# Push the Docker image to the registry
echo "Pushing the Docker image to the registry..."
docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${TAG}

if [ $? -ne 0 ]; then
  echo "Failed to push the Docker image. Exiting."
  exit 1
fi

echo "Docker image uploaded successfully."
