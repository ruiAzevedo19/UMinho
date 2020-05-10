#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdio.h>

int main(){
    if( mkfifo("fifo", 0666) == -1 )
        perror("Named pipe failed!");

	return 0;
}

