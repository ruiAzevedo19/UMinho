#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

int main(){
	int n;
	char c;

	while( (n = read(0,&c,1)) > 0 )
		write(1,&c,1);
	
	return 0;
}

