#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define READ 0 
#define WRITE 1
#define MAX 1024

int main(){
    pid_t pid;
    int fd[2];

    if( pipe(fd) < 0 ){
        perror("Error creating pipe!");
        return 1;
    }
    pid = fork();
    if( pid < 0 ){
        perror("Error creating child process");
        return 1;
    }
    if( !pid ){
        dup2(fd[READ],0);
        close(fd[READ]);
        close(fd[WRITE]);
        execlp("wc","wc","-l",NULL);
        _exit(1);
    }
    else{
        dup2(fd[WRITE],1);
        close(fd[WRITE]);
        close(fd[READ]);
        execlp("ls","ls","/etc",NULL);
    }
    return 0;
}
