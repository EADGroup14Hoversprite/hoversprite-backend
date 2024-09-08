@echo off
echo RUN THIS FILE TO START THE SERVER ON WINDOWS

:: Check if Maven build was successful
if %errorlevel% equ 0 (
    echo JAR file built successfully
) else (
    echo Failed to build JAR file. Please try again.
    exit /b 1
)

:: This section will check for the PostgreSQL Docker image and pull if not found
echo Checking for PostgreSQL Docker image...
docker images postgres:latest >nul 2>&1
if %errorlevel% neq 0 (
    echo PostgreSQL image not found. Pulling from Docker Hub...
    docker pull postgres:latest

    :: Check if Docker pull was successful
    if %errorlevel% equ 0 (
        echo PostgreSQL image pulled successfully.
    ) else (
        echo Failed to pull PostgreSQL image. Exiting.
        exit /b 1
    )
) else (
    echo PostgreSQL image already exists.
)

:: This section will build the Hoversprite Docker image
echo Building the Spring Boot Docker image...
docker build -t hoversprite-backend:latest .

:: This section will start docker-compose
echo Starting Docker Compose...
docker-compose up