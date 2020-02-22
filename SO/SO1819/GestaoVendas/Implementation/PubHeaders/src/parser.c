/*******************************************************************************
*
* NOME DO FICHEIRO :        parser.c
*
* DESCRIÇÃO :
*       Funcoes de parsing e funcoes de strings
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include "../modules/parser.h"

/* --------------------------------------------------------------------------------- */
/*
 * --> Funcao
 *		splitMsg
 * ----------------------------------------------------
 * --> Descricao
 *	    Parte a mensagem recebida e guarda a diferente informacao
 * ----------------------------------------------------
 * --> Parametros
 *      msg     : mensagem enviada por um cliente
 *      pid     : apontador que guarda o pid do cliente que enviou a mensagem
 *      cod     : apontador que guarda o codigo do artigo a consultar
 *      quant   : apontador que guarda, caso exista, uma quantidade de produto
 *      returns : numero de palavras da mensagem
 */
int splitMsg(char *msg, int *cod, int *quant){
    char *word = NULL;
    int nr_cmds = 0;

    word = strtok(msg, " ");
    if( word ){
        *cod = atoi(word);
        nr_cmds++;
    }
    word = strtok(NULL, " ");
    if( word ){
        *quant = (word[0] == '-') ? (- atoi(word + 1)) : atoi(word);
        nr_cmds++;
    } 
    return nr_cmds;
}
/*
 * --> Funcao
 *		words
 * ----------------------------------------------------
 * --> Descricao
 *		Conta o numero de palavras de uma string
 * ----------------------------------------------------
 * --> Parametros
 *      cmd     : string
 *      returns : numero de palavras da string cmd
 */
int words(char *cmd){
    int i = 0;
    int w = 0;

    for(i = 0; cmd[i] && cmd[i] != ' '; i++);
    if( cmd[i] == ' ' ){
        w++;
        for( ; cmd[i] && cmd[i] == ' '; i++);
        if( cmd[i] ){
            for( ; cmd[i] && cmd[i] != ' '; i++);
            w++;
        }
    }
    return w;
}

/*
 * --> Funcao
 *		readln
 * ----------------------------------------------------
 * --> Descricao
 *		Le uma linha dado um descritor de ficheiro
 * ----------------------------------------------------
 * --> Parametros
 *      fildes  : descritor do ficheiro a ler
 *		buf     : buffer que contem a linha lida
 *		nbyte   : tamanho do buffer
 *      returns : numero de bytes lidos
 */
ssize_t readln(int fildes, char *buf, size_t nbyte){
	size_t nbytes = 0;
	int n;
	char c;

	while( nbytes < nbyte && (n = read(fildes,&c,1)) > 0 && c != '\n' )
		buf[nbytes++] = c;

	(nbytes < nbyte) ? (buf[nbytes] = '\0') : (buf[nbytes - 1] = '\0');	

	return nbytes;
}
/*
ssize_t readln(int fildes, char *buf, size_t nbyte){
    int found = 0, i = 0, nb = 0;

    while( !found ){
        nb += read(fildes, buf + nb, nbyte);
        for( ; i < nb && *(buf + i) != '\n' && *(buf + i) != '\r'; i++);
		if( nb == 0 )
			return nb;
        if( i < nb ){
            found = 1;
            (buf + i) = 0;
			lseek(fildes,i,SEEK_SET);
        }
        else
            buf = realloc(buf, i + nbyte);
    }
    return nb;
}
*/
/*
 * --> Funcao
 *		normalize
 * ----------------------------------------------------
 * --> Descricao
 *		Acrescenta um \n no final da string
 * ----------------------------------------------------
 * --> Parametros
 *		nome : nome a normalizar
 *      returns : nome com \n no fim
 */
char *normalize(char *nome){
	char *n = strdup(nome);
	n = realloc(n, strlen(n) * sizeof(char) + 1);
	sprintf(n,"%s\n",n);
	return n;
}

/*
 * --> Funcao
 *		nr_args
 * ----------------------------------------------------
 * --> Descricao
 *		Verifica se o numero de argumentos de um comando é
 * válido
 * ----------------------------------------------------
 * --> Parametros
 *		cmd     : comando para fazer verificacao
 *		args    : numero de argumentos suposto
 *      returns : nome com \n no fim
 */
int nr_args(char **cmd, int args){
	int i, len, find_empty = 0;

	for(i = 0; !find_empty && i < args; i++)
		len = strlen(cmd[i]);
		if( (i == 0 && len != 1) || (i > 0 && len < 1) )
			find_empty = 1;
	return find_empty;
}

/*
 * --> Funcao
 *		create_str
 * ----------------------------------------------------
 * --> Descricao
 *		Copia um intervalo de uma string para outra string
 * ----------------------------------------------------
 * --> Parametros
 *      str     : string a copiar
 *		i       : limite inferior
 *		j       : limite superior
 *      returns : sub-string de str
 */
static char *create_str(char *str, int i, int j){
	char *new = malloc((j - i + 2) * sizeof(char));
	int idx;

	for(idx = 0; idx + i <= j; idx++)
		new[idx] = str[idx + i];
	new[idx] = 0;

	return new;
}

/*
 * --> Funcao
 *		parser
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve o comando a executar no formato :
 *			cmd := <acao, nome/codigo, preco/nome>
 * ----------------------------------------------------
 * --> Parametros
 *		comand  : comando para fazer parse
 *      returns : sub-string de str
 */
char **parser(char *comand){
	char **cmd = (char **)malloc(4 * sizeof(char *));
	int i = 0, j, tmp;

	/* acao := cmd[0] */
	for(; comand[i] && comand[i] == ' '; i++);
	cmd[0] = malloc(sizeof(char));
	cmd[0][0] = comand[i];
	tmp = i;

	if( comand[tmp] == 'i' || comand[tmp] == 'p'){
		/* price := cmd[2] */
		for(j = strlen(comand) - 1; comand[j] == ' ' && j >= 0; j--);
		for(i = j; comand[i - 1] != ' ' && i >= 0; i--);
		cmd[2] = create_str(comand,i,j);

		/* nome/codigo := cmd[1] */
		for(j = i - 1; comand[j] == ' '; j--);
		for(i = ++tmp; comand[i] == ' '; i++);
		cmd[1] = create_str(comand,i,j);
	}
	else{
		/* codigo := cmd[1] */
		for(++i; comand[i] && comand[i] == ' '; i++);
		for(tmp = i; comand[tmp] && comand[tmp] != ' '; tmp++);
		cmd[1] = create_str(comand,i,--tmp);

		/* novo_nome := cmd[2] */
		for(++tmp; comand[tmp] && comand[tmp] == ' '; tmp++);
		cmd[2] = create_str(comand,tmp, strlen(comand) - 1);
	}

	/* null := cmd[3] */
	cmd[3] = 0;

	return cmd;
}

/* --------------------------------------------------------------------------------- */
