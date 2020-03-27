#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char** argv){
    pid_t pid[argc - 1];
    int i, status;

    for(i = 0; i < argc - 1; i++)
        if( !(pid[i] = fork()) ){
            execlp(argv[i + 1], argv[i + 1], NULL);
            _exit(0);
        }
    for(i = 0; i < argc - 1; i++)
        wait(NULL);
        if( !WIFEXITED(status) )
            perror("Child process error!");

	return 0;
}
