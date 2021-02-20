#!/bin/bash

dbUser=$1
nrWarehouses=$2
backupFile=$3
nrClients=$4

######################################################
#                   ARCHIVE MODE                     #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-1 $dbUser $nrWarehouses $backupFile $nrClients "#archive_mode =" "archive_mode = off" archiving/archive_mode archive_mode_off

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-2 $dbUser $nrWarehouses $backupFile $nrClients "#archive_mode =" "archive_mode = on" archiving/archive_mode archive_mode_on

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-3 $dbUser $nrWarehouses $backupFile $nrClients "#archive_mode =" "archive_mode = always" archiving/archive_mode archive_mode_always

######################################################
#                    ARCHIVE COMMAND                 #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-4 $dbUser $nrWarehouses $backupFile $nrClients "#archive_command =" "archive_command = '%p'" archiving/archive_command archive_command_p

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-5 $dbUser $nrWarehouses $backupFile $nrClients "#archive_command =" "archive_command = '%f'" archiving/archive_command archive_command_f


######################################################
#                     ARCHIVE TIMEOUT                #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-6 $dbUser $nrWarehouses $backupFile $nrClients "#archive_timeout =" "archive_timeout = 10" archiving/archive_timeout archive_timeout_10

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-7 $dbUser $nrWarehouses $backupFile $nrClients "#archive_timeout =" "archive_timeout = 60" archiving/archive_timeout archive_timeout_60

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-8 $dbUser $nrWarehouses $backupFile $nrClients "#archive_timeout =" "archive_timeout = 100" archiving/archive_timeout archive_timeout_100

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-archiving-9 $dbUser $nrWarehouses $backupFile $nrClients "#archive_timeout =" "archive_timeout = 120" archiving/archive_timeout archive_timeout_120
