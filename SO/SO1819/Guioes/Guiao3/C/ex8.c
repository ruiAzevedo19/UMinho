#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char **argv){
	pid_t pid;
	int *freq = calloc(argc - 1, sizeof(int));
	int ps;

	for(ps = 1; ps < argc; ps++){
		pid = fork();
		/* fork error */
		if( pid == -1 ){
			perror("Fork failed!");
			return 1;
		}
		/* parent process */
		if( pid > 0 ){
		
		}
		/* child process */
		else{
			
		}
			
	}

}
