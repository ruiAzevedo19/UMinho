#include <unistd.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char **argv){
	if( argc < 2 )
		return 1;
	
	int fd_in = 0, fd_out = 1;
	int i;

	for(i = 1; i < argc; i++){
		if( !strcmp(argv[i], "-i") ){
			fd_in = open(argv[i + 1], O_CREAT | O_RDONLY | O_WRONLY, 777);
			dup2(fd_in,0);
		}
		else{
			if( !strcmp(argv[i], "-o") ){
				fd_out = open(argv[i + 1], O_CREAT | O_RDONLY | O_WRONLY, 777);
				dup2(fd_out,1);
			} 
		}
	}

	return 0;
}
