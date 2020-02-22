/*******************************************************************************
*
* NOME DO FICHEIRO :        parser.h
*
* DESCRIÇÃO :
*       Prototipos das funcoes publicas de parser.c
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#ifndef PARSER_H_
#define PARSER_H_

#include <fcntl.h>
#include <unistd.h>

int splitMsg(char *msg, int *cod, int *quant);

ssize_t readln(int, char *, size_t);

char **parser(char *);

char *normalize(char *);

int nr_args(char **, int);

#endif

/* --------------------------------------------------------------------------------- */
