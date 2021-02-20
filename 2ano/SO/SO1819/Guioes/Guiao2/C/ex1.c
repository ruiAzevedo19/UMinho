#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(){
	// id := processo do programa && ft := processo do pai(itnerpretador de comandos)
	pid_t id = getpid();
	pid_t ft = getppid();

	printf("id := %d\nft := %d\n",id,ft);

	return 0;
}
