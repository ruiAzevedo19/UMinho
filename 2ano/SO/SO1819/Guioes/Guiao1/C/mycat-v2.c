#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv){

	// buffer que vai ser usado para leitura e escrita
	int size = atoi( argv[1] );
	char buf[size];
	int n;

	// ler do STDIN para o buffer, e escrever do buffer para STDOUT
	while( (n = read(0,buf,size)) > 0 ){
		if( n >= size )
			buf[size - 1] = 0;
		write(1,buf,n);
	}
	return 0;
}
