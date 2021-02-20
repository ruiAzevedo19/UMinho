#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){
	pid_t pid;
	int status, i;

	for(i = 0; i < 10; i++){
		pid = fork();
		if( pid == -1 ){
			perror("Fork failed!");
			return 1;
		}
		if( pid == 0 ){
			printf("--- (%d) Child ---\n",i);
			printf("PID := %d   |   PPID := %d\n", getpid(),getppid());
		}
		else{
			wait(NULL);
			_exit(0);
		}
	}
	return 0;
}

