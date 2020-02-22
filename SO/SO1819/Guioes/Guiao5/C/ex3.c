#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>

#define BUFFER_SIZE 1024
#define READ_END    0 
#define WRITE_END   1

int main(){
	pid_t pid; 
	int fd[2];
	char buf[BUFFER_SIZE];
	int n;

	if( pipe(fd) == -1 ){
		perror("Pipe failed");
		return 1;
	}
	
	pid = fork();
	if( pid == -1 ){
		perror("Pipe failed");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		/* Closes de read end of the pipe */
		close(fd[READ_END]);
		/* Reads from the STDIN */
		n = read(0,buf,BUFFER_SIZE);
		/* Writes in the write end of the pipe */
		write(fd[WRITE_END],buf,n);
		/* Closes the write end of the pipe */
		close(fd[WRITE_END]);
	}
	/* Child process */
	else{
		/* Close the write end of the pipe */
		close(fd[WRITE_END]);
		/* Reads from the read end of the pipe */
		dup2(fd[READ_END],0);
		/* Execute wc */
		execlp("wc","wc",NULL);
	}
	return 0;
}
