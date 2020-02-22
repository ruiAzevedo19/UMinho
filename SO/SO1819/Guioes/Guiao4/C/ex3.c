#include <unistd.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

int main(){
	/* open /etc/passwd */
	int fd = open("/etc/passwd",O_RDONLY);
	/*  redirect fd to STDIN */
	dup2(fd,0);
	close(fd);
	/* open saida.txt */
	int fdS = open("saida.txt", O_RDONLY | O_WRONLY | O_CREAT, 777);
	/* redirect fdS to STDOUT */
	dup2(fdS,1);
	close(fdS);
	/* open error.txt */
	int fdE = open("erros.txt", O_RDONLY | O_WRONLY | O_CREAT, 777);
	/* redirect fdE to STDERR */
	dup2(fdE,2);
	close(fdE);

	char buf[1024];
	pid_t pid;
	int status;

	pid = fork();
	if( pid == -1 ){
		perror("Fork failed!");
		return 1;
	}
	/* Parent process */
	if( pid > 0 ){
		pid = wait(&status);
		return status;
	}
	/* Child process */
	else{
		execlp("wc","wc",NULL);
		_exit(0);
	}
	return 0;
}
