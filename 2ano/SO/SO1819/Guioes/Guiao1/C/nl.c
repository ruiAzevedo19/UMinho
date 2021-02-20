#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PAD 

ssize_t readln(int fildes, void *buf, size_t nbyte){
	ssize_t nbytes = 0;
	int n;
	char c;
	char *buffer = (char *)buf;

	// So le o numero de caracteres corresponde ao tamanho do buffer
	while( nbytes < nbyte && (n = read(fildes,&c,1)) > 0 && c != '\n' )
		buffer[nbytes++] = c;

	(nbytes < nbyte) ? (buffer[nbytes] = '\0') : (buffer[nbytes - 1] = '\0');	

	return nbytes;
}

char *padding(char *buf, int n, int i){
	char *pad = malloc(sizeof(char) * (n + 10));
	sprintf(pad,"\t%d  %s\n",i,buf);
	return pad;
}

int main(int argc, char **argv){
	int fd, i = 1;
	ssize_t n;
	char buf[1024];
	char *pad;

	if( argc < 2 )
		fd = 0;
	else
		fd = open(argv[1], O_RDONLY);
	
	while( (n = readln(fd,buf,1024)) > 0  ){
		pad = padding(buf,n,i++);
		write(0,pad,strlen(pad));
	}
	return 0;
}
