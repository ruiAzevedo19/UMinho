#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

void printMatrix(int n, int m, int mtx[n][m]){
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++)
			printf("|  %d  |", mtx[i][j]);
		putchar('\n');
	}
}

void gerate(int n, int m, int mtx[n][m]){
	for(int i = 0; i < n; i++)
		for(int j = 0; j < m; j++)
			mtx[i][j] = rand() % 10;
}

int contains(int m[], int n, int x){
	int found = 0;

	for(int i = 0; i < n && !found; i++)
		if(m[i] == x)
			found = 1;
	return found;
}

int main(int argc, char **argv){
	int i = atoi(argv[1]);
	int j = atoi(argv[2]);
	int search = rand() % 10;
	int ps, status, found = 0;
	int mx[i][j];
	pid_t pid;
	gerate(i, j, mx);
	
	for(ps = 0; ps < i; ps++){
		pid = fork();
		if( pid == -1 ){
			perror("Fork failed!");
			return 1;
		} 
		if( pid == 0 ){
			found = contains(mx[ps],j,search);
			_exit(found);
		}
	}

	for(ps = 0; ps < i; ps++){
		pid = wait(&status);
		found = found || WEXITSTATUS(status);
	}

	printMatrix(i,j,mx);
	printf("Search := %d\n", search);
	printf("Found  := %d\n", found);
	return 0;
}
