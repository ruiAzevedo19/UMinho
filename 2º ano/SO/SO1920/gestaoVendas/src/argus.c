#include "../headers/argusRelay.h"

#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char** argv){
    int wr_fifo = open("request_fifo", O_WRONLY);
    int rd_fifo = open("answer_fifo",O_RDONLY);

    if( wr_fifo < 0 || rd_fifo < 0 ){
        perror("Error opening pipe!");
        return -1;
    }

    ArgusRelay ar = initArgusRelay(wr_fifo,rd_fifo);

    if( argc > 1 )
        cmdln(ar,argv + 1,argc - 1);
    else 
        bash(ar);

    destroyArgusRelay(ar);
    close(wr_fifo);
    close(rd_fifo);

    return 0; 
}
