#include "../headers/controller.h"
#include "../headers/parser.h"

#include <stdlib.h>
#include <string.h>
#include <sys/signal.h>
#include <unistd.h>
#include <stdio.h>
#include <signal.h>
#include <fcntl.h>

/* --- Controller structure ---------------------------- */
struct controller{    /*                                 */         
    int job;          /* job number                      */
    char* cmd;        /* command to be executed          */
    int ti;           /* inactivity time                 */
    int te;           /* execution time                  */ 
    int pipe;         /* pipe to output process          */
};                    /*                                 */
/* ----------------------------------------------------- */

static int SIZE = 128;
static pid_t procs[128];
static int p = 0;
static int j = 0;

/* --- Signal handlers --------------------------------- */

void killEmAll(int signal){
    char b[128];
    for(int i = 0; i < SIZE; i++)
        if( procs[i] > 0 )
            kill(procs[i],SIGKILL);

    sprintf(b,"%d",j);
    unlink(b);

    if( signal == SIGALRM )
        _exit(1);
    if( signal == SIGUSR2 )
        _exit(2);
    if( signal == SIGUSR1 )
        _exit(3);
}

void terminate_monitor(){
    kill(getppid(),SIGUSR2);
}

/* --- Construtor & Destructor ------------------------- */ 

Controller initController(int job, char* cmd, int ti, int te, int pipe){
    Controller c = malloc(sizeof(struct controller));

    c->job = job;
    j = job;
    c->cmd = strdup(cmd);
    c->ti = ti;
    c->te = te;
    c->pipe = pipe;

    memset(procs,-1,128);

    return c;
}

void destroyController(Controller c){
    free(c->cmd);
    free(c);
}

/* --- Funcionality ------------------------------------ */

static int insertProc(pid_t pid){
    int i, found = 0;
    for(i = 0; !found && i < SIZE; i++)
        if( procs[i] < 0 ){
            procs[i] = pid;
            found = 1;
        }
    return i;
}

static int countPipes(char* cmd){
    int i,n;
    for(i = 0, n = 0; cmd[i]; i++)
        n += cmd[i] == '|' ? 1 : 0;
    return n;
}

void execute(Controller c){
    signal(SIGALRM,killEmAll);
    signal(SIGUSR1,killEmAll);
    signal(SIGUSR2,killEmAll);

    if( c->te > 0 )
        alarm(c->te);

    pid_t pid;
    int i, n = countPipes(c->cmd); 
    int argc, nr_cmd;
    char **argv, **cmds = splitPipe(c->cmd, &nr_cmd);
    char buf[512], out[512];
    sprintf(out,"%d",c->job);
    int out_fd = open(out, O_CREAT | O_WRONLY, 0666);
    dup2(out_fd,1);
    close(out_fd);

    size_t rd;
    int size = (n > 0) ? n - 1 : 1; 
    int fds[size][2], mon[2][2];
    if( n > 0 ){
        pipe(mon[0]);
        pipe(mon[1]);
    }

    for(i = 0; i <= n; i++){
        if( i > 0 && i < n && n > 1 )
            pipe(fds[i-1]);
        if( !i && n > 0 ){
            pid = fork();
            if( !pid ){
                signal(SIGALRM,terminate_monitor);
                close(mon[0][1]);
                close(mon[1][0]);
                alarm(c->ti);
                while( (rd = read(mon[0][0],buf,512)) > 0 ){
                    alarm(c->ti);
                    write(mon[1][1],buf,rd);
                }
                close(mon[0][0]);
                close(mon[1][1]);
                _exit(0);
            }
            else
                insertProc(pid);
        }
        if( !(pid = fork()) ){
            if( !i && n > 0 ){
                close(mon[0][0]);
                close(mon[1][0]);
                close(mon[1][1]);
                dup2(mon[0][1],1);
                close(mon[0][1]);
            }
            if( i == 1 && n > 0 ){
                dup2(mon[1][0],0);
                close(mon[1][0]);
                if( n > 1 ){
                    close(fds[0][0]);
                    dup2(fds[0][1],1);
                    close(fds[0][1]);
                }
            }
            if( i > 1 && i < n && n > 1 ){
                close(fds[i-2][1]);
                dup2(fds[i-2][0],0);
                close(fds[i-2][0]);
                close(fds[i-1][0]);
                dup2(fds[i-1][1],1);
                close(fds[i-1][1]);
            }
            if( i == n && n > 1 ){
                close(fds[n-2][1]);
                dup2(fds[n-2][0],0);
                close(fds[n-2][0]);
            }
            argv = words(cmds[i],&argc);
            execvp(argv[0],argv);
            _exit(1);
        }
        if( !i && n > 0 ){
            close(mon[0][0]);
            close(mon[0][1]);
            close(mon[1][1]);
        }
        if( i == 1 ){
            close(mon[1][0]);
            if( n > 1 )
                close(fds[0][1]);
        }
        if( i > 1 && i < n && n > 1 )
            close(fds[i-1][1]);
        if( i == n && n > 1 ){
            close(fds[n-2][0]);
            close(fds[n-2][1]);
        }
        insertProc(pid); 
    }

    int found = 0, status;
    if( !n ){
        pid_t pid = wait(&status);
    }
    else{
        for(int i = 0; i < n + 2; i++){
            pid_t pid = wait(&status);
            for(int i = 0; !found && i < SIZE; i++)
                if( procs[i] == pid ){
                    procs[i] = -1;
                    found = 1;
                }
        }
    }
    write(c->pipe,&c->job,sizeof(int));
}


