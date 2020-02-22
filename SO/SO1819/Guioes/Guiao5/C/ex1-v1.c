#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

#define BUFFER_SIZE 1024
#define READ_END    0 
#define WRITE_END   1

int main(){
	pid_t pid;
	int fd[2];
	char msg[BUFFER_SIZE] = "Greetings Mr. Pipe";
	char buf[BUFFER_SIZE];

	if( pipe(fd) == -1 ){
		perror("Pipe failded!");
		return 1;
	}

	pid = fork();
	if( pid == -1 ){
		perror("Fork failed!");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		/* Closes the read end of the pipe */
		close(fd[READ_END]);
		/* Writes the message in the write end of the pipe */
		write(fd[WRITE_END],msg,strlen(msg) + 1);
		/* Closes de write end of the pipe */
		close(fd[WRITE_END]);
	}
	/* Child process */
	else{
		/* Closes the write end of the pipe */
		close(fd[WRITE_END]);
		/* Reads from the read end of the pipe */
		read(fd[READ_END],buf,BUFFER_SIZE);
		printf("%s\n",buf);
		/* Closes the read end of the pipe */
		close(fd[READ_END]);
	}
	return 0;
}
