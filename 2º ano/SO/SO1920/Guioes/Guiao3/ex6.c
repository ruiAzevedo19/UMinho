#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>

static char** split(char* command){
    int i = 0;
    char** argv = malloc(sizeof(char*) * 100);

    char* c = strtok(command, " ");
    while( c ){
        c = strtok(NULL," "); 
        argv[i++] = strdup(c); 
    }
    argv[i] = 0;

    return argv;
}

int sys(char* command){
    int status;
    pid_t pid;
    char** argv = split(command);

    if( !(pid = fork()) ){
        execlp(argv[0],argv[0],argv + 1);
        _exit(-1);
    }
    else
        wait(&status);
    
    return (WEXITSTATUS(status) == -1) ? -1 : 0; 
}

int main(){
   /* por acabar */ 

	return 0;
}

