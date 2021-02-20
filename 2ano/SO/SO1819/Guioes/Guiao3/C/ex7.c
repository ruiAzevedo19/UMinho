#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "aux.h"

#define BASH  "mybash > "  // Bash message
#define PSIZE  9         // Prompt size
#define STDIN  0
#define STDOUT 1

int main(){
	int n, exit_flag = 0, background_flag = 0;
	char buf[1024];
	char **cmd;
	pid_t pid;

	while( !exit_flag ){
		/* writes prompt */
		write(STDOUT,BASH,PSIZE);
		/* if there is a command to process */
		if( (n = readln(STDIN,buf,1024)) ){
			/* if the last char is '&' the process will be done in background */
			if( buf[n - 1] == '&'){
				buf[n - 1] = '\0';
				background_flag = 1;	
			}
			cmd = words(buf);
			/* if the command is not exit */
			if( strcmp("exit",cmd[0]) ){
				pid = fork();
				/* Child process */
				if( pid == 0 ){
					execvp(cmd[0],cmd);
					_exit(-1);
				}
				free(cmd);
				/* Parent process */
				if( background_flag == 0 )
					waitpid(pid,NULL,0);
			}
			else
				exit_flag = 1;
		}
	}
	return 0;
}

