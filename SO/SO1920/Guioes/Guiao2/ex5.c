#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

int main(){
    pid_t pid;

    for(int i = 0; i < 10; i++){
        pid = fork();
        if( pid == -1 ){
            perror("Fork failed!");
            return 1;
        }
        if( !pid ){
            printf("--- Child %d ---\n", i);
            printf("PPID = %d\nPID = %d\n",getppid(),getpid());
            sleep(5);
        }
        else{
            pid = wait(NULL);
            printf("Child awakes = %d\n", pid);
            _exit(0);
        }
    }
	return 0;
}

