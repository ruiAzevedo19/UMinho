#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdio.h>

int main(){
    int n;
    char b[1024];

    int fd_file = open("log.txt", O_CREAT | O_APPEND | O_WRONLY, 0666); 
    int fd_fifo;

    while( 1 ){
        fd_fifo = open("fifo", O_RDONLY);
        printf("New client!\n");
        while( (n = read(fd_fifo,b,1024)) > 0 )
            write(fd_file,b,n);
    }
    close(fd_file);
    close(fd_fifo);

	return 0;
}

