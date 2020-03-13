#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>

int main(){
    printf("PID = %d\nPPID = %d\n",getpid(),getppid());
	return 0;
}

