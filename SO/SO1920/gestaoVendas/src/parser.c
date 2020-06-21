#include "../headers/parser.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

char** words(char* in, int* nr_words){
    int i = 0, size = 10;
    char** ws = malloc(sizeof(char*) * size + 1);
    char* w;
    char* delim;
    w = strtok(in," ");
    delim = !strcmp(w,"executar") ? "'" : " ";
    while( w ){
        if( i == size ){
            size *= 2;
            ws = realloc(ws, sizeof(char*) * size);
        }
        ws[i++] = strdup(w);
        w = strtok(NULL,delim);
    }
    ws[i] = 0;
    *nr_words = i;

    return ws;
}

char** splitPipe(char* in, int* nr_words){
    int i = 0, size = 10;
    char** ws = malloc(sizeof(char*) * size + 1);
    char* w;
    char* delim = "|";
    w = strtok(in,"|");
    while( w ){
        if( i == size ){
            size *= 2;
            ws = realloc(ws, sizeof(char*) * size);
        }
        ws[i++] = strdup(w);
        w = strtok(NULL,delim);
    }
    ws[i] = 0;
    *nr_words = i;

    return ws;
}

void freeWords(char** words, int nr_words){
    for(int i = 0; i < nr_words; i++)
        free(words[i]);
    free(words);
}

ssize_t readln(int fildes, char* line, size_t size){
	ssize_t r = 0, n;
    char buf[size];

    n = read(fildes,buf,size);
    for(r = 0; (int)r < (int)size && buf[r] != '\n'; r++)
	   	line[r] = buf[r];
    line[r++] = '\n';

	return r;
}

ssize_t readlnFile(int fildes, char* line, size_t size){
    ssize_t r = 0, n;
    char buf[size];

    n = lseek(fildes,0,SEEK_CUR);
    read(fildes,buf,size);
    for(r = 0; (int)r < (int)size && buf[r] != '\n'; r++)
	   	line[r] = buf[r];
    line[r++] = '\n';
    lseek(fildes,n + r,SEEK_SET);

	return r;
}
