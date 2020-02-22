/*******************************************************************************
*
* NOME DO FICHEIRO :        cv_api.c
*
* DESCRIÇÃO :
*       Modulo com a funcionalidade do cliente de vendas
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include "../../PubHeaders/modules/parser.h"
#include "../../PubHeaders/modules/dialog.h"
#include "../modules/cv_api.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

/* --------------------------------------------------------------------------------- */

/*
 * --> Funcao
 *		buf_to_msg
 * ----------------------------------------------------
 * --> Descricao
 *		Converte um buffer numa request message
 * ----------------------------------------------------
 * --> Parametros
 *      buf     : buffer para conversao
 *      returns : request message
 */
static RequestMsgBox buf_to_msg(char *buf){
    int cod, quant;
    int nr_cmd = splitMsg(buf,&cod,&quant);
    RequestMsgBox msg = NULL;

    if( nr_cmd == 1 ){
        msg = malloc(sizeof(struct request_msg_box));
        msg->type = 'c';
        msg->pid  = -1;
        msg->item_id = cod;
        msg->price    = -1;
        msg->nr_items = -1;
    }

    if( nr_cmd == 2 ){
        msg = malloc(sizeof(struct request_msg_box));
        msg->type = 'v';
        msg->pid  = getpid();
        msg->item_id = cod;
        msg->price    = -1;
        msg->nr_items = quant;
    }
    return msg;
}

/*
 * --> Funcao
 *		printMessage
 * ----------------------------------------------------
 * --> Descricao
 *		Imprime uma mensagem no terminal
 * ----------------------------------------------------
 * --> Parametros
 *      msg     : mensagem a imprimir
 */
static void printMsg(ReplyMsgBox msg){
    char *print = malloc(50);

    if( msg->type == 'p' )
        sprintf(print,"Preço = %f\nStock = %d\n",msg->price, msg->stock);
    else
        sprintf(print,"Novo Stock = %d\n",msg->stock);

    write(1,print,strlen(print));
    free(print);
}

/*
 * --> Funcao
 *		communicate
 * ----------------------------------------------------
 * --> Descricao
 *		Faz a comunicacao com o servidor
 * ----------------------------------------------------
 * --> Parametros
 *      request : descritor do fifo de envio de pedidos
 *      reply   : descritor do fifo de resposta
 */
void communicate(int fifo,int request, int reply){
    int n;
    char *buf = malloc(80);
    RequestMsgBox request_msg;
    ReplyMsgBox reply_msg;

    while( (n = readln(0,buf,80)) > 0){
        if( buf[0] ){
            request_msg = buf_to_msg(buf);
            if( request_msg->type == 'c' )
                sendRequest(request,request_msg);
            else {
                if( request_msg->type == 'v' )
                    sendRequest(fifo,request_msg);
            }
            reply_msg = readReply(reply);
            printMsg(reply_msg);
            free(reply_msg);
            buf[0] = 0;
        }
    }
    request_msg->type = 'e';
    sendRequest(request,request_msg);
    free(request_msg);
    free(buf);
}
/* --------------------------------------------------------------------------------- */
