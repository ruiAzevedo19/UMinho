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

char *idx(char *arg){
	int n = strlen(arg);
	char *buf = malloc(sizeof(char) * (n + 11));
	sprintf(buf,"==>  %s  <==\n",arg);
	return buf;
}

int main(int argc, char **argv){
	int flag, n, fdi, fd = 0, i = 0;
	char buf[1024];
	char *id;
	
	if(argc > 1) 
		flag = atoi(argv[1] + 1);

	switch( argc ){
		case 1  : perror("Tem que especificar o número de linhas");
			     break;

		case 2  : while( i++ < flag && (n = readln(fd,buf,1024)) > 0 )
			    	 	write(0,buf,n);
				break;
		
		default : for(fdi = 2; fdi < argc; fdi++){
					fd = open(argv[fdi], O_RDONLY);
					if( argc > 3 ){
						id = idx(argv[fdi]);
						write(0, id, strlen(id));
					}
					while( i++ < flag && (n = readln(fd,buf,1024)) > 0 )
						write(0,buf,n);
					i = 0;
				}
				break;
	}
	return 0;
}
