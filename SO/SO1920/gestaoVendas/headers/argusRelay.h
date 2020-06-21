#ifndef _COMMUNICATION_H_
#define _COMMUNICATION_H_

#include "request.h"
#include "answer.h"


/* --- Struct for ArgusRelay --------- */
typedef struct argusRelay *ArgusRelay;
/* -------------------------------------- */


/**
 * Initializes argusRelay structure 
 *
 * @params int request fifo descriptor
 * @params int ansert fifo descriptor
 * @returns argusRelay structure
 */
ArgusRelay initArgusRelay(int,int);

/**
 * Destroys ArgusRelay data structure 
 *
 * @params ArgusRelay structure to destroy 
 */ 
void destroyArgusRelay(ArgusRelay);

/**
 * Executes a single command from the command line 
 *
 * @params ArgusRelay argusRelay structure  
 * @param char** list of commands given by the user from command line 
 * @param int number of arguments
 */
void cmdln(ArgusRelay, char**, int);

/**
 * Provides a bash environment 
 */
void bash(ArgusRelay);


#endif
