#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>

// Nota : se nao se colocar o wait no bloco do processo pai, ha possibilidade
// do processo pai acabar primeiro que o processo filho. Neste caso diz-se que
// o processo filho ficou orfao

int main(){
    int status;
    pid_t pid;

    pid = fork();

    switch(pid){
        case (-1) : perror("Fork failed");
                    return 1;
        case 0    : // processo filho
                    printf("--- Child Process ---\n");
                    printf("PID = %d\nPPID = %d\n",getpid(),getppid());
                    break;
        default   : // processo pai
                    wait(&status);
                    printf("--- Parent Process ---\n");
                    printf("PID = %d\nPPID = %d\nCID = %d\n",getpid(),getppid(),pid);
                    _exit(0);
    }
	return 0;
}

