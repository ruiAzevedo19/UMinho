#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

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

int main(){
	
	sys("   head -3 ex1.c    ");	

	return 0;
}
