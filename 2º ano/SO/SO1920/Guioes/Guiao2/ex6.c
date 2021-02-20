#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define LINES 5
#define COLS  10000

void genMatrix(int matrix[LINES][COLS]){
    for(int i = 0; i < LINES; i++)
        for(int j = 0; j < COLS; j++)
            matrix[i][j] = i;
}

int find(int line[], int x){
    int i;
    for(i = 0; i < COLS && line[i] != x; i++);
    return (i == COLS) ? 0 : 1;
}

int main(int argc, char** argv){
    int n = atoi(argv[1]);
    int found = 0, status;
    int matrix[LINES][COLS];
    pid_t pid;

    genMatrix(matrix);

    for(int i = 0; i < LINES; i++){
        pid = fork();
        if( !pid ){
            found = find(matrix[i],n);
            _exit(found);
        }
    }
    for(int i = 0; i < LINES; i++){
        wait(&status);
        found += WEXITSTATUS(status);
    }

    found = (found > 0) ? 1 : 0;

    printf("Found elem %d? %d\n", n, found);

	return 0;
}

