#include <unistd.h>
#include <stdio.h>

int main(){
    execlp("ls","-l",NULL);
    
    // Não é impresso pois o programa é subsituido pelo ls
    printf("Hello, world!\n");

    return 0;
}
