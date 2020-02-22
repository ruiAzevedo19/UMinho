#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

static const char *CV_PATH = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Cliente/cv";

int main(){
    int i;
    int pid;
    for(i = 0; i < 10; i++){
        pid = fork();
        if( pid == 0 ){
		  printf("Filho %d\n",i);
    int fd = open("sell_script", O_RDONLY, 0777);
            dup2(fd,0);
            close(fd);
            execv(CV_PATH,NULL);
            _exit(-1);
        }   
    }
    for(i = 0; i < 10; i++)
    	wait(NULL);
    return 0;
}
