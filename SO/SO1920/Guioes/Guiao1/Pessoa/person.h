#ifndef PERSON_H
#define	PERSON_H

typedef struct person{
	char name[100];
	int age;
}Person;

/* Adiciona uma pessoa */
int add(int fd, Person *p);

/* Update da idade através do nome */
int update(int fd, char* name, int age);

/* Update da idade através do registo */
int update_reg(int fd, int reg, int age);

/* Devolve uma pessoa com um determinado nome */
int get(Person *p, int fd, char* name);


#endif
