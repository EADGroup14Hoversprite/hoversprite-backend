#!/bin/bash

# RUN THIS FILE TO PULL IMAGES AND RUN THE SPRING BOOT APP USING DOCKER COMPOSE

# Define variables
DOCKER_COMPOSE_FILE="docker-compose.yml"

# Pull the latest Docker images for all services in the docker-compose file
echo "Pulling the latest images for all services..."
docker-compose -f ${DOCKER_COMPOSE_FILE} pull

if [ $? -ne 0 ]; then
  echo "Failed to pull images. Exiting."
  exit 1
fi

# Check if any containers with the same name are already running and stop them
echo "Stopping any existing containers..."
docker-compose -f ${DOCKER_COMPOSE_FILE} down

# Run the Docker Compose file to start the services
echo "Starting the services with Docker Compose..."
docker-compose -f ${DOCKER_COMPOSE_FILE} up -d

if [ $? -ne 0 ]; then
  echo "Failed to start the services. Exiting."
  exit 1
fi

# Check the logs for the application container
echo "Showing logs for the Spring Boot application..."
docker-compose -f ${DOCKER_COMPOSE_FILE} logs -f hoversprite-app
