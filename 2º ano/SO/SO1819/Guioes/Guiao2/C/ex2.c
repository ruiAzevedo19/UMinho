#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){
	pid_t pid;
	int status;

	pid = fork();
	if( pid == -1 ){
		perror("Fork failed!");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		wait(&status);
		printf("I\'am the father receiving the child ...!\n");
		printf("PID := %d\nCPID := %d\n", getpid(), pid);;
	}
	/* Child process */
	else{
		sleep(5);
		printf("I\'am the child!\n");
		printf("PID := %d\nPPID := %d\n", getpid(), getppid());
		_exit(0);
	}
	return 0;
}
