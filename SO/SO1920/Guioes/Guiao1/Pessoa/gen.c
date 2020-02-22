#include "person.h"
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

int main(int argc, char** argv){
	int n = atoi(argv[1]);
	int fd = open("db", O_CREAT | O_RDWR);
	Person p = malloc(sizeof(struct person));

	for(int i = 0; i < n; i++){
		sprintf(p->name, "Nome %d", i);
		p->age = i;
		write(fd,p,sizeof(struct person));
	}
	close(fd);

	return 0;
}
