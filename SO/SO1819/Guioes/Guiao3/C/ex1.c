#include <unistd.h>
#include <stdio.h>

int main(){
	
	/* Execute ls -l */
	execlp("ls","ls","-l",NULL);

	/* Nothing from here is executed */
	printf("Hello word!\n");

	return 0;
}
