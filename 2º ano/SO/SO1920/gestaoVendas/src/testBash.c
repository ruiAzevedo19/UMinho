#include "../headers/parser.h"

#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
    int i = 0;
    int p[2];
    char buf[128];
    ssize_t n;
    pid_t pid;
    int fd = open("bashScript", O_RDONLY, 0666);

    pipe(p);
    pid = fork();

    switch( pid ){
        case 0 : close(p[1]);
                 dup2(p[0],0);
                 close(p[0]);
                 execlp("./argus","./argus",NULL);
                 _exit(1);
                 break;
        default : close(p[0]);
                  while( i < 24 ){
                      n = readlnFile(fd,buf,128);
                      if( n == 0 || n == 1 || !strcmp(buf,"sair\n") )
                          break;
                      write(p[1],buf,n);
                      memset(buf,0,128);
                      i++;
                      sleep(1);
                  }
                  close(p[1]);
    }
    
    return 0;
}
