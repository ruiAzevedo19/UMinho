#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){
	pid_t pid;

	pid = fork();
	if( pid == -1 ){
		perror("Fork failed!");
		return 1;
	}
	/* Child process */
	if( pid == 0 ){
		printf("Child process\n\n");
		printf("Executing ls -l ...\n");
		sleep(2);
		/* Execute ls -l */
		execlp("ls","ls","-l",NULL);
		_exit(0);
	}
	/* Parent process */
	else{
		wait(NULL);
		printf("\nChild process finished ...\n");
	}
	return 0;
}
