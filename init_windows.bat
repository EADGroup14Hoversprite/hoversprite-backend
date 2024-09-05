@echo off

:: RUN THIS FILE TO START THE SERVER ON WINDOWS

:: This section will check for the PostgreSQL Docker image
echo Checking for PostgreSQL Docker image...
docker images -q postgres:latest > nul 2>&1
if %errorlevel% neq 0 (
    echo PostgreSQL image not found. Pulling from Docker Hub...
    docker pull postgres:latest
    if %errorlevel% neq 0 (
        echo Failed to pull PostgreSQL image. Exiting.
        exit /b 1
    ) else (
        echo PostgreSQL image pulled successfully.
    )
) else (
    echo PostgreSQL image already exists.
)

:: This section will build the Hoversprite Docker image
echo Building the Spring Boot Docker image...
docker build -t hoversprite-backend:latest .
if %errorlevel% neq 0 (
    echo Failed to build the Docker image. Exiting.
    exit /b 1
)

:: This section will start docker-compose
echo Starting Docker Compose...
docker-compose up
if %errorlevel% neq 0 (
    echo Failed to start Docker Compose. Exiting.
    exit /b 1
)
