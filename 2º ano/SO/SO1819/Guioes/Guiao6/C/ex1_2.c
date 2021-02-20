#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main(){
	int n;
	char buf[1024];
	/* opens pipe with write permissions */
	int fd = open("fifo",O_WRONLY);
	/* reads from STDIN and writes to the pipe */
	while( (n = read(0,buf,1024)) > 0 )
		write(fd,buf,n);
	close(fd);

	return 0;
}
