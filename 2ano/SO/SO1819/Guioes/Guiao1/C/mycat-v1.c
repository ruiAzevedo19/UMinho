#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv){

	// buffer que vai ser usado para leitura e escrita
	char c;
	int n;

	// (1) lê um caracter de cada vez do STDIN e coloca-o em c
	// (2) escreve o que foi guardado em c no STDOUT
	while( (n = read(0,&c,1)) > 0 )
		write(1,&c,1);
	
	return 0;
}
