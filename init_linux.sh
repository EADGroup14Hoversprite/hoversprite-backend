#!/bin/bash

# RUN THIS FILE TO START THE SERVER ON LINUX

## Export environment variables for PostgreSQL (these are for local usage, not Docker Compose)
export POSTGRES_DB=hoversprite
export POSTGRES_USER=hoversprite_admin
export POSTGRES_PASSWORD=enterprisehd

echo "Environment variables set."

## Build the JAR file
echo "Building JAR file..."
mvn clean package

if [ $? -eq 0 ]; then
    echo "JAR file built successfully"
else
    echo "Failed to build JAR file. Please try again."
    exit 1
fi

## Check if PostgreSQL Docker image exists
echo "Checking for PostgreSQL Docker image..."
if [[ "$(docker images -q postgres:latest 2>/dev/null)" == "" ]]; then
  echo "PostgreSQL image not found. Pulling from Docker Hub..."
  docker pull postgres:latest

  if [ $? -eq 0 ]; then
    echo "PostgreSQL image pulled successfully."
  else
    echo "Failed to pull PostgreSQL image. Exiting."
    exit 1
  fi
else
  echo "PostgreSQL image already exists."
fi

## Build the Hoversprite Docker image
echo "Building the Spring Boot Docker image..."
docker build -t hoversprite-backend:latest .

if [ $? -ne 0 ]; then
  echo "Failed to build Spring Boot Docker image. Exiting."
  exit 1
fi

## Start Docker Compose
echo "Starting Docker Compose..."
sudo docker-compose up
