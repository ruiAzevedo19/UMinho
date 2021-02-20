#ifndef _PARSER_H_
#define _PARSER_H_

#include <unistd.h>

/**
 * Creates a list of words given an input
 *
 * @param in input string 
 * @param *nr_words saves number of words 
 * @returns list of words 
 */
char** words(char*, int*);

/**
 * Splits an input string in pipes 
 * @param char* input string 
 * @param int* number of commands
 */
char** splitPipe(char*,int*);

/**
 * frees the memory allocated for a list of strings
 *
 * @param char** list of words
 * @param int number of words in the list 
 */
void freeWords(char**, int);

/**
 * Reads one line from the file pointed by the file descriptor
 *
 * @param int file descriptor 
 * @param char* buffer containing the line read 
 * @param size_t number of elements to be read 
 * @returns number of elements read 
 */
ssize_t readln(int,char*,size_t);

/**
 * Reads one line from the file pointed by the file descriptor
 *
 * @param int file descriptor 
 * @param char* buffer containing the line read 
 * @param size_t number of elements to be read 
 * @returns number of elements read 
 */
ssize_t readlnFile(int,char*,size_t);

#endif

