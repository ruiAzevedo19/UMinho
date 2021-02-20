#include "../headers/agr.h"

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/fcntl.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

/* --- Aggregation structure -------------- */
struct agr{         /*                      */
    int pipe;       /* fifo for aggregation */
    char* outFile;  /* name of output file  */
    char* idx;      /* index file           */
};                  /*                      */
/* ---------------------------------------- */

/* --- Constructor & Destructor ---------------------------------- */
Agr initAgr(const char* outFile, const char* idx, int pipe){
    Agr a = malloc(sizeof(struct agr));
    a->pipe = pipe;
    a->outFile = strdup(outFile);
    a->idx = strdup(idx);
    return a;
}

void destroyAgr(Agr a){
    free(a->outFile);
    free(a->idx);
    free(a);
}

/* --- Static ---------------------------------------------------- */
static void executeAgr(int out, int idx, int file){
    char fName[16], buf[512];
    sprintf(fName,"%d",file);
    int i = 0, nrBytes, outByte;
    ssize_t n;

    
    int f = open(fName, O_CREAT | O_RDWR, 0666);
    if( f != -1 ){
        nrBytes = lseek(f,0,SEEK_END);
        lseek(f,0,SEEK_SET);
        outByte = lseek(out,0,SEEK_END);
        lseek(idx, (file - 1) * 2 * sizeof(int), SEEK_SET);
        write(idx,&outByte,sizeof(int));
        write(idx,&nrBytes,sizeof(int));
        while( (n = read(f,buf,512)) > 0 ){
            i += n;
            write(out,buf,n);
            if( i > nrBytes )
                break;
        }
        close(f);
        unlink(fName);
    }
}

/* --- Functionality --------------------------------------------- */
void aggregate(Agr a){
    ssize_t n;
    int file;
    int out = open(a->outFile, O_CREAT | O_RDWR, 0666);
    int idx = open(a->idx, O_CREAT | O_RDWR, 0666);
    
    while( (n = read(a->pipe,&file,sizeof(int))) > 0 )
        executeAgr(out,idx,file);     

    close(out);
    close(idx);
} 
























