#ifndef PERSON_H
#define	PERSON_H

typedef struct person{
	char name[100];
	int age;
}*Person;

int add(int fd, Person p);

int update(int fd, char* name, int age);

Person get(int fd, char* name);

#endif
