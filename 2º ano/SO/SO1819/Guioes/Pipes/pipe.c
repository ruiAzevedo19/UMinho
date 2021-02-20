#include <unistd.h>
#include <stdio.h>
#include <string.h>

#define BUFFER_SIZE 30
#define READ_END    0
#define WRITE_END   1

int main(){
	pid_t pid;
	int fd[2];
	char message[BUFFER_SIZE] = "Greetings Mr. Pipe";
	char buf[BUFFER_SIZE];

	if( pipe(fd) == -1 ){
		perror("Pipe failed");
		return 1;
	}

	pid = fork();
	if( pid < 0 ){
		perror("Fork failed!");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		/* Close the read end of the pipe */
		close(fd[READ_END]);
		/* Writes the message in the write end of the pipe */
		write(fd[WRITE_END], message, strlen(message) + 1);
		/* Closes the write end of the pipe */
		close(fd[WRITE_END]);
	}
	/* Child process */
	else{
		/* Closes the write end of the pipe */
		close(fd[WRITE_END]);
		/*  */
		/* Closes the read end of the pipe */
		close(fd[READ_END]);
	}
	return 0;	
}
