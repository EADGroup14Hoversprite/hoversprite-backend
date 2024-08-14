#!/bin/bash

# RUN THIS FILE TO START THE SERVER ON LINUX

## This section will export all the environment variables necessary to build the JAR file

export POSTGRES_DB=hoversprite
export POSTGRES_USER=hoversprite_admin
export POSTGRES_PASSWORD=enterprisehd

export SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/hoversprite
export SPRING_DATASOURCE_USERNAME=hoversprite_admin
export SPRING_DATASOURCE_PASSWORD=enterprisehd

echo "Environment variables set."

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

## This section will build the JAR file

echo "Building JAR file..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "JAR file built successfully"
else
    echo "Failed to build JAR file. Please try again."
    exit 1
fi

## This section will build the Hoversprite Docker image

echo "Building the Spring Boot Docker image..."
docker build -t hoversprite-backend:latest .

## This section will start docker-compose

echo "Starting Docker Compose..."
sudo docker-compose up
if [ $? -eq 0 ]; then
    echo "Docker Compose started successfully"
else
    echo "Failed to start Docker Compose. Exiting"
    exit 1
fi
