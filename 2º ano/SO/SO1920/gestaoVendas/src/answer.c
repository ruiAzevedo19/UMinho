#include "../headers/answer.h"

#include <stdlib.h>
#include <string.h>

/* --- Answer structure ------------------------------------------ */
struct answer{
    char type;
    int output;
};
/* --------------------------------------------------------------- */

/* --- Constructor & Destructor ---------------------------------- */ 
Answer createAnswer(){
    return malloc(sizeof(struct answer));
}

Answer initAnswer(char type, int output){
    Answer a = malloc(sizeof(struct answer));
    a->type = type;
    a->output = output;
    return a;
}

void destroyAnswer(Answer a){
    free(a);
}

/* --- Getters --------------------------------------------------- */

char getAnswerType(Answer a){
    return a->type;
}

int getAnswerOutput(Answer a){
    return a->output;
}

int answerSize(){
    return sizeof(struct answer);
}
