#include "vector.h"
#include <stdio.h>
#include <stdlib.h>

#define N 100
//variavel global
int v[N];

int main(int arg, char** argv){
	int f;
	// --- variavel global ---
	fill(v,N,1);
	f = find(v,N,0);
	printf("(1) f := %d\n", f);
	f = find(v,N,1);
	printf("(2) f := %d\n", f);
	// search out of bounds
	fill(v,N + 20, 3);
	f = find(v + N, 20, 3);
	printf("(3) f := %d\n", f);
	f = find(v + N, 20, 1);
	printf("(4) f := %d\n", f);
	// bounded search
	int i = 5, j = 8;
	int f_bound = find(v + i, N - j + i - 1, 3);
	printf("Bounded find = %d\n", f_bound);
	
	// --- variavel local ---
	int vector[N];
	fill(vector,N,1);
	f = find(vector,N,1);
	printf("(5) f := %d\n", f);
	f = find(vector,N,0);
	printf("(6) f := %d\n", f);
	// search out of bounds
	fill(vector,N+20,0);
	f = find(vector + N,20,0);
	printf("(7) f := %d\n", f);
	f = find(vector + N,20,1);
	printf("(8) f := %d\n", f);

	// --- variavel alocada dinamicamente ---
	int* vect = malloc(sizeof(int) * N);
	fill(vect,N,1);
	f = find(vect,N,1);
	printf("(9) f := %d\n", f);
	f = find(vect,N,0);
	printf("(10) f := %d\n", f);
	// search out of bounds
	fill(vect,N+20,0);
	f = find(vect + N,20,0);
	printf("(11) f := %d\n", f);
	f = find(vect + N,20,1);
	printf("(12) f := %d\n", f);

	return 0;
}

