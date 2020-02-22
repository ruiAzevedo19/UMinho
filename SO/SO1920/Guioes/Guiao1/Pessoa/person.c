#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "person.h"

int add(int fd, Person p){
	int r;
	lseek(fd,0,SEEK_END);
	write(fd,p,sizeof(struct person));
	r = lseek(fd,-sizeof(struct person),SEEK_CUR);
	return r;
}

int update(int fd, char*name, int age){
	ssize_t n;
	int found = 0;
	Person p = malloc(sizeof(struct person));
	while( !found && (n = read(fd,p,sizeof(struct person))) > 0 ){
		if( !strcmp(name, p->name) ){
			lseek(fd,-sizeof(struct person) + sizeof(char) * 100, SEEK_CUR);
			write(fd,&age,sizeof(int));
			found = 1;
		}
	}
	free(p);
	return found;
}

Person get(int fd, char* name){
	Person p = malloc(sizeof(struct person));
	ssize_t n;
	int found = 0;

	while( !found && (n = read(fd,p,sizeof(struct person))) > 0)
		if( !strcmp(name,p->name) )
			found = 1;
	return p;
}
