#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "person.h"

void add(int fd, Person *p){
	write(fd,p,sizeof(struct person));
}

int update(int fd, char*name, int age){
	ssize_t n;
	int found = 0;
	Person p;
	while( !found && (n = read(fd,&p,sizeof(struct person))) > 0 ){
		if( !strcmp(name, p.name) ){
			lseek(fd,-sizeof(int), SEEK_CUR);
			write(fd,&age,sizeof(int));
			found = 1;
		}
	}
	return found;
}

int update_reg(int fd, int reg, int age){
	int found = 0;

	if( !(reg % sizeof(struct person)) ){
		lseek(fd,reg + sizeof(char) * 100,SEEK_SET);
		write(fd,&age,sizeof(int));
		found = 1;
	}
	return found;
}

int get(Person *p, int fd, char* name){
	ssize_t n;
	int found = 0;

	while( !found && (n = read(fd,p,sizeof(struct person))) > 0)
		if( !strcmp(name,p->name) )
			found = 1;
	return found;
}
