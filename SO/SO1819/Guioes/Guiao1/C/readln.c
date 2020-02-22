#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

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

int main(int argc, char **argv){
	int fd;
	char buf[1024];
	
	// Se nao for especificado o ficheiro lê do STDIN senao le do ficheiro
	if( argc < 2 )
		fd = 0;
	else
		fd = open(argv[1], O_RDONLY);
	
	// Le a linha do STDIN ou de um ficheiro
	ssize_t n = readln(fd,buf,1024);
	
	// Escreve a linha no STDOUT (n := nr de caracteres da primeira linha)
	write(0,buf,n);
}
