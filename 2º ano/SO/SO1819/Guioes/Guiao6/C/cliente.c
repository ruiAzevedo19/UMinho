#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#define PROMPT "Cliente > "
#define PSIZE  11

int main(){
	int n = 1;
	char buf[1024];
	int fd = open("fifo",O_WRONLY);

	while( n > 0 ){
		write(1,PROMPT,PSIZE);
		n = read(0,buf,1024);
		if( buf[0] != '\n' )
			write(fd,buf,n);
	}
	close(fd);

	return 0;
}
