#!/bin/bash

# ---------------------------------------------------
#    GOOGLE CLOUD LOCAL DISKS CONFIGURATION SCRIPT
#                  ABD UMINHO
# ---------------------------------------------------

# Create the file system
sudo mkfs.ext4 -F /dev/nvme0n1

# Create the directory
sudo mkdir -p /mnt/disks/postgresql

# Mount the filesystem
sudo mount /dev/nvme0n1 /mnt/disks/postgresql

# Give permissions to the mounted directory
sudo chmod a+rwx /mnt/disks/postgresql