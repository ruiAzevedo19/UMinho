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
			printf("--- (%d) Child ---\n", i);
			sleep(5);
			_exit(i);
		}
	}
	for(i = 0; i < 10; i++){
		pid = wait(&status);
		printf("Received child %d\n",WEXITSTATUS(status));
	}

	return 0;
}
