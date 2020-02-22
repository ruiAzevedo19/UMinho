#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

#define N 5

ssize_t readln(int fildes, char* line, size_t size){
	ssize_t r = 0, n;
	char c;

	while( r < size && (n = read(fildes,&c,1)) > 0 && c != '\n' )
	   	line[r++] = c;
	(r < size) ? (line[r] = 0) : (line[r - 1] = 0);

	return r;
}

int main(int argc, char** argv){
	char b[N];
	int fd = (argc == 1) ? 0 : open(argv[1],O_RDONLY);
	int r = readln(fd,b,N);
	write(1,b,strlen(b));
	write(1,"\n",sizeof(char));
	return 0;
}
