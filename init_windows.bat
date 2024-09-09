@echo off
REM RUN THIS FILE TO PULL IMAGES AND RUN THE SPRING BOOT APP USING DOCKER COMPOSE

SET DOCKER_COMPOSE_FILE=docker-compose.yml

REM Pull the latest images for all services
echo Pulling the latest images for all services...
docker-compose -f %DOCKER_COMPOSE_FILE% pull

if %ERRORLEVEL% NEQ 0 (
    echo Failed to pull images. Exiting.
    exit /b 1
)

REM Stop any running containers
echo Stopping any existing containers...
docker-compose -f %DOCKER_COMPOSE_FILE% down

REM Start services with Docker Compose
echo Starting the services with Docker Compose...
docker-compose -f %DOCKER_COMPOSE_FILE% up -d

if %ERRORLEVEL% NEQ 0 (
    echo Failed to start services. Exiting.
    exit /b 1
)

REM Show logs for the hoversprite-app service
echo Showing logs for the Spring Boot application...
docker-compose -f %DOCKER_COMPOSE_FILE% logs -f hoversprite-app
