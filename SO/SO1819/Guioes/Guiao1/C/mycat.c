#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>


int main(int argc, char **argv){
	int fd = 0, n;
	char buf[1024];
	
	if( argc > 1 )
		for(int i = 1; i < argc; i++){
			fd = open(argv[i], O_RDONLY);
			while( (n = read(fd,buf,1024)) > 0 )
				write(0,buf,n);
		}
	else{
		while( (n = read(fd,buf,1024)) > 0 )
			write(0,buf,n);
	}

	return 0;
}
