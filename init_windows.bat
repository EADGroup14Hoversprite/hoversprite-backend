@echo off

:: This section will set all the environment variables necessary to build the JAR file

set POSTGRES_DB=hoversprite
set POSTGRES_USER=hoversprite_admin
set POSTGRES_PASSWORD=enterprisehd

set SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/hoverprite
set SPRING_DATASOURCE_USERNAME=hoversprite_admin
set SPRING_DATASOURCE_PASSWORD=enterprisehd

echo Environment variables set.

:: This section will pull the postgres official Docker image if it does not exist on the machine
echo Checking for PostgreSQL Docker image...
docker images -q postgres:latest >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo PostgreSQL image not found. Pulling from Docker Hub...
    docker pull postgres:latest

    :: Check if the pull was successful
    if %ERRORLEVEL% EQU 0 (
        echo PostgreSQL image pulled successfully.
    ) else (
        echo Failed to pull PostgreSQL image. Exiting.
        exit /b 1
    )
) else (
    echo PostgreSQL image already exists.
)

:: This section will build the JAR file

echo Building JAR file...
mvn clean package

if %ERRORLEVEL% EQU 0 (
    echo JAR file built successfully.
) else (
    echo Failed to build JAR file. Please try again.
    exit /b 1
)

:: This section will build the Spring Boot Docker image

echo Building the Spring Boot Docker image...
docker build -t hoversprite-backend:latest .

if %ERRORLEVEL% NEQ 0 (
    echo Failed to build the Spring Boot Docker image. Exiting.
    exit /b 1
)

:: This section will start docker-compose

echo Starting Docker Compose...
docker-compose up -d

if %ERRORLEVEL% EQU 0 (
    echo Docker Compose started successfully.
) else (
    echo Failed to start Docker Compose. Exiting.
    exit /b 1
)
