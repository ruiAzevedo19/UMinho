#include <unistd.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(){
	/* open /etc/passwd */
	int fd = open("/etc/passwd",O_RDONLY);
	/*  redirect fd to STDIN */
	dup2(fd,0);
	close(fd);
	/* open saida.txt */
	int fdS = open("saida.txt", O_RDONLY | O_WRONLY | O_CREAT);
	/* redirect fdS to STDOUT */
	dup2(fdS,1);
	close(fdS);
	/* open error.txt */
	int fdE = open("erros.txt", O_RDONLY | O_WRONLY | O_CREAT);
	/* redirect fdE to STDERR */
	dup2(fdE,2);
	close(fdE);

	char buf[1024];
	/* must read from /etc/password and write in saida.txt and erros.txt */
	int n = read(0,buf,1024);
	write(1,buf,n);
	write(2,buf,n);
	return 0;
}
