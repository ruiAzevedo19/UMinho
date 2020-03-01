#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char** argv){
	int f = open(argv[1], O_RDONLY);
	int k = open(argv[2], O_CREAT | O_WRONLY | O_TRUNC);
	int N = atoi(argv[3]);

	if(f < 0 || k < 0){
		perror("Error opening files!");
		return 1;
	}
	char b[N];
	int n;
	while( (n = read(f,b,N)) > 0 )
		write(k,b,n);
	close(f);
	close(k);

	return 0;

}


