#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char** argv){
    pid_t pid;

    if( !(pid = fork()) ){
        strcpy(argv[0], "dif");
        execvp("./ex3",argv);
        _exit(0);
    }
    else
        wait(NULL);
    return 0;
}
