#include <signal.h> 
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

void alarm_handler(int sig){
	printf("Working ...\n");
}

int main(){
	signal(SIGALRM,alarm_handler);

	wh
	
	whiee
	alarm(1);
	
	return 0;
}
