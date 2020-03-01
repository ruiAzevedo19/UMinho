#include "person.h"
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

int main(int argc, char** argv){
    if( argc < 2 ){
        perror("Number of elements need!");
        return 1;
    }

	int n = atoi(argv[1]);
	int fd = open("db", O_CREAT | O_WRONLY, 0666);
	Person p;

	for(int i = 0; i < n; i++){
		sprintf(p.name, "Nome %d", i);
		p.age = i;
		add(fd,&p);
	}
	close(fd);

	return 0;
}
