#!/bin/bash

# Variables
DB_NAME=hoversprite
DB_USER=hoversprite_admin
DB_PASSWORD=enterprisehd
DB_HOST=localhost
DB_PORT=5432
SUPERUSER=postgres

# Check if the database exists
DB_EXISTS=$(psql -U $SUPERUSER -h $DB_HOST -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'")

# Create the database if it doesn't exist
if [ "$DB_EXISTS" != "1" ]; then
  psql -U $SUPERUSER -h $DB_HOST -c "CREATE DATABASE $DB_NAME"
fi

# Create the role and grant privileges
psql -U $SUPERUSER -h $DB_HOST -d $DB_NAME <<EOF
DO
\$\$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '$DB_USER') THEN
      CREATE ROLE $DB_USER LOGIN PASSWORD '$DB_PASSWORD';
   END IF;
END
\$\$;

GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;

-- Grant necessary privileges on the public schema
GRANT USAGE, CREATE ON SCHEMA public TO $DB_USER;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO $DB_USER;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO $DB_USER;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO $DB_USER;
EOF
