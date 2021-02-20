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

    if( fd < 0 ){
        perror("Error opening file!");
        return 1;
    }

    if( *argv[1] == '-' ){
        switch( *(argv[1]+1) ){
            case 'i' :  if( argc != 4 ){
                            perror("Invalid number of arguments!");
                            close(fd);
                            return 1;
                        }
                        off_set = lseek(fd,0,SEEK_END);
                        strcpy(p.name,argv[2]);
                        p.age = atoi(argv[3]);
                        add(fd,&p);
                        printf("register %d\n", off_set);
                        break;
            case 'u' :  if( argc != 4 ){
                            perror("Invalid number of arguments!");
                            close(fd);
                            return 1;
                        }
                        update(fd , argv[2] , atoi(argv[3]));
                        break;
            case 'g' :  if( argc != 3 ){
                            perror("Invalid number of arguments!");
                            close(fd);
                            return 1;
                        }
                        s = get(&p , fd , argv[2]);
                        if( s == 0 )
                            perror("Name not found!");
                        else
                            printf("Name = %s\nAge = %d\n",p.name,p.age);
                        break;
            case 'U' :  if( argc != 4 ){
                            perror("Invalid number of arguments!");
                            close(fd);
                            return 1;
                        }
                        s = update_reg(fd , atoi(argv[2]) , atoi(argv[3])); 
                        if( !s )
                            perror("Register doesn't correspond to age!");
                        break;
            default  :  perror("Incompatible options!");
        }
    }
    else{
        perror("Incompatible options!");
        return 1;
    }
    close(fd);

    return 0;
}
