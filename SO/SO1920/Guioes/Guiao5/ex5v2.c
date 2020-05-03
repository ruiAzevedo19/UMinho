/**
 * Neste caso, a informação vai dos filhos para o pai
 * Os processos de leitura vão bloquear à espera que o processo filho 
 * escreva no pipe 
 *
 *          wc -l                               Processo principal
 *           /|\                                    read 
 *            |
 *          | 0 | 1 |
 *               /|\                                write
 *                |
 *               uniq                           Filho 1
 *                /|\                               read 
 *                 |
 *               | 0 | 1 |
 *                    /|\                           write
 *                     |
 *                 cut -f7 -d:                  Filho 2
 *                    /|\                           read
 *                     | 
 *                   | 0 | 1 |
 *                        /|\                       write
 *                         |
 *                  grep -v ^# /etc/passwd      Filho 3 
 *                                                  read 
 */
#include <unistd.h>
#include <stdio.h>

#define READ 0
#define WRITE 1

int main(){
    pid_t pid;
    int fd[2];

    pipe(fd);
    pid = fork();
    if( !pid ){
        dup2(fd[WRITE],1);
        close(fd[WRITE]);
        close(fd[READ]);
        pipe(fd);
        pid = fork();
        if( !pid ){
            dup2(fd[WRITE],1);
            close(fd[WRITE]);
            close(fd[READ]);
            pipe(fd);
            pid = fork();
            if( !pid ){
                dup2(fd[WRITE],1);
                close(fd[WRITE]);
                close(fd[READ]);
                execlp("grep","grep","-v","^#","/etc/passwd",NULL);
                _exit(1);
            }
            else{
                dup2(fd[READ],0);
                close(fd[WRITE]);
                close(fd[READ]);
                execlp("cut","cut","-f7","-d:",NULL);
                _exit(1);
            }
        }
        else{
            dup2(fd[READ],0);
            close(fd[WRITE]);
            close(fd[READ]);
            execlp("uniq","uniq",NULL);
            _exit(1);
        }
    }
    else{
        dup2(fd[READ],0);
        close(fd[WRITE]);
        close(fd[READ]);
        execlp("wc","wc","-l",NULL);
        _exit(1);
    }
    return 0;
}
