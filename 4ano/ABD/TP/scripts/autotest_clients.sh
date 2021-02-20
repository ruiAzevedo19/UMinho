#!/bin/bash

dbUser=$1
nrWarehouses=$2
backupFile=$3

~/scripts/auxiliary_scripts/autorun_clients.sh server-10-cli $dbUser $nrWarehouses $backupFile 10 clients "$nrWarehouses"warehouses_10clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-20-cli $dbUser $nrWarehouses $backupFile 20 clients "$nrWarehouses"warehouses_20clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-30-cli $dbUser $nrWarehouses $backupFile 30 clients "$nrWarehouses"warehouses_30clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-40-cli $dbUser $nrWarehouses $backupFile 40 clients "$nrWarehouses"warehouses_40clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-50-cli $dbUser $nrWarehouses $backupFile 50 clients "$nrWarehouses"warehouses_50clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-60-cli $dbUser $nrWarehouses $backupFile 60 clients "$nrWarehouses"warehouses_60clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-70-cli $dbUser $nrWarehouses $backupFile 70 clients "$nrWarehouses"warehouses_70clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-80-cli $dbUser $nrWarehouses $backupFile 80 clients "$nrWarehouses"warehouses_80clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-90-cli $dbUser $nrWarehouses $backupFile 90 clients "$nrWarehouses"warehouses_90clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-100-cli $dbUser $nrWarehouses $backupFile 100 clients "$nrWarehouses"warehouses_100clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-110-cli $dbUser $nrWarehouses $backupFile 110 clients "$nrWarehouses"warehouses_110clients

~/scripts/auxiliary_scripts/autorun_clients.sh server-120-cli $dbUser $nrWarehouses $backupFile 120 clients "$nrWarehouses"warehouses_120clients
