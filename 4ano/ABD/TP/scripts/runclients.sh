#!/bin/bash

# ---------------------------------------------------
#    RUN THE TRANSACTION SCRIPT FOR A GIVEN NUMBER
#         OF CLIENTS AND WAREHOUSES - SCRIPT
#                     ABD UMINHO
# ---------------------------------------------------

helpFunction(){
   echo ""
   echo "Usage: $0 -s dbServerName -w nrWarehouses -c nrClients"
   echo -e "\t-s Define the Database Server Name"
   echo -e "\t-w Define the Number of Warehouses"
   echo -e "\t-u Define the database user"
   echo -e "\t-c Define the Number of Clients"
   exit 1 # Exit script after printing help
}

while getopts "c:w:s:u:" opt
do
   case "$opt" in
      w ) nrWarehouses="$OPTARG" ;;
      s ) dbServerName="$OPTARG" ;;
      u ) dbUser="$OPTARG" ;;
      c ) nrClients="$OPTARG" ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

# Print helpFunction in case number of db server name or db user or warehouses or number of clients was not provided
if [ -z "$dbServerName" ] || [ -z "$dbUser" ] || [ -z "$nrWarehouses" ] || [ -z "$nrClients" ]
then
   echo "Some or all of the parameters are empty.";
   helpFunction
fi

# Begin script in case all parameters are correct

# Cd to the correct directory
cd ~/tpc-c-0.1-SNAPSHOT

# Define the database connection address
sed -i.bak "s#^db.connection.string=.*#db.connection.string=jdbc:postgresql://${dbServerName}/tpcc#g" etc/database-config.properties

# Define the database username
sed -i.bak "s/^db.username=.*/db.username=${dbUser}/g" etc/database-config.properties

# Define the number of warehouses
sed -i.bak "s/^tpcc.number.warehouses=.*/tpcc.number.warehouses=$nrWarehouses/g" etc/workload-config.properties

# Define the number of clients
sed -i.bak "s/^tpcc.numclients =.*/tpcc.numclients = $nrClients/g" etc/workload-config.properties

# Run the transaction script
echo ">>>>>>> Running the transaction script..."
sh ~/tpc-c-0.1-SNAPSHOT/run.sh
