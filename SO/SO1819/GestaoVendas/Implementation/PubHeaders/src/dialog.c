/*******************************************************************************
*
* NOME DO FICHEIRO :        item.c
*
* DESCRIÇÃO :
*       Declaração das funções sobre os artigos
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include "../modules/dialog.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>

/* --------------------------------------------------------------------------------- */
static const char *FIFO_DIR = 
                "../Fifos/";
//static const char *FIFO_DIR = 
//                "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Fifos/";

/* --------------------------------------------------------------------------------- */

/*
 * --> Funcao
 *		nameFifo
 * ----------------------------------------------------
 * --> Descricao
 *		Cria o nome do pipe
 * ----------------------------------------------------
 * --> Parametros
 *      pid     : pid do processo cliente 
 *      type    : request ou reply
 *      returns : nome do pipe
 */
char *nameFifo(int pid, char type){
    char *name = malloc(300);
    sprintf(name,"%s%c%d",FIFO_DIR,type,pid);
    return name;
}
/*
 * --> Funcao
 *		request_msg
 * ----------------------------------------------------
 * --> Descricao
 *		Cria uma mensagem para ser enviada ao servidor
 * ----------------------------------------------------
 * --> Parametros
 *      item_id : id do artigo
 *      returns : mensagem a ser enviada para o servidor
 */
RequestMsgBox request_msg(char type, int pid, int item_id, int price, int nr_items){
    RequestMsgBox msg  = malloc(sizeof(struct request_msg_box));
    
    msg->type = type;
    msg->pid = pid;
    msg->item_id = item_id;
    msg->price   = price; 
    msg->nr_items = nr_items;
    
    return msg;
}

/*
 * --> Funcao
 *		reply_msg
 * ----------------------------------------------------
 * --> Descricao
 *		Cria uma mensagem para ser enviada ao cliente
 * ----------------------------------------------------
 * --> Parametros
 *      price   : preco do artigo
 *      stock   : stock do artigo
 *      returns : mensagem a ser enviada para o cliente
 */
ReplyMsgBox reply_msg(float price, int stock){
    ReplyMsgBox msg  = malloc(sizeof(struct reply_msg_box));
    
    msg->price = price;
    msg->stock = stock;
    
    return msg;
}

/*
 * --> Funcao
 *		sendRequest
 * ----------------------------------------------------
 * --> Descricao
 *		Envia uma mensagem de pedido ao servidor
 * ----------------------------------------------------
 * --> Parametros
 *      fifo    : identificador do fifo publico
 *      msg     : mensagem a ser enviada
 *      returns : numero diferente de -1 em caso de sucesso, -1 caso contrario
 */
int sendRequest(int fifo, RequestMsgBox msg){
    int suc = -1;
    int try = 0;

    while( (suc = write(fifo,msg,sizeof(struct request_msg_box))) == -1 && try++ < 500); 
       // sleep(5);
    return suc;
}

/*
 * --> Funcao
 *		sendRequest
 * ----------------------------------------------------
 * --> Descricao
 *		Envia uma mensagem de resposta ao cliente
 * ----------------------------------------------------
 * --> Parametros
 *      fifo    : identificador do fifo publico
 *      msg     : mensagem a ser enviada
 *      returns : numero diferente de -1 em caso de sucesso, -1 caso contrario
 */
int sendReply(int fifo, ReplyMsgBox msg){
    int suc = -1;
    int try = 0;

    while( (suc = write(fifo,msg,sizeof(struct reply_msg_box))) == -1 && try++ < 500 );
        //sleep(5);
    return suc;
}

/*
 * --> Funcao
 *		readRequest
 * ----------------------------------------------------
 * --> Descricao
 *		Le o pedido do cliente
 * ----------------------------------------------------
 * --> Parametros
 *      fifo    : identificador do fifo publico
 *      returns : mensagem recebida do cliente
 */
RequestMsgBox readRequest(int fifo){
    RequestMsgBox msg = malloc(sizeof(struct request_msg_box));

    if( !read(fifo,msg,sizeof(struct request_msg_box)) )
        msg = NULL;
    return msg;
}

/*
 * --> Funcao
 *		readReply
 * ----------------------------------------------------
 * --> Descricao
 *		Le a resposta do servidor
 * ----------------------------------------------------
 * --> Parametros
 *      fifo    : identificador do fifo publico
 *      returns : mensagem recebida do servidor
 */
ReplyMsgBox readReply(int fifo){
    ReplyMsgBox msg = malloc(sizeof(struct reply_msg_box));

    if( !read(fifo,msg,sizeof(struct reply_msg_box)) )
        msg = NULL;
    return msg;
}

/* --------------------------------------------------------------------------------- */
