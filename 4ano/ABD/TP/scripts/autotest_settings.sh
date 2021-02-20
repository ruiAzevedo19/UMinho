#!/bin/bash

dbUser=$1
nrWarehouses=$2
backupFile=$3
nrClients=$4

######################################################
#                         FSYNC                      #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-1 $dbUser $nrWarehouses $backupFile $nrClients "#fsync =" "fsync = off" settings/fsync fsync

######################################################
#                  SYNCHRONOUS COMMIT                #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-2 $dbUser $nrWarehouses $backupFile $nrClients "#synchronous_commit =" "synchronous_commit = off" settings/synchronous_commit synchronous_commit_off

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-3 $dbUser $nrWarehouses $backupFile $nrClients "#synchronous_commit =" "synchronous_commit = remote_write" settings/synchronous_commit synchronous_commit_remote_write

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-4 $dbUser $nrWarehouses $backupFile $nrClients "#synchronous_commit =" "synchronous_commit = local" settings/synchronous_commit synchronous_commit_local

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-5 $dbUser $nrWarehouses $backupFile $nrClients "#synchronous_commit =" "synchronous_commit = remote_apply" settings/synchronous_commit synchronous_commit_remote_apply

######################################################
#                     WAL SYNC METHOD                #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-6 $dbUser $nrWarehouses $backupFile $nrClients "#wal_sync_method =" "wal_sync_method = fsync" settings/wal_sync_method wal_sync_method_fsync

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-7 $dbUser $nrWarehouses $backupFile $nrClients "#wal_sync_method =" "wal_sync_method = open_datasync" settings/wal_sync_method wal_sync_method_open_datasync

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-8 $dbUser $nrWarehouses $backupFile $nrClients "#wal_sync_method =" "wal_sync_method = open_sync" settings/wal_sync_method wal_sync_method_open_sync

######################################################
#                    FULL PAGE WRITES                #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-9 $dbUser $nrWarehouses $backupFile $nrClients "#full_page_writes =" "full_page_writes = off" settings/full_page_writes full_page_writes_off

######################################################
#                       WAL BUFFERS                  #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-10 $dbUser $nrWarehouses $backupFile $nrClients "#wal_buffers =" "wal_buffers = 2MB" settings/wal_buffers wal_buffers_2MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-11 $dbUser $nrWarehouses $backupFile $nrClients "#wal_buffers =" "wal_buffers = 4MB" settings/wal_buffers wal_buffers_4MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-12 $dbUser $nrWarehouses $backupFile $nrClients "#wal_buffers =" "wal_buffers = 8MB" settings/wal_buffers wal_buffers_8MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-13 $dbUser $nrWarehouses $backupFile $nrClients "#wal_buffers =" "wal_buffers = 16MB" settings/wal_buffers wal_buffers_16MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-14 $dbUser $nrWarehouses $backupFile $nrClients "#wal_buffers =" "wal_buffers = 32MB" settings/wal_buffers wal_buffers_32MB

######################################################
#                       COMMIT DELAY                 #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-15 $dbUser $nrWarehouses $backupFile $nrClients "#commit_delay =" "commit_delay = 10" settings/commit_delay commit_delay_10

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-16 $dbUser $nrWarehouses $backupFile $nrClients "#commit_delay =" "commit_delay = 200" settings/commit_delay commit_delay_200

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-17 $dbUser $nrWarehouses $backupFile $nrClients "#commit_delay =" "commit_delay = 500" settings/commit_delay commit_delay_500

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-18 $dbUser $nrWarehouses $backupFile $nrClients "#commit_delay =" "commit_delay = 1000" settings/commit_delay commit_delay_1000

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-19 $dbUser $nrWarehouses $backupFile $nrClients "#commit_delay =" "commit_delay = 1500" settings/commit_delay commit_delay_1500

######################################################
#                       COMMIT SIBLINGS              #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-20 $dbUser $nrWarehouses $backupFile $nrClients "#commit_siblings =" "commit_siblings = 2" settings/commit_siblings commit_siblings_2

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-21 $dbUser $nrWarehouses $backupFile $nrClients "#commit_siblings =" "commit_siblings = 4" settings/commit_siblings commit_siblings_4

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-22 $dbUser $nrWarehouses $backupFile $nrClients "#commit_siblings =" "commit_siblings = 8" settings/commit_siblings commit_siblings_8

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-23 $dbUser $nrWarehouses $backupFile $nrClients "#commit_siblings =" "commit_siblings = 16" settings/commit_siblings commit_siblings_16

######################################################
#                        WAL LEVEL                   #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-24 $dbUser $nrWarehouses $backupFile $nrClients "#wal_level =" "wal_level = minimal" settings/wal_level wal_level_minimal

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-25 $dbUser $nrWarehouses $backupFile $nrClients "#wal_level =" "wal_level = logical" settings/wal_level wal_level_logical

######################################################
#                    WAL WRITER DELAY                #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-26 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_delay =" "wal_writer_delay = 100ms" settings/wal_writer_delay wal_writer_delay_100

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-27 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_delay =" "wal_writer_delay = 500ms" settings/wal_writer_delay wal_writer_delay_500

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-28 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_delay =" "wal_writer_delay = 1000ms" settings/wal_writer_delay wal_writer_delay_1000

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-29 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_delay =" "wal_writer_delay = 2000ms" settings/wal_writer_delay wal_writer_delay_2000

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-30 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_delay =" "wal_writer_delay = 5000ms" settings/wal_writer_delay wal_writer_delay_5000

######################################################
#                 WAL WRITER FLUSH AFTER             #
######################################################

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-31 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 2MB" settings/wal_writer_flush_after wal_writer_flush_after_2MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-32 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 4MB" settings/wal_writer_flush_after wal_writer_flush_after_4MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-33 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 8MB" settings/wal_writer_flush_after wal_writer_flush_after_8MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-34 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 16MB" settings/wal_writer_flush_after wal_writer_flush_after_16MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-35 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 32MB" settings/wal_writer_flush_after wal_writer_flush_after_32MB

~/scripts/auxiliary_scripts/autorun_optimizations.sh server-settings-36 $dbUser $nrWarehouses $backupFile $nrClients "#wal_writer_flush_after =" "wal_writer_flush_after = 64MB" settings/wal_writer_flush_after wal_writer_flush_after_64MB