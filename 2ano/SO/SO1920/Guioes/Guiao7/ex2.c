#include <signal.h>
#include <sys/types.h>
#include <time.h>
#include <stdio.h>

int run = 1;
int count = 0;
double start, measure;

void ctrl_c_handler(int signal){
    measure = (clock() - start) / CLOCKS_PER_SEC;
    count++;
    printf("\n%lfs\n",measure);
}

void ctrl_slash_handler(int signal){
   printf("\nCtrl + c pressed %d times\n",count); 
}

int main(){
    start = clock(); 

    signal(SIGINT,ctrl_c_handler);
    signal(SIGQUIT,ctrl_slash_handler);

    while( run );

    return 0;
}

