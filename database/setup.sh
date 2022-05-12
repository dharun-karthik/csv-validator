#!/bin/bash
brew install postgresql
brew services start postgresql
createuser db_user
psql postgres -c "ALTER USER db_user WITH ENCRYPTED PASSWORD 'database-user'"
psql postgres -c "CREATE DATABASE csv_validator"
psql postgres -c "GRANT ALL PRIVILEGES ON DATABASE csv_validator to db_user"
psql db_user -d csv_validator -f create_fields_table.sql