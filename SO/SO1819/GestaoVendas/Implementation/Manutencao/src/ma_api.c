/*******************************************************************************
*
* NOME DO FICHEIRO :        ma_api.c
*
* DESCRIÇÃO :
*       Declaracao das funcionalidades da manutencao de artigos. A maior parte das 
* funcionalidades encontram-se no modulo item.h
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
#include "../../PubHeaders/modules/item.h"
#include "../../PubHeaders/modules/parser.h"
#include "../../PubHeaders/modules/dialog.h"

/* --------------------------------------------------------------------------------- */

static const char *PUB_FIFO = 
             "../Fifos/pub_fifo";

//static const char *PUB_FIFO = 
//                "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Fifos/pub_fifo";

/* --------------------------------------------------------------------------------- */

/*
 * --> Funcao
 *		execute
 * ----------------------------------------------------
 * --> Descricao
 *		Executa o comando pedido
 * ----------------------------------------------------
 * --> Parametros
 *		cmd : comando a executar
 *		returns : 0 se a alteracao foi bem sucedida, 1 caso contrário
 */
int execute(char **cmd){
	int fd, id, suc = 1;
	Item it;
	char *norm;
	RequestMsgBox msg;

	if( strlen(cmd[0]) == 1 ) {
		switch( *cmd[0] ){
			case 'i' : id = insert_item(cmd[1],atof(cmd[2]));
					 if( id >= 0 ){
						char *id_ret = malloc(40);
						sprintf(id_ret,"Artigo inserido com o ID %d\n", id);
						write(1,id_ret,strlen(id_ret));
						free(id_ret);
						suc = 0;
					 }
					 break;

			case 'n' : suc = update_name(atoi(cmd[1]),cmd[2]);
				       break;

			case 'p' : suc = update_price(atoi(cmd[1]),atof(cmd[2]));
					   break;

			case 's' : it = get_artigo(atoi(cmd[1]));
					   if( it ){
							norm = normalize_item(it);
							write(1,norm,strlen(norm));
							free(norm);
							free(it->name);
							free(it);
							suc = 0;
					   }
					    break;
			case 'a' : printf("Recebi o comando a");
					   msg = malloc(sizeof(struct request_msg_box));
					   msg = request_msg('a',-1,-1,-1,-1);
					   fd = open(PUB_FIFO,O_WRONLY);
					   printf("Resultado do open %d\n",fd);
					   printf("Resultado do envio %d\n",sendRequest(fd,msg));
					   free(msg);
					   break;
		}
	}
	return suc;
}

/* --------------------------------------------------------------------------------- */


