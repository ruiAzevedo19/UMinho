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

char** getPipeCommand(char** argv, int* argc, int* isOver){
    int i, j;
    char** command;

    for(i = *argc - 1; i >= 0 && strcmp(argv[i],"|"); i--);

    if( i == -1 )
        i = 0;
    *isOver = ( i == 0 ) ? 1 : 0 ;

    command = malloc(sizeof(char**) * (*argc - i));
    
    if( i > 0 )
        free(argv[i++]);
    for(j = 0; i < *argc; j++, i++){
        command[j] = strdup(argv[i]);
        free(argv[i]);
    }
    command[j] = 0;
    *argc = *argc - j - 1;

    return command;
}

void exec(char** argv, int argc){
    pid_t pid;
    int fd[2];
    char** command;
    int isOver = 0;
    pid = fork();
    if( !pid ){
        while( !isOver ){
            
        }
    }
    else{
        wait(NULL);
    }
}


void print(char** ws, int nr_words){
    for(int i = 0; ws[i]; i++)
        printf("%s\n",ws[i]);
}

int main(){
  char** ws;
  int isOver = 0;
  int nr_words, background, n = 1;
  char b[1024];
  char prompt[9] = "bash >> ";
  char **command;

  while( 1 ){
      write(1,prompt,9);
      n = readln(0,b,1024);
      if( !strcmp("quit",b) )
          break;
      ws = words(b,&nr_words);
      while( !isOver ){
          command = getPipeCommand(ws,&nr_words,&isOver);
          print(command,nr_words);
      }
  }
  return 0;
}
