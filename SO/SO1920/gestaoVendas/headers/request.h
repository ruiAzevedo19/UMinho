#ifndef _REQUEST_H_
#define _REQUEST_H_

/* --- Struct request ------- */
typedef struct request *Request;

/**
 * Creates an empty request 
 *
 * @returns empty request
 */
Request createRequest();

/**
 * Initializes a request 
 * @param char type of request
 * @param char[] command to execute
 * @param int argument value of request 
 * @returns reequest
 */
Request initRequest(char,char*,int);

/**
 * Destroys a request 
 *
 * @param Request request to be destroyed 
 */
void destroyRequest(Request);

/*
 * @param Request request 
 * @returns command of the request 
 */
char getRequestType(Request);

/*
 * @param Request request 
 * @returns args list of the request 
 */
char* getRequestArgs(Request);

/*
 * @param Request request 
 * @returns integer arg value 
 */
int getRequestValue(Request);

/**
 * @param Request request 
 * @returns size of request 
 */
int requestSize();

#endif

