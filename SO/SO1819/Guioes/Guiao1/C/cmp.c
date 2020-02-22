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

char *padding(char *file1, char *file2, int chr, int line){
	int n1 = strlen(file1);
	int n2 = strlen(file2);
	char *pad = malloc(sizeof(char) * (n1 + n2 + 30));
	sprintf(pad,"%s %s differ: char %d, line %d\n",file1,file2,chr,line);
	return pad;
}

int compare(char *f1, char *f2){
	int i;
	for(i = 0; f1[i] && f2[i] && f1[i] == f2[i]; i++);
	return i;
}
int main(int argc, char **argv){
	int fd1, fd2;	// descritores para cada um dos ficheiros
	int cmp = 1, n1 = 1, n2 = 1, t_bytes = 0, bytes = 0, lines = 0;
	char buf1[1024], buf2[1024];
	char *pad;

	switch( argc ){
		case 1  :
		case 2  : perror("Tem que especificar dois ficheiros para leitura");
			     break;

		case 3  : fd1 = open(argv[1], O_RDONLY);
				fd2 = open(argv[2], O_RDONLY);
				while( (n1 = readln(fd1,buf1,1024)) > 0 && (n2 = readln(fd2,buf2,1024)) > 0 ){
					cmp = compare(buf1,buf2);
					lines++ ; bytes += cmp; t_bytes += n1;
					if( cmp < n1 && cmp < n2 )
						break;
				}
				if( bytes < t_bytes ){
					pad = padding(argv[1],argv[2],bytes,lines);
					write(1,pad, strlen(pad) * sizeof(char));
				}
				break;
		
		default : perror("Só pode especificar dois ficheiros para escrita");
	}
	return 0;
}
