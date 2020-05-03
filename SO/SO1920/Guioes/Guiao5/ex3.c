#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

#define READ 0
#define WRITE 1

int main(){
    pid_t pid;
    int fd[2], status, n;
    char buffer[1024];

    if( pipe(fd) < 0 ){
        perror("Error creating pipe!");
        return 1;
    }
    pid = fork();
    if( pid < 0 ){
        perror("Error creating child process!");
        return 1;
    }
    if( !pid ){
        dup2(fd[READ],0);
        close(fd[READ]);
        close(fd[WRITE]);
        execlp("wc","wc",NULL);
        _exit(0);
    }
    else{
        close(fd[READ]);
        while( (n = read(0,buffer,1024)) > 0 )
                write(fd[WRITE],buffer,n);
        close(fd[WRITE]);
        wait(&status);
        if( WIFEXITED(status) )
            printf("Child process (%d) ended with sucess!\n",pid);
        else 
            printf("Error in child process (%d)!\n",pid);
    }
    return 0;
}
