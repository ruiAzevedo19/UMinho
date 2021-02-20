#!/bin/bash

# ---------------------------------------------------
#    CLEAN DATABASE AND CREATE A NEW ONE BY A GIVEN 
#             NUMBER OF WAREHOUSES - SCRIPT
#                     ABD UMINHO
# ---------------------------------------------------

helpFunction(){
   echo ""
   echo "Usage: $0 -s dbServerName -w nrWarehouses"
   echo -e "\t-s Define the PostgreSQL DB Server Name"
   echo -e "\t-w Define the Number of Warehouses"
   exit 1 # Exit script after printing help
}

while getopts "s:w:" opt
do
   case "$opt" in
      w ) nrWarehouses="$OPTARG" ;;
      s ) dbServerName="$OPTARG" ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

# Print helpFunction in case number of warehouses or db server name was not provided
if [ -z "$nrWarehouses" ] || [ -z "$dbServerName" ]
then
   echo "Some or all of the parameters are empty.";
   helpFunction
fi

# Begin script in case all parameters are correct

# Drop database
dropdb -h $dbServerName tpcc

# Run the script to create the db again
sh ~/scripts/auxiliary_scripts/createdb.sh $dbServerName $nrWarehouses

