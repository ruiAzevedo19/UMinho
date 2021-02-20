#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

int main(int argc, char **argv){
	pid_t pid;
	int i;

	if( argc < 2 ){
		perror("No arguments specified!");
		return 1;
	}
	for(i = 1; i < argc; i++){
		pid = fork();
		if( pid == -1 ){
			perror("Fork failed!");
			return 1;
		}
		if( pid == 0 ){
			printf("Child %d executing ...\n", i);
			execl(argv[i],NULL);
		}
	}
	for(i = 1; i < argc; i++)
		wait(NULL);
	return 0;
	
}
