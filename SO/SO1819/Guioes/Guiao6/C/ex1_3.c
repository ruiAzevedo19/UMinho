#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main(){
	char buf[1024];
	int n, fd = open("fifo",O_RDONLY);
	
	while( (n = read(fd,buf,1024)) )
		write(1,buf,n);
	close(fd);

	return 0;
}
