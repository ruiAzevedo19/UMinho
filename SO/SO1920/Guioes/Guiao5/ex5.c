/**
 * Este exercício funciona mas como o processo pai acaba primeiro que o processo filho 
 * não é lançada a prompt da bash
 */
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define READ 0 
#define WRITE 1
#define MAX 1024

int main(){
    pid_t pid;
    int i = 0, fd[2];

    pipe(fd);
    pid = fork();
    if( !pid ){
        dup2(fd[READ],0);
        close(fd[WRITE]);
        close(fd[READ]);
        pipe(fd);
        pid = fork();
        if( !pid ){
            dup2(fd[READ],0);
            close(fd[WRITE]);
            close(fd[READ]);
            pipe(fd);
            pid = fork();
            if( !pid ){
                dup2(fd[READ],0);
                close(fd[WRITE]);
                close(fd[READ]);
                execlp("wc","wc","-l",NULL);
            }
            else{
                dup2(fd[WRITE],1);
                close(fd[WRITE]);
                close(fd[READ]);
                execlp("uniq","uniq",NULL);
            }
        }
        else{
            dup2(fd[WRITE],1);
            close(fd[WRITE]);
            close(fd[READ]);
            execlp("cut","cut","-f7","-d:",NULL);
        }
    }
    else{
        dup2(fd[WRITE],1);
        close(fd[WRITE]);
        close(fd[READ]);
        execlp("grep","grep","-v","^#","/etc/passwd",NULL);
    }
    return 0;
}
