#!/bin/bash

# -----------------------------------------------------
#       BENCHMARK SERVER CONFIGURATION - SCRIPT
#                   ABD UMINHO
# -----------------------------------------------------

helpFunction(){
   echo ""
   echo "Usage: $0 -s dbServerName -u dbUser -w nrWarehouses"
   echo -e "\t-s Define the server name"
   echo -e "\t-u Define the database user"
   echo -e "\t-w Define the Number of Warehouses"
   exit 1 # Exit script after printing help
}

while getopts "s:u:w:" opt
do
   case "$opt" in
      s ) dbServerName="$OPTARG" ;;
      u ) dbUser="$OPTARG" ;;
      w ) nrWarehouses="$OPTARG" ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

# Print helpFunction in case number of db server name, db user and number of warehouses was not provided
if [ -z "$dbServerName" ] || [ -z "$dbUser" ] || [ -z "$nrWarehouses" ]
then
   echo "Some or all of the parameters are empty.";
   helpFunction
fi

# Update packages
yes | sudo apt-get update

# Install Java
yes | sudo apt-get install openjdk-8-jdk

# Install pip
yes | sudo apt install python3-pip

# Install scipy
yes | pip3 install scipy

# Move file showtpc.py to the directory results
mkdir -p ~/results
cp ~/scripts/files/showtpc.py ~/results

# Untar files
tar xvf ~/scripts/files/tpc-c-0.1-SNAPSHOT-tpc-c.tar.gz -C ~/
tar xvf ~/scripts/files/extra.tgz -C ~/

# Cd to the tpcc directory
cd ~/tpc-c-0.1-SNAPSHOT

# Define the database connection address
sed -i.bak "s#^db.connection.string=.*#db.connection.string=jdbc:postgresql://${dbServerName}/tpcc#g" etc/database-config.properties

# Define the database username
sed -i.bak "s/^db.username=.*/db.username=${dbUser}/g" etc/database-config.properties

# Define the database password
sed -i.bak "s/^db.password=.*/db.password=/g" etc/database-config.properties

# Install postgresql client
yes | sudo apt-get install postgresql-client-12

# Run the script to create a new db
sh ~/scripts/auxiliary_scripts/createdb.sh $dbServerName $nrWarehouses
