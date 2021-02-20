#!/bin/bash

# Create a new server VM
echo ">>>>>>>>>>>>> Creating a new Server VM on Google Cloud."
gcloud beta compute --project=abd-2020-2021 instances create $1 --zone=us-central1-a --machine-type=n1-standard-2 --subnet=default --network-tier=PREMIUM --maintenance-policy=MIGRATE --service-account=536808376468-compute@developer.gserviceaccount.com --scopes=https://www.googleapis.com/auth/devstorage.read_only,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/monitoring.write,https://www.googleapis.com/auth/servicecontrol,https://www.googleapis.com/auth/service.management.readonly,https://www.googleapis.com/auth/trace.append --image=ubuntu-2004-focal-v20201211 --image-project=ubuntu-os-cloud --boot-disk-size=10GB --boot-disk-type=pd-ssd --boot-disk-device-name=$1 --local-ssd=interface=NVME --no-shielded-secure-boot --shielded-vtpm --shielded-integrity-monitoring --reservation-affinity=any

# Wait 90 seconds for instance to be on
sleep 90s

# Copy scripts to new server VM
echo ">>>>>>>>>>>>> Copying scripts folder to the new Server VM on Google Cloud."
gcloud compute scp --zone us-central1-a --recurse ~/scripts $1:

# Run the server configuration script
echo ">>>>>>>>>>>>> Running the initial configuration script on new Server."
gcloud compute ssh --zone us-central1-a $1 --command "~/scripts/dbserverconfiguration.sh -a $1 -n 10.128.0.0 -d -i"

# Connect to the remote server and execute the script to clean the DB
echo ">>>>>>>>>>>>> Going to clear the database on db server."
gcloud compute ssh --zone us-central1-a $1 --command "~/scripts/dbservercleanpostgresdata.sh -a $1 -n 10.128.0.0 -d"

# Wait 30 seconds
sleep 30s

# Execute the restore script
echo ">>>>>>>>>>>>> Restoring started."
~/scripts/restoredb.sh -s $1 -w $3 -b $4

# Stop the postgresql server
gcloud compute ssh --zone us-central1-a $1 --command '/usr/lib/postgresql/12/bin/pg_ctl stop -D /mnt/disks/postgresql/data'

# Edit the postgresql.conf with the optimization
gcloud compute ssh --zone us-central1-a $1 --command "sed -i.bak 's/^$6.*/$7/g' /mnt/disks/postgresql/data/postgresql.conf"

# Save a log with the edited file
gcloud compute ssh --zone us-central1-a $1 --command "mkdir -p ~/logs && cp /mnt/disks/postgresql/data/postgresql.conf ~/logs/$9.conf"

# Start the postgresql server
gcloud compute ssh --zone us-central1-a $1 --command '/usr/lib/postgresql/12/bin/postgres -D /mnt/disks/postgresql/data -k. </dev/null &>/dev/null &'

# Run the transaction script
echo ">>>>>>>>>>>>> Run transactional script."
~/scripts/runclients.sh -s $1 -u $2 -w $3 -c $5

# Copy the logs folder
echo ">>>>>>>>>>>>> Copying the logs folder from the database server."
gcloud compute scp --zone us-central1-a --recurse $1:~/logs ~/

# Delete the VM
echo ">>>>>>>>>>>>> Deleting the database server."
yes | gcloud compute instances delete $1 --zone us-central1-a

# Create dat results directory and change file name
echo ">>>>>>>>>>>>> Changing file name."
mkdir -p ~/dat_results/$8
mv ~/tpc-c-0.1-SNAPSHOT/TPCC*.dat ~/dat_results/$8/$9.dat

# Run script
echo ">>>>>>>>>>>>> Running showtpc.py"
mkdir -p ~/results/$8
~/results/showtpc.py -bc ~/dat_results/$8/$9.dat >> ~/results/$8/$9.txt