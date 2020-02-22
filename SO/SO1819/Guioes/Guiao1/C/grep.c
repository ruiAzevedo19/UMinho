#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

ssize_t readln(int fildes, void *buf, size_t nbyte){
	ssize_t nbytes = 0;
	int n;
	char c;
	char *buffer = (char *)buf;

	// So le o numero de caracteres corresponde ao tamanho do buffer
	while( nbytes < nbyte && (n = read(fildes,&c,1)) > 0 ){
		buffer[nbytes++] = c;
		if( c == '\n')
			break;
	}
	(nbytes < nbyte) ? (buffer[nbytes] = '\0') : (buffer[nbytes - 1] = '\0');	

	return nbytes;
}

char *idx(char *buf, int n, char *file){
	char *id = malloc(sizeof(char) * (n + 1));
	sprintf(id,"%s:%s",file,buf);
	return id;
}

int main(int argc, char **argv){
	int fdi, n, line, fd, i = 0; 
	char buf[1024];

	switch( argc ){
		case 1  : perror("Tem que especificar o número da linha e ficheiro");
			     break;

		case 2  : perror("Tem que especificar um ficheiro para leitura");
			     break;

		default : line = atoi(argv[1]);
				for(fdi = 2; fdi < argc; fdi++){
					n = 1;
					fd = open(argv[fdi], O_RDONLY);
					while( i < line && n > 0 )
						if( (n = readln(fd,buf,1024)) )
							i++;
					if( i == line )
						break;
				}
				if( i == line ){
					char *id = buf;
					int tam  = n; 
					if( argc > 3 ){
						id = idx(buf,n,argv[fdi]);
						tam = n + strlen(argv[fdi]) + 1;
					}
					write(1,id,tam);
				}
				break;
		}
	return 0;
}
