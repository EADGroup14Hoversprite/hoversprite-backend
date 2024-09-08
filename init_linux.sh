@echo off
REM RUN THIS FILE TO START THE SERVER ON WINDOWS

REM Check if PostgreSQL Docker image exists
echo Checking for PostgreSQL Docker image...
docker images -q postgres:latest > nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo PostgreSQL image not found. Pulling from Docker Hub...
    docker pull postgres:latest
    if %ERRORLEVEL% EQU 0 (
        echo PostgreSQL image pulled successfully.
    ) else (
        echo Failed to pull PostgreSQL image. Exiting.
        exit /b 1
    )
) else (
    echo PostgreSQL image already exists.
)

REM Build the Hoversprite Docker image
echo Building the Spring Boot Docker image...
docker build -t hoversprite-backend:latest .
if %ERRORLEVEL% NEQ 0 (
    echo Failed to build Spring Boot Docker image. Exiting.
    exit /b 1
)

REM Start Docker Compose
echo Starting Docker Compose...
docker-compose up
