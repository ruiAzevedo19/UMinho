#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

int main(){
    int fd_in    = open("/etc/passwd", O_RDONLY, 00400);
    int fd_out   = open("saida.txt", O_CREAT | O_WRONLY, 00200);
    int fd_error = open("erros.txt", O_CREAT | O_WRONLY, 00200);

    if( fd_in < 0 || fd_out < 0 || fd_error < 0 ){
        perror("Can´t open files!");
        return 1;
    }

    dup2(fd_in,0)    ; close(fd_in);
    dup2(fd_out,1)   ; close(fd_out);
    dup2(fd_error,2) ; close(fd_error);

    execlp("wc","wc", NULL);

    return 0;
}

