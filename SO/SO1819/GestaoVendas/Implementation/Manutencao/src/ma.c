/*******************************************************************************
*
* NOME DO FICHEIRO :        ma.c
*
* DESCRIÇÃO :
*       Main do programa manutencao de artigos
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "../modules/ma_api.h"
#include "../../PubHeaders/modules/parser.h"

/* --------------------------------------------------------------------------------- */

int main(){
	int n = -1, suc;
	char *command = NULL;
	char **cmd;

	while( n != 0 ){
		command = malloc(30);
		n = readln(0,command,30);
		if( n > 0 ){
			cmd = parser(command);
			suc = execute(cmd);
			free(cmd);
		}
		free(command);
	}
	return 0;
}

/* --------------------------------------------------------------------------------- */

