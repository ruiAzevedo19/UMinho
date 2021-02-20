#include "../headers/request.h"

#include <string.h>
#include <stdlib.h>

/* --- Request structure ----------------------------------------- */
struct request{       /*                                           */
    char type;        /* function to execute                       */
    char argv[512];   /* args of the function                      */
    int value;        /* arg for function that receives int as arg */
};                    /*                                           */
/* --------------------------------------------------------------- */


/* --- Construtor & Destructor ----------------------------------- */
Request createRequest(){
    return (Request)malloc(sizeof(struct request));
}

Request initRequest(char type, char* argv, int value){
    Request r = (Request)malloc(sizeof(struct request));

    r->type = type;
    if( argv )
        strcat(r->argv,argv);
    r->value = value;

    return r;
}

void destroyRequest(Request r){
    free(r);
}

/* --- Getters --------------------------------------------------- */
char getRequestType(Request r){
    return r->type;
}

char* getRequestArgs(Request r){
    return strdup(r->argv);
}

int getRequestValue(Request r){
    return r->value;
}

/* --- Functionality --------------------------------------------- */

int requestSize(){
    return sizeof(struct request);
}


