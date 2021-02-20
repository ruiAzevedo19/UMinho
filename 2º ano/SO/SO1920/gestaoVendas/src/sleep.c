#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char** argv){
    int i = 0;
    char str[128];

    if( argc < 2 ){
        perror("Invalid number of arguments!");
        return 1;
    }
    sprintf(str,"sleep %d : end of program\n",atoi(argv[1]));

    sleep( atoi(argv[1]) );
    write(1,str,strlen(str));
    
    return 0;
}
