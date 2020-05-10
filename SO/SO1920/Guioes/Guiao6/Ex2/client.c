#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){
    int n;
    char b[1024];
    int fd = open("fifo", O_WRONLY);

    while( (n = read(1,b,1024)) > 0 )
        write(fd,b,n);

	return 0;
}

