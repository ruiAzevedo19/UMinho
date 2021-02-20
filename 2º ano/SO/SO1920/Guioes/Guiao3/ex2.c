#include <stdio.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/wait.h>

int main(){
    pid_t pid;
    int status;

    if( !(pid = fork()) ){
        execl("/bin/ls", "/bin/ls", "-l", NULL);
        _exit(0);
    }
    else{
        wait(&status);
        if( WIFEXITED(status) )
            puts("Sucess");
        else 
            puts("Child error");
    }
    return 0;
}
