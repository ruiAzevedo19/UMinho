#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <time.h>
#include <stdio.h>

int run = 1;
int count, t = 0;

void ctrl_c_handler(int signal){
    count++;
    printf("\n%ds\n",t);
}

void ctrl_slash_handler(int signal){
   printf("\nCtrl + c pressed %d times\n",count); 
   run = 0;
}

void count_time_handler(int signal){
    t++;
}

int main(){
    signal(SIGINT,ctrl_c_handler);
    signal(SIGQUIT,ctrl_slash_handler);
    signal(SIGALRM,count_time_handler);

    alarm(1);
    while( run ){
        pause();
        alarm(1);
    }

    return 0;
}
