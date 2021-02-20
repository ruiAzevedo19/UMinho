#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include "aux.h"


ssize_t readln(int fildes, void *buf, size_t nbyte){
	ssize_t nbytes = 0;
	int n;
	char c;
	char *buffer = (char *)buf;

	// So le o numero de caracteres corresponde ao tamanho do buffer
	while( nbytes < nbyte && (n = read(fildes,&c,1)) > 0 && c != '\n' )
		buffer[nbytes++] = c;

	(nbytes < nbyte) ? (buffer[nbytes] = '\0') : (buffer[nbytes-- - 1] = '\0');

	return nbytes;
}

/* Counts the number of words */
int word(char *buf){
    int i, count = 0;

    for(i = 0; buf[i] && buf[i] == ' '; i++);

    for(; buf[i]; i++)
        if( buf[i] != ' ' && (buf[i + 1] == ' ' || buf[i + 1] == '\0') )
            count++;
    return count;
}

/* Creates char **argv */
void cpy(char **w, int idx, char *command, int i, int j){
	w[idx] = malloc(sizeof(char) * (j - i + 1));
	int k = 0;
	while( i < j){
		w[idx][k] = command[i];
		i++; k++;;
	}
	w[idx][k] = '\0';
}

/* Reads the command and break it in words */
char **words(char *command){
	int i,j,idx = 0;
	char **w = (char **)malloc(word(command) * sizeof(char *));
	/* clears all the empty spaces */
	for(i = 0; command[i] && command[i] == ' '; i++);

	j = i;
	while( command[j] ){
		if( command[j] != ' ' )
			j++;
		else{
			cpy(w,idx,command,i,j);
			idx++;
			for(; command[j] && command[j] == ' '; j++);
			i = j;
		}
	}
	if( command[i] )
		cpy(w,idx++,command,i,j);

	w[idx] = NULL;

	return w;
}

/* Execute the command */
int sys(char *command){
	if( !command )
		return 1;
	pid_t pid;
	char **argv = words(command);
	pid = fork();
	if( pid == -1 )
		return 1;
	/* Parent process */
	if( pid > 0 ){
		wait(NULL);
		return 0;
	}
	/* Child process */
	else{
		execvp(argv[0], argv);
		_exit(-1);
	}
}
