#ifndef HASHTABLE_H
#define HASHTABLE_H

typedef struct entry{
	char* abbreviation;
	char* meaning;
	struct entry *next;
}*Abr, Abbreviation;

void initDictionary(int n);

void add(char* abr, char* mean);

char* lookup(char* abr);

#endif
