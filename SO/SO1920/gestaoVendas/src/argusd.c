#include <sys/types.h>
#include <sys/_types/_errno_t.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>  
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <errno.h>

#include "../headers/request.h"
#include "../headers/controller.h"
#include "../headers/answer.h"
#include "../headers/daemonRelay.h"
#include "../headers/agr.h"


/* --- Global variables --------------------------------------- */
static const char* RD_FIFO  = "request_fifo";
static const char* WR_FIFO  = "answer_fifo";
static const char* LOG_FILE = "log";
static const char* HISTORY_FILE = "history";
static const char* OUT_FILE = "output";
static const char* IDX_FILE = "idx";
/* --- Level of multi process --------------------------------- */
static const int SIZE = 512;
/* --- Manage running processes ------------------------------- */
static pid_t procs[SIZE];
static int nrProcs[SIZE];
static char* cmds[SIZE];
static int runningProcs = 0;
static int currentProc = 1;
static int hist = 0;
/* --- Aggregation process ------------------------------------- */
static pid_t outPid;
/* --- Times --------------------------------------------------- */
static int te = 0;
static int ti = 0;
/* ------------------------------------------------------------- */

/* --- signals ------------------------------------------------- */
void kill_handler(){
    kill(outPid,SIGKILL);
    unlink(RD_FIFO);
    unlink(WR_FIFO);
    _exit(-1);
}

void sigchld_handler(){
    int fd, status, rd, nr = 1,found = 0;
    char* hsty;

    pid_t pid = wait(&status);

    fd = open(HISTORY_FILE, O_CREAT | O_RDWR, 0666);
    hist++;
    write(fd,&hist,sizeof(int));
    lseek(fd,0,SEEK_END);
    for(int i = 0; !found && i < SIZE; i++)
        if( procs[i] == pid ){
            hsty = createHistoryMessage(WEXITSTATUS(status),nrProcs[i],cmds[i]);
            write(fd,hsty,strlen(hsty));
            free(hsty);
            procs[i] = -1;
            nrProcs[i] = -1;
            free(cmds[i]);
            cmds[i] = 0;
            runningProcs--;
            found = 1;
        }
    close(fd);
}

/* --- Aux ----------------------------------------------------- */
static int insertProc(char* cmd, pid_t pid){
    int i, n = (currentProc - 1) % SIZE;
    procs[n] = pid;
    cmds[n] = strdup(cmd);
    nrProcs[n] = currentProc;
    currentProc++;
    runningProcs++;
    return currentProc-1;
}

static void createLog(){
    int fd = open(LOG_FILE, O_CREAT | O_APPEND | O_WRONLY);
    dup2(fd,2);
    close(fd);
}

static void readNrProc(){
    int n;
    int fd = open(HISTORY_FILE, O_RDONLY, 0666);
    if( fd != -1 )
        read(fd,&currentProc,sizeof(int));
    close(fd);
}

/* --- Main ---------------------------------------------------- */
int main(){
    int rd_fifo, wr_fifo;
    memset(procs,-1,SIZE);

    if( mkfifo(RD_FIFO,0666) < 0 || mkfifo(WR_FIFO,0666) < 0 ){ 
        if( !strcmp("File exists",strerror(errno)) ){
            unlink(RD_FIFO);
            unlink(WR_FIFO);
            if( mkfifo(RD_FIFO,0666) < 0 || mkfifo(WR_FIFO,0666) < 0 ) {
                perror("Erro ao criar o named pipe");
                return -1;
            }
        }
        else{
            perror("Erro ao criar o named pipe");
            return -1;
        }
    }
    if( (rd_fifo = open(RD_FIFO,O_RDWR,0666)) < 0 ){
        perror("Erro ao abrir o named pipe");
        return -1;
    }
    if( (wr_fifo = open(WR_FIFO,O_RDWR,0666)) < 0 ){
        perror("Erro ao abrir o named pipe");
        return -1;
    }
    createLog();
    readNrProc();


    Request r;
    Answer a;
    DaemonRelay d;

    pid_t pid;
    char* m;
    int found, proc;

    signal(SIGINT,kill_handler);
    signal(SIGCHLD,sigchld_handler);

    int outPipe[2];
    pipe(outPipe);
    outPid = fork();
    if( !outPid ){
        close(outPipe[1]);
        Agr agr = initAgr(OUT_FILE, IDX_FILE,outPipe[0]);        
        aggregate(agr);
        destroyAgr(agr);
    }
    while( 1 ){
        r = createRequest(); 
        read(rd_fifo,r,requestSize());
        switch( getRequestType(r) ){
            case 'i' : ti = getRequestValue(r);
                       break;
            case 'm' : te = getRequestValue(r);
                       break;
            case 't' : proc = getRequestValue(r); 
                       if( procs[proc - 1] > 0 ){
                           kill(procs[proc - 1],SIGUSR1);
                       }
                       break;
            case 'e' : proc = currentProc;
                       pid = fork();
                       if( !pid ){
                           close(outPipe[0]);
                           Controller c = initController(proc,getRequestArgs(r),ti,te, outPipe[1]);
                           execute(c);
                           close(outPipe[1]);
                           destroyController(c);
                           _exit(0);
                       }
                       else{
                           found = insertProc(getRequestArgs(r),pid);
                           a = initAnswer('e', found);
                           write(wr_fifo,a,answerSize());
                       }
                       break;
            case 'l' : d = initDaemonRelay(wr_fifo);
                       sendList(d,cmds,runningProcs,SIZE);
                       destroyDaemonRelay(d);
                       break;
            case 'r' : d = initDaemonRelay(wr_fifo);
                       sendHistory(d,HISTORY_FILE);
                       destroyDaemonRelay(d);
                       break;
            case 'o' : d = initDaemonRelay(wr_fifo);
                       int pr = getRequestValue(r);
                       if( pr < currentProc )
                            sendOutput(d,OUT_FILE,IDX_FILE,getRequestValue(r));
                       destroyDaemonRelay(d);

        }
        destroyRequest(r);
    }

    return 0;
}
