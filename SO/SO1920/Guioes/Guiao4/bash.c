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

void freeWordsBetween(char** words, int li, int ls){
    words[li] = 0;
    for(int i = li + 1; i <= ls; i++)
        free(words[i]);
}

int findSpecialCase(char** words, int nr_words, char* special){
    int i, found = 0;
    for(i = 0; !found && i < nr_words; i++) 
        if( !strcmp(words[i],special) )
            found = 1;
    return (i == nr_words) ? -1 : --i;
}

int redir(char** argv, int *argc){
    int rdir, fd, r;

    rdir = findSpecialCase(argv, *argc, ">");
    if( rdir > 0 ){
        fd = open(argv[rdir + 1], O_CREAT | O_WRONLY);
        if( fd < 0 )
            return -1;
        dup2(fd,1);
        close(fd);
        freeWordsBetween(argv,rdir,*argc - 1);
        *argc = rdir;
    }
    rdir = findSpecialCase(argv, *argc,"<");
    if( rdir > 0 ){
        fd = open(argv[rdir + 1], O_RDONLY);
        if( fd < 0 )
            return 1;
        dup2(fd,0);
        close(fd);
        freeWordsBetween(argv,rdir,*argc - 1);
        *argc = rdir;
    }
    rdir = findSpecialCase(argv, *argc, ">>");
    if( rdir > 0 ){
        fd = open(argv[rdir + 1], O_CREAT | O_APPEND | O_WRONLY);
        if( fd < 0 )
            return -1;
        dup2(fd,1);
        close(fd);
        freeWordsBetween(argv,rdir,*argc - 1);
        *argc = rdir;
    }
    rdir = findSpecialCase(argv, *argc, "2>");
    if( rdir > 0 ){
        fd = open(argv[rdir + 1], O_CREAT | O_WRONLY);
        if( fd < 0 )
            return -1;
        dup2(fd,2);
        close(fd);
        freeWordsBetween(argv,rdir,*argc - 1);
        *argc = rdir;
    }
    rdir = findSpecialCase(argv, *argc, "2>>");
    if( rdir > 0 ){
        fd = open(argv[rdir + 1], O_CREAT | O_APPEND | O_WRONLY);
        if( fd < 0 )
            return -1;
        dup2(fd,2);
        close(fd);
        freeWordsBetween(argv,rdir,*argc - 1);
        *argc = rdir;
    }
    return 0;
}

void execute(char** argv, int *argc, int background){
    pid_t pid;
    int s = redir(argv,argc);
    if( s < 0 ){
        perror("Fail opening redirection file!");
       return;
    }
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

void restoreFileDesc(int stin, int stout, int sterror){
    dup2(stin,0);
    dup2(stout,1);
    dup2(sterror,2);
}

int main(int argc, char** argv){
    char** ws;
    int nr_words, background, n = 1;
    char b[1024];
    char prompt[9] = "bash >> ";
    int stin = dup(0), stout = dup(1), sterror = dup(2);

    while( 1 ){
        write(1,prompt,9);
        n = readln(0,b,1024);
        if( !strcmp("quit",b) )
            break;
        ws = words(b,&nr_words);
        background = runBackground(ws[nr_words - 1]);
        execute(ws,&nr_words,background);
        restoreFileDesc(stin,stout,sterror);
        freeWords(ws,nr_words);
    }
    return 0;
}
