#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include "readln.h"

#define N 4096

int main(int argc, char** argv){
    int fd = (argc == 1) ? 0 : open(argv[1], O_RDONLY);
    ssize_t n;
    int i = 1;
    char b[N];
    char l[N+6];
    
    if( fd < 0 ){
        perror("Error opening file!");
        return 1;
    }

    while( (n = readln_v2(fd,b,N)) > 0 ){
        b[n] = 0;
        sprintf(l,"\t%d   %s",i++,b);
        write(1,l,strlen(l));
    }

    return 0;
}
