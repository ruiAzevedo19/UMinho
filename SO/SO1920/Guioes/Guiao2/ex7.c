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
            matrix[i][j] = rand() % 10;
}

int find(int line[], int x){
    int i;
    for(i = 0; i < COLS && line[i] != x; i++);
    return (i == COLS) ? 0 : 1;
}

int main(int argc, char** argv){
    int n = atoi(argv[1]);
    int line, status;
    int matrix[LINES][COLS];
    int lines[LINES];
    memset(lines,0,LINES);
    pid_t pid;

    genMatrix(matrix);

    for(int i = 0; i < LINES; i++){
        pid = fork();
        if( !pid ){
            line = (find(matrix[i],n)) ? i : -1;
            _exit(line);
        }
    }
    for(int i = 0; i < LINES; i++){
        wait(&status);
        if( WEXITSTATUS(status) >= 0 )
            lines[WEXITSTATUS(status)] = 1;
    }

    printf("--- Lines that contain %d ---\n", n);
    for(int i = 0; i < LINES; i++)
        if( lines[i] )
            printf("Line %d\n", i);

	return 0;
}

