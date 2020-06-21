#include "../headers/daemonRelay.h"
#include "../headers/parser.h"

#include <fcntl.h>
#include <sys/fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/* --- DaemonRelay structure ----- */
struct daemonRelay{ /*             */
    int fifo;       /* named pipe  */
};                  /*             */ 
/* ------------------------------- */

/* --- Construtor & Destructor ------------------------- */

DaemonRelay initDaemonRelay(int fifo){
    DaemonRelay d = malloc(sizeof(struct daemonRelay));
    d->fifo = fifo;
    return d;
}

void destroyDaemonRelay(DaemonRelay d){
    free(d);
}

/* --- Functionality ----------------------------------- */

void sendList(DaemonRelay d, char** argv, int p, int N){
    char msg[512];
    int n;

    write(d->fifo,&p,sizeof(int));
    for(int i = 0; i < N; i++)
        if(argv[i]){
            sprintf(msg,"#%d: %s\n",i+1,argv[i]);
            n = strlen(msg);
            write(d->fifo,&n,sizeof(int));
            write(d->fifo,msg,n);
        }
}

void sendHistory(DaemonRelay d, const char* hist_path){
    char msg[512];
    int n, m, rd;

    int fd = open(hist_path,O_RDONLY, 0666);
    read(fd,&n,sizeof(int));
    write(d->fifo,&n,sizeof(int));
    if( n > 0 )
        for(int i = 0; i < n; i++){
            memset(msg,0,512);
            rd = readlnFile(fd,msg,512);
            write(d->fifo,&rd,sizeof(int));
            write(d->fifo,msg,rd);
        }
    close(fd);
}

void sendOutput(DaemonRelay d, const char* outfile, const char* idx, int p){
    int res, div, init, nrBytes, i = 0;
    char buf[512] = {0};
    int id = open(idx, O_RDONLY,0666);
    int out = open(outfile, O_RDONLY,0666);

    if( id < 0 || out < 0 )
        return;
    
    lseek(id, (p-1) * 2 * sizeof(int), SEEK_SET);
    read(id,&init,sizeof(int));
    read(id,&nrBytes,sizeof(int));
    lseek(out,init,SEEK_SET);
    write(d->fifo,&nrBytes,sizeof(int));

    div = (int)nrBytes / 512;
    res = nrBytes % 512;

    for(int i = 0; i < div; i++){
        read(out,buf,512);
        write(d->fifo,buf,512);
    }
    read(out,buf,res);
    write(d->fifo,buf,res);
}

char* createHistoryMessage(int i, int p, char* cmd){
    char msg[512];

    switch( i ){
        case 0 : sprintf(msg,"#%d, concluída: %s\n",p,cmd);
                 break;
        case 1 : sprintf(msg,"#%d, max execucao: %s\n",p,cmd);
                 break;
        case 2 : sprintf(msg,"#%d, max inactivade: %s\n",p,cmd);
                 break;
        case 3 : sprintf(msg,"#%d, terminar: %s\n",p,cmd);
                 break;
    }
    return strdup(msg);

}
