#!/bin/bash

# ---------------------------------------------------
#                CREATE DATABASE SCRIPT
#                     ABD UMINHO
# ---------------------------------------------------

dbServerName=$1
nrWarehouses=$2
backupFile=$3

# Create a new database
createdb -h $dbServerName tpcc

# Load SQL scripts
cd ~/tpc-c-0.1-SNAPSHOT
psql -h $dbServerName tpcc < etc/sql/postgresql/createtable.sql
psql -h $dbServerName tpcc < etc/sql/postgresql/createindex.sql
for i in etc/sql/postgresql/*01; do psql -h $dbServerName tpcc < $i; done

# Define the number of warehouses
sed -i.bak "s/^tpcc.number.warehouses=.*/tpcc.number.warehouses=$nrWarehouses/g" etc/workload-config.properties

if [ -z "$backupFile" ]
then
    # Execute load script
    echo ">>>>>>>>> Executing load script..."
    sh ~/tpc-c-0.1-SNAPSHOT/load.sh

    # Create extra tables
    cd ~/extra
    psql -h $1 tpcc < createExtraTables.sql
else
    # Create extra tables
    cd ~/extra
    psql -h $1 tpcc < createExtraTables.sql

    # Restore the database
    echo ">>>>>>>>> Restore initiated..."
    pg_restore -h $dbServerName -c -d tpcc < $backupFile
fi