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

char *padding(int lines, int words, int bytes, char *file){
	char *pad = NULL;
	
	if( file != NULL ){
		int n = strlen(file);
		pad = malloc(sizeof(char) * (n + 20));
		sprintf(pad,"\t%d\t%d\t%d %s\n",lines,words,bytes,file);
	}
	else{
		pad = malloc(sizeof(char) * 20);
		sprintf(pad,"\t%d\t%d\t%d\n",lines,words,bytes);
	}
	return pad;
}

int word(char *buf){
	int i, count = 0;

	for(i = 0; buf[i] && buf[i] == ' '; i++);
	
	for(; buf[i]; i++)
		if( buf[i] != ' ' && (buf[i + 1] == ' ' || buf[i + 1] == '\0') )
			count++;
	return count;
}

int main(int argc, char **argv){
	int t_lines = 0, t_words = 0, t_bytes = 0;
	int lines = 0, words = 0, bytes = 0;
	int n, fdi, fd, w;
	char buf[1024];
	char *pad;

	switch( argc ){
		case 1  : while( (n = readln(0,buf,1024)) > 0 ){
					lines++; words += word(buf); bytes += n;
				}
				char *tmp = NULL;
				pad = padding(lines,words,bytes,tmp);
				write(1, pad, strlen(pad) * sizeof(char));
				break;

		default : for(fdi = 1; fdi < argc; fdi++){
					lines = 0; words = 0; bytes = 0;
					fd = open(argv[fdi], O_RDONLY);
					while( (n = readln(fd,buf,1024)) > 0 ){
						t_lines++; lines++;
						t_bytes += n; bytes += n;
						w = word(buf); words += w; t_words += w;
					}
					pad = padding(lines,words,bytes,argv[fdi]);
					write(1,pad,strlen(pad) * sizeof(char));
				}
				if(argc > 2){
					char *total = strdup("Total");
					pad = padding(t_lines,t_words, t_bytes,total);
					write(1,pad, strlen(pad) * sizeof(char));
				}
	}
	return 0;
}









