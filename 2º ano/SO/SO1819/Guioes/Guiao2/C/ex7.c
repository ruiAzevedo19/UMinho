#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>

/* Prints de matrix i  STDOUT */
void printMatrix(int n, int m, int **mtx/*[n][m]*/){
	for(int i = 0; i < n; i++){
		for(int j = 0; j < m; j++)
			printf("|  %d  |", mtx[i][j]);
		putchar('\n');
	}
}

/* Gerates a nxm matrix */
void gerate(int n, int m, int **mtx/*[n][m]*/){
	for(int i = 0; i < n; i++)
		for(int j = 0; j < m; j++)
			mtx[i][j] = rand() % 10;
}

/* true if array m contains element x */
int contains(int m[], int n, int x){
	int found = 0;

	for(int i = 0; i < n && !found; i++)
		if(m[i] == x)
			found = 1;
	return found;
}

int main(int argc, char **argv){
	if( argc < 3 ){
		perror("Insert lines and columns!");
		return 1;
	}
	
	/* matrix lines and columns passed by arguments */
	int i = atoi(argv[1]);
	int j = atoi(argv[2]);
	/* creats a matrix in the heap && gerates a random matrix */
	int **mx = (int **)malloc(sizeof(int *) * i);
	for(int m = 0; m < i; m++)
		mx[m] = (int *)malloc(sizeof(int) * j);
	gerate(i,j,mx);
	printMatrix(i,j,mx);
	/* variables */
	int ps, status, found = 0, search = rand() % 10;
	pid_t pid[i];


	/* (1) - create i processes, each one searching in a matrix line */
	for(ps = 0; ps < i; ps++){
		pid[ps] = fork();
		if( pid[ps] == -1 ){
			perror("Fork failed!");
			return 1;
		}
		/* Child process */
		if( pid[ps] == 0 ){
			if( (found = contains(mx[ps],j,search)) )
				_exit(1);
			_exit(0);
		}
	}
	free(mx);
	/* (2) - father catches all i childs */
	printf("Search := %d\n", search);
	for(ps = 0; ps < i; ps++){
		waitpid(pid[ps],&status,0);
		if( WEXITSTATUS(status) == 1 )
			printf("Linha %d | ",ps);
	}
	putchar('\n');

	return 0;
}





















