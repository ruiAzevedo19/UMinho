#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

ssize_t readln(int fildes, char* line, size_t size){
	ssize_t r = 0, n;
	char c;

	while( r < size && (n = read(fildes,&c,1)) > 0 && c != '\n' )
	   	line[r++] = c;
	(r < size) ? (line[r] = 0) : (line[r - 1] = 0);

	return r;
}

void nl(int fildes){
	ssize_t n;
	int i = 1;
	char b[1024];
	char l[1024];

	while( (n = readln(fildes,b,1024)) > 0 ){
		sprintf(l,"\t%d   %s\n",i++,b);
		write(0,l,strlen(l));
	}
}

int main(int argc, char** argv){
	int f;

	switch ( argc ){
		case 1 : nl(0);
				 break;
		case 2 : f = open(argv[1], O_RDONLY);
				 nl(f);
				 break;
		default : perror("Invalid arguments!");
	}
}
