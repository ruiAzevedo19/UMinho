#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include "readln.h"

/* solução muito ineficiente */
ssize_t readln(int fildes, char* line, size_t size){
	ssize_t r = 0, n;
	char c;

	while( r < size && (n = read(fildes,&c,1)) > 0 && c != '\n' )
	   	line[r++] = c;
    line[r] = 0;

	return r;
}

/* melhor do que a solucao anteror mas ainda podemos diminui uma chamada ao sistema */
ssize_t readln_v2(int fildes, char* line, size_t size){
	ssize_t n = read(fildes,line,size);
	int i = 0;
	if( n > 0 ){
		for( ; i < n && line[i] != '\n'; i++);
		lseek(fildes,-(n - i - 1), SEEK_CUR);
        i++;
	}	
	return i;
}

/* */
ssize_t readln_v3(int fildes, char* line, size_t size){
    return 0;
}
