#include <unistd.h>
#include <stdio.h>

int main(int argc, char **argv){
	argv[0] = "./ex4";

	execvp("./ex3",argv);

	return 0;
}
