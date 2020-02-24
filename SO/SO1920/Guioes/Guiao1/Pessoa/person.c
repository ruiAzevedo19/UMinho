#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "person.h"

int add(int fd, Person *p){
	int r;
	lseek(fd,0,SEEK_END);
	write(fd,p,sizeof(struct person));
	r = lseek(fd,-sizeof(struct person),SEEK_CUR);
	return r;
}

int update(int fd, char*name, int age){
	ssize_t n;
	int found = 0;
	Person p;
	while( !found && (n = read(fd,&p,sizeof(struct person))) > 0 ){
		if( !strcmp(name, p.name) ){
			lseek(fd,-sizeof(struct person) + sizeof(char) * 100, SEEK_CUR);
			write(fd,&age,sizeof(int));
			found = 1;
		}
	}
	return found;
}

int update_reg(int fd, int reg, int age){
	int found = 0;

	if( !(reg % sizeof(struct person)) ){
		lseek(fd,reg + sizeof(char) * 100,SEEK_CUR);
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
