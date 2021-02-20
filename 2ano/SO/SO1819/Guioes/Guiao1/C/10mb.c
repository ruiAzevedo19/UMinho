#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#define MB 1048576 // 1024 * 1024 -> MB

int main(int argc, char **argv){
	
	// Abre um ficheiro com premissões de leitura, escrita e criação caso não exista
	if( argv[1] ){
		int fd = open(argv[1], O_CREAT | O_RDWR);
		char bytes[10] = "aaaaaaaaa";
		int i = 0;
	
		while( i++ < MB )
			write(fd,bytes,10);
	}
	else
		perror("Tem que especificar o nome do ficheiro\n");
	
	return 0;
}
