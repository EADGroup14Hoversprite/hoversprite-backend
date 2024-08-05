@echo off

:: Variables
set DB_NAME=hoversprite
set DB_USER=hoversprite_admin
set DB_PASSWORD=enterprisehd
set DB_HOST=localhost
set DB_PORT=5432
set SUPERUSER=postgres

:: Check if the database exists
for /f %%i in ('psql -U %SUPERUSER% -h %DB_HOST% -tAc "SELECT 1 FROM pg_database WHERE datname='%DB_NAME%'"') do set DB_EXISTS=%%i

:: Create the database if it doesn't exist
if "%DB_EXISTS%" neq "1" (
    psql -U %SUPERUSER% -h %DB_HOST% -c "CREATE DATABASE %DB_NAME%"
)

:: Create the role and grant privileges
psql -U %SUPERUSER% -h %DB_HOST% -d %DB_NAME% -c "DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '%DB_USER%') THEN
        CREATE ROLE %DB_USER% LOGIN PASSWORD '%DB_PASSWORD%';
    END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE %DB_NAME% TO %DB_USER%;

GRANT USAGE, CREATE ON SCHEMA public TO %DB_USER%;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO %DB_USER%;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO %DB_USER%;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO %DB_USER%;"