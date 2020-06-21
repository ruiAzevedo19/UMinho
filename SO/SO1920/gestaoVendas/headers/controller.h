#ifndef _CONTROLLER_H_
#define _CONTROLLER_H_ 

typedef struct controller *Controller;

/*
 * Initializes controller structure
 *
 * @param int job number
 * @param char* command to execute 
 * @param ti inactivity time 
 * @param te execution time 
 * @param int pipe for output process
 * @returns controller structure 
 */
Controller initController(int,char*,int,int,int);

/**
 * Destroys a controller structure 
 */
void destroyController(Controller);

/**
 * Executes controller 
 *
 * @param Controller controller to execute  
 */
void execute(Controller);

#endif 
