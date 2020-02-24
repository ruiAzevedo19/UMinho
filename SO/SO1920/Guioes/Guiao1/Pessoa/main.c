#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include "person.h"

int main(int argc, char** argv){
	int fd = open("db", O_CREAT | O_RDWR);
	int off_set, s;
	Person p;

	if( argc > 4 )
		return 1;

	switch( *(argv[1]+1) ){
		case 'i' : 	strcpy(p.name,argv[2]);
					p.age = atoi(argv[3]);
					off_set = add(fd,&p);
					printf("register %d\n", off_set);
					break;
		case 'u' :  update(fd , argv[2] , atoi(argv[3]));
					break;
		case 'g' :  s = get(&p , fd , argv[2]);
					if( s == 0 )
						perror("Name not found!");
					else
						printf("Name = %s\nAge = %d\n",p.name,p.age);
					break;
		case 'U' :	s = update_reg(fd , atoi(argv[2]) , atoi(argv[3])); 
					if( !s )
						perror("Register doesn't correspond to age!");
					break;
		default  :  perror("Invalid flags");
	}
	return 0;
}
