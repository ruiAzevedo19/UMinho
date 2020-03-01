#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include "readln.h"

#define N 4096

int main(int argc, char** argv){
    int fd = (argc == 1) ? 0 : open(argv[1], O_RDONLY);
    ssize_t n;

    if( fd < 0 ){
        perror("Error opening file!");
        return 1;
    }
    
    char b[N];
    while( (n = readln_v2(fd,b,N)) > 0 )
        write(1,b,n);

    return 0;
}
