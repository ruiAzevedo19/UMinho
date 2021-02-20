#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdio.h>

int main(){
    char b[1024];
    int n;
    int fd = open("fifo",O_WRONLY);
    printf("Named pipe opened!\n");

    while( (n = read(1,b,1024)) > 0 )
        write(fd,b,n);

    close(fd);

	return 0;
}

