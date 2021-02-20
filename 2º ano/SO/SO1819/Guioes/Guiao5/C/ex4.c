#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>

#define BUFFER_SIZE 1024
#define READ_END    0 
#define WRITE_END   1

int main(){
	pid_t pid;
	int fd[2];

	if( pipe(fd) == -1 ){
		perror("Pipe failed!");
		return 1;
	}

	pid = fork();
	if( pid == -1 ){
		perror("Fork failed!");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		/* Close the read end of the pipe */
		close(fd[READ_END]);
		/* Redirect write end of the pipe to STDOUT, i.e, STDOUT will write in pipe's write end */
		dup2(fd[WRITE_END],1);
		/* Closes write end of the pipe */
		close(fd[WRITE_END]);
		/* Execute ls /etc */
		execlp("ls","ls","/etc",NULL);
		/* exit */
		_exit(0);
	}
	/* Child process */
	else{
		/* Closes the write end of the pipe */
		close(fd[WRITE_END]);
		/* Redirect read end to STDOUT, i.e, STDIN will read in pipe's read end */
		dup2(fd[READ_END],0);
		/* Closes read end of the pipe */
		close(fd[READ_END]);
		/* Execute wc -l */
		execlp("wc","wc","-l",NULL);
	}
	return 0;
}
