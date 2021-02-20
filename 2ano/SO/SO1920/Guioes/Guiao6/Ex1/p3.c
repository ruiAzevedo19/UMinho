#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdio.h>

int main(){
    char b[1024];
    int n;
    int fd = open("fifo",O_RDONLY);
    printf("Named pipe opened!\n");

    while( (n = read(fd,b,1024)) > 0 )
        write(1,b,n);

    close(fd);

	return 0;
}

