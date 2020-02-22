#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char** argv){
	if( argc != 3 ){
		printf("Error: number of arguments is 3\n");
		return 1;
	}

	int f = open(argv[1], O_RDONLY);
	int k = open(argv[2], O_CREAT | O_WRONLY | O_TRUNC);
	
	char c;
	int n;
	while( (n = read(f,&c,1)) > 0 )
		write(k,&c,1);

	close(f);
	close(k);

	return 0;

}


