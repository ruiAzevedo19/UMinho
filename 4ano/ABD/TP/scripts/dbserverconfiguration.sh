#!/bin/bash

# -----------------------------------------------------
#    DATABASE SERVER CONFIGURATION, INITIATE, RECOVER
#          AND GOOGLE CLOUD LOCALDISKS OPTIONS
#                      ABD UMINHO
# -----------------------------------------------------

helpFunction(){
   echo ""
   echo "Usage: $0 -a listenAddress -n localNetwork -d(optional) -i(optional)"
   echo -e "\t-a Define the listen address (eg: server) at the postgresql.conf"
   echo -e "\t-n Define the local network (eg: 10.128.0.0) at the pg_hba.conf"
   echo -e "\t-d Option if we want to use local disks from Google Cloud"
   echo -e "\t-i Option if it's the initial configuration, if this options is not active then it will just recover from the reboot"
   exit 1 # Exit script after printing help
}

unset LOCALDISKS INITIAL_CONFIGURATION

while getopts "a:n:id" opt
do
   case "$opt" in
      a ) listenAddress="$OPTARG" ;;
      n ) localNetwork="$OPTARG" ;;
      i ) INITIAL_CONFIGURATION='yes' ;;
      d ) LOCALDISKS='yes' ;;
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

# Print helpFunction in case number of listen address or local network was not provided
if [ -z "$listenAddress" ] || [ -z "$localNetwork" ]
then
   echo "Some or all of the parameters are empty.";
   helpFunction
fi

# Verify if it's the initial configuration
if [ -n "$INITIAL_CONFIGURATION" ]
then
   # Update packages
   yes | sudo apt-get update

   # Install postgres 12
   yes | sudo apt-get install postgresql-12

   # Stop postgres
   sudo systemctl stop postgresql

   # Disable postgres
   sudo systemctl disable postgresql
fi

# Verify if the local disks option is active to change to the correct directory
if [ -n "$LOCALDISKS" ]
   then
      echo ">>>>>>>>>>> Using Google Cloud Local Disks."

      # Run the script to configure the local disks
      sh ~/scripts/auxiliary_scripts/localdisksconfiguration.sh

      # Change to the install directory
      cd /mnt/disks/postgresql/
   else
      # Change to the install directory
      cd
fi

# Create configuration data in the current dir
/usr/lib/postgresql/12/bin/initdb -D data

# Define the listening address
sed -i.bak "s/^#listen_addresses =.*/listen_addresses = 'localhost,$listenAddress'/g" data/postgresql.conf

# Define the listening address
echo -e "host    all             all             $localNetwork/10           trust" >> data/pg_hba.conf

# Initialize the server
/usr/lib/postgresql/12/bin/postgres -D data -k. </dev/null &>/dev/null &
