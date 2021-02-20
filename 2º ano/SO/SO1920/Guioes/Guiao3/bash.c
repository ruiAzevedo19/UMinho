#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <stdlib.h>
#include "readln.h"

char** words(char* in, int* nr_words){
    int i = 0, size = 10;
    char** ws = malloc(sizeof(char*) * size + 1);
    char* w;

    w = strtok(in," ");
    while( w ){
        if( i == size ){
            size *= 2;
            ws = realloc(ws, sizeof(char*) * size);
        }
        ws[i++] = strdup(w);
        w = strtok(NULL," ");
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

void printWords(char** words, int nr_words){
    for(int i = 0; i < nr_words; i++)
        printf("%s\n",words[i]);
}

void execute(char** argv, int argc, int background){
    pid_t pid;

    switch( background ){
        case 0 : pid = fork();
                 if( !pid ){
                     execvp(argv[0],argv);
                     printf("Comando Inválido!\n");
                     _exit(1);
                 }
                 else
                     wait(NULL);
                 break; 
        case 1 : pid = fork();
                 if( !pid ){
                     execvp(argv[0],argv);
                     printf("Comando Inválido!\n");
                     _exit(1);
                 }
                 break;
    }
}

int runBackground(char* word){
    int b = 0;
    if(word[strlen(word) - 1] == '&'){
        word[strlen(word) - 1] = 0;
        b = 1;
    }
    return b;
}

int main(int argc, char** argv){
    char** ws;
    int nr_words, background, n = 1;
    char b[1024];
    char prompt[9] = "bash >> ";

    while( 1 ){
        write(1,prompt,9);
        n = readln(0,b,1024);
        if( !strcmp("quit",b) )
            break;
        ws = words(b,&nr_words);
        background = runBackground(ws[nr_words - 1]);
        execute(ws,nr_words,background);
        freeWords(ws,nr_words);
    }
    return 0;
}
