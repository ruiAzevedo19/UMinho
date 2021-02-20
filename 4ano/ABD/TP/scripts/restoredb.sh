#!/bin/bash

# ---------------------------------------------------
#        DROP DATABASE AND RESTORE THE DB FROM
#           A PREVIOUS BACKUP - SCRIPT
#                  ABD UMINHO
# ---------------------------------------------------

helpFunction(){
   echo ""
   echo "Usage: $0 -s dbServerName -w nrWarehouses -b backupFile"
   echo -e "\t-s Define the PostgreSQL DB Server Name"
   echo -e "\t-w Define the Number of Warehouses"
   echo -e "\t-b Define the backup file path"
   exit 1 # Exit script after printing help
}

while getopts "s:b:w:" opt
do
   case "$opt" in
      b ) backupFile="$OPTARG" ;;
      w ) nrWarehouses="$OPTARG" ;;
      s ) dbServerName="$OPTARG" ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

# Print helpFunction in case number of warehouses or db server name was not provided
if [ -z "$nrWarehouses" ] || [ -z "$backupFile" ] || [ -z "$dbServerName" ]
then
   echo "Some or all of the parameters are empty.";
   helpFunction
fi

# Begin script in case all parameters are correct

# Drop database
dropdb -h $dbServerName tpcc

backupFilePath=$(dirname $(readlink -f "$backupFile"))
backupFileName=$(basename -- "$backupFile")

# Run the script to create tables and restore the db
sh ~/scripts/auxiliary_scripts/createdb.sh $dbServerName $nrWarehouses "$backupFilePath/$backupFileName"