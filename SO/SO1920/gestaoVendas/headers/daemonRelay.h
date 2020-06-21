#ifndef _DAEMON_RELAY_
#define _DAEMON_RELAY_

typedef struct daemonRelay *DaemonRelay;

/**
 * Creates pointer to daemonRelay
 *
 * @param int named pipe to writee
 * @returns DaemonRelay
 */
DaemonRelay initDaemonRelay(int);

/**
 * Destroys daemonRelay 
 * @param DaemonRelay structure to delete
 */
void destroyDaemonRelay(DaemonRelay);

/**
 * Sends a list of the current running commands
 *
 * @param DaemonRelay daemon communication
 * @param char** list of running commands 
 * @param int number of commands running 
 * @param int size of the list 
 *
 */
void sendList(DaemonRelay,char**,int,int);

/**
 * Sends history 
 *
 * @param DaemonRelay daemon communication
 * @param char** list of history commands 
 * @param int number of commands  
 * @param int number of commands 
 */
void sendHistory(DaemonRelay,const char*);

/**
 * Sends the output of a certain job
 *
 * @param DaemonRelay relay structure 
 * @param const char* path to output file 
 * @param const char* path to index file 
 * @param int job number 
 */
void sendOutput(DaemonRelay,const char*, const char*, int);

/**
 * Creates a history message
 *
 * @param int type of termination 
 * @param int number of process 
 * @param char* command 
 * @returns history message 
 */
char* createHistoryMessage(int,int,char*);

#endif
