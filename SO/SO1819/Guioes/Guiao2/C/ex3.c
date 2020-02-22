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
		/* Parent process */
		if( pid > 0 ){
			pid = wait(&status);	
			printf("Received child %d ...\n", WEXITSTATUS(status));
		}
		/* Child process */
		else{
			printf("--- (%d) Child ---\n",i+1);
			printf("PID := %d  |  PPID := %d\n",getpid(),getppid());
			sleep(3);
			_exit(i+1);
		}
	}
	return 0;
}
