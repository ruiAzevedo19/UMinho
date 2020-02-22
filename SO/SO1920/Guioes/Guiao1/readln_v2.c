#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define N 4096

ssize_t readln(int fildes, char* line, size_t size){
	ssize_t n = read(fildes,line,size);
	int i;
	if( n > 0 ){
		for(i = 0; i < size && line[i] != '\n'; i++);
		(i == size) ? (line[--i] = 0) : (line[i] = 0);
		lseek(fildes,-(n - i + 1), SEEK_CUR);
	}	
	return n;
}

int main(int argc, char** argv){
	if( argc > 2 ){
		perror("Too much arguments!");
		return 1;
	}

	int fd = (argc == 1) ? 0 : open(argv[1], O_RDONLY);
	char buffer[N];
	ssize_t n = readln(fd,buffer,N);
	
	printf("Buffer := %s\nRead   := %zd\n", buffer,n);

	return 0;
}
