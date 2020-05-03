#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

static int findFlag(char** args, int argc, char* flag){
    int i, found = 0;

    for(i = 0; !found && i < argc; i++)
        if( !strcmp(args[i], flag) )
            found = 1;
    return found ? --i : -1;
}

int main(int argc, char** argv){
    int i, fd_in, fd_out, flags = 0;
    pid_t pid;

    if( (i = findFlag(argv,argc,"-i")) > 0 ){
        fd_in = open(argv[i + 1], O_RDONLY, 00400);
        if( !fd_in ){
            perror("Error opening input file!");
            return 1;
        }
        flags++;
        dup2(fd_in, 0);
        close(fd_in);
    }
    if( (i = findFlag(argv,argc,"-o")) > 0 ){
        fd_out = open(argv[i + 1], O_CREAT | O_APPEND | O_WRONLY);
        flags++;
        dup2(fd_out, 1);
        close(fd_out);
    }

    i = 2 * flags + 1;

    pid = fork();
    if( !pid ){
        execvp(argv[i],argv + i);
        _exit(1);
    }
    else
        wait(NULL);

    return 0;
}
