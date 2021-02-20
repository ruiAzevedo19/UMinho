#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include "readln.h"

int main(){
    int fd_in    = open("/etc/passwd", O_RDONLY, 00400);
    int fd_out   = open("saida.txt", O_CREAT | O_WRONLY, 00200);
    int fd_error = open("erros.txt", O_CREAT | O_WRONLY, 00200);

    if( fd_in < 0 || fd_out < 0 || fd_error < 0 ){
        perror("Can´t open files!");
        return 1;
    }
    dup2(fd_in,0);
    dup2(fd_out,1);
    dup2(fd_error,2);

    close(fd_in);
    close(fd_out);
    close(fd_error);

    char b[1024];
    readln(0,b,1024);

    write(1,b,strlen(b));
    write(2,b,strlen(b));

    printf("Hello, World!\n");

    return 0;
}
