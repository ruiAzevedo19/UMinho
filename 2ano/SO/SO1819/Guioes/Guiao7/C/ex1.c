#include <signal.h> 
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int count_time = 0;
int count_ctrl = 0;

void ctrl_c_handler(int signal){
	printf("Time: %d\n", count_time);
	count_ctrl++;
}

void ctrl_slash_handler(int signal){
	printf("Ctrl-C pressed %d times\n", count_ctrl);		
}

int main(){
	signal(SIGINT,ctrl_c_handler);
	signal(SIGQUIT,ctrl_slash_handler);

	while( 1 ){
		count_time++;
		printf("Working ...\n");
		sleep(1);
	}

	return 0;
}
