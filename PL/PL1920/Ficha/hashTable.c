#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "hashTable.h"

#define N 5

	
Abr dictionary[N];

/* --- Funcao de hash --- */
int hash(char* abr){
	int prime = 7;
	for (int i = 0; i < strlen(abr); i++) 
   		prime = prime*31 + abr[i];
	return (prime % N);
}

/* --- Inicializa o dicionario --- */
void initDictionary(int n){
	for(int i = 0; i < n; i++)
		dictionary[i] = NULL;
}

/* --- Adiciona uma abreviatura ao dicionario --- */
void add(char* abr, char* mean){
	Abr entry = malloc(sizeof(Abbreviation));
	entry->abbreviation = strdup(abr);
	entry->meaning = strdup(mean);
	int h = hash(abr);
	entry->next = dictionary[h];
	dictionary[h] = entry;
}

/* --- Procura uma abreviatura no dicionario --- */
char* lookup(char* abr){
	char* mean = NULL;

	int h = hash(abr);
	Abr search = dictionary[h];

	for(int found = 0; !found && search; search = search->next)
		if( !strcmp(abr, search->abbreviation) ){
			mean = strdup(search->meaning);
			found = 1;
		}
	return mean;
}

