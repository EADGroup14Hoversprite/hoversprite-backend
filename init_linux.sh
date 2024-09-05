#!/bin/bash

# RUN THIS FILE TO START THE SERVER ON LINUX

## This section will pull the postgres official Docker image if it does not exist on the machine
echo "Checking for PostgreSQL Docker image..."
if [[ "$(docker images -q postgres:latest 2>/dev/null)" == "" ]]; then
  echo "PostgreSQL image not found. Pulling from Docker Hub..."
  docker pull postgres:latest

  # Check if the pull was successful
  if [ $? -eq 0 ]; then
    echo "PostgreSQL image pulled successfully."
  else
    echo "Failed to pull PostgreSQL image. Exiting."
    exit 1
  fi
else
  echo "PostgreSQL image already exists."
fi

## This section will build the Hoversprite Docker image

echo "Building the Spring Boot Docker image..."
docker build -t hoversprite-backend:latest .

## This section will start docker-compose

echo "Starting Docker Compose..."
sudo docker-compose up