#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include "aux.h"

int main(){
	int n;
	char buf[1024];
	int fd_log = open("log", O_CREAT | O_APPEND | O_RDWR | O_APPEND );
	int fd_pip = open("fifo", O_RDONLY);

	while( (n = read(fd_pip,buf,1024)) ){
		write(1,buf,n);
		write(fd_log,buf,n);
	}
	close(fd_log);
	close(fd_pip);

	return 0;	
}
