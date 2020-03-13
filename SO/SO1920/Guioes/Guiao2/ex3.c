#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

// Nota: a macro WEXITSTATUS permite aceder ao valor de saida de um processo filho

int main(){
    pid_t pid;
    int i;
    int status;

    for(i = 0; i < 10; i++){
       pid = fork();
       if( !pid ){
           // child process
           printf("PID = %d\nPPID = %d\n",getpid(), getppid());
           sleep(5);
           _exit(i);
       }
       else{
           // parent process 
           pid = wait(&status);
           printf("Child %d finished! Exit code %d\n", pid, WEXITSTATUS(status));
       }
    }
	return 0;
}

