#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include "person.h"

int main(int argc, char** argv){
	int fd = open("db", O_CREAT | O_RDWR);
	int off_set;

	if( argc > 4 )
		return 1;

	Person p = NULL;
	switch( *(argv[1]+1) ){
		case 'i' :  p = malloc(sizeof(struct person));
					strcpy(p->name,argv[2]);
					p->age = atoi(argv[3]);
					off_set = add(fd,p);
					printf("registo %d\n", off_set);
					free(p);
					break;
		case 'u' :  update(fd,argv[2],atoi(argv[3]));
					break;
		case 'g' :  p = get(fd,argv[2]);
				    printf("\nNome = %s\nIdade = %d\n\n",p->name,p->age);
					break;
		default  :  perror("Invalid flags");
	}
}
