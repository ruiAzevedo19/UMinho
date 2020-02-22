/*******************************************************************************
*
* NOME DO FICHEIRO :        sv_api.c
*
* DESCRIÇÃO :
*       Funcoes do servidor de vendas
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <time.h>
#include "../modules/sv_api.h"
#include "../../PubHeaders/modules/dialog.h"
#include "../../PubHeaders/modules/item.h"
#include "../../PubHeaders/modules/sell.h"

static const char *VENDAS  = "../DataBase/vendas";

//static const char *VENDAS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/vendas";

/* --- Funções encapsuladas --------------------------------------------------*/

/*
 * --> Funcao
 *		getPriceStock
 * ----------------------------------------------------
 * --> Descricao
 *	    Devolve o preco e o stock numa caixa de mensagem
 * ----------------------------------------------------
 * --> Parametros
 *      id      : id do artigo a consultar
 *      returns : caixa de mensagem com preco e stock
 */
static ReplyMsgBox getPriceStock(int id){
    ReplyMsgBox msg = malloc(sizeof(struct reply_msg_box));
    msg->type  = 'p';
    msg->price = get_price(id);
    msg->stock = get_stock(id);
    return msg;
}

/*
 * --> Funcao
 *		answerClient
 * ----------------------------------------------------
 * --> Descricao
 *	    Cria uma comunicacao com o cliente
 * ----------------------------------------------------
 * --> Parametros
 *      msg     : pedido de ligacao
 *      returns : resposta do servidor
 */
void answerClient(RequestMsgBox msg){
    int n;
    RequestMsgBox req_msg = malloc(sizeof(struct request_msg_box));
    ReplyMsgBox rep_msg;
    /* Fifos de comunicacao */
    char *request_fifo = nameFifo(msg->pid,'s');
    char *reply_fifo   = nameFifo(msg->pid,'r');

    int request = open(request_fifo, O_RDONLY);
    int reply   = open(reply_fifo,O_WRONLY);

    write(reply, "Sucesso", 8);
    while( (n = read(request,req_msg,sizeof(struct request_msg_box))) > 0 ){
        if( req_msg->type == 'c' ){
            rep_msg = getPriceStock( req_msg->item_id );
            sendReply(reply,rep_msg);
            free(rep_msg);
        }
        else{
            close(request);
            close(reply);
            break;
        }
    }
    free(req_msg);
}

/*
 * --> Funcao
 *		sell_or_update
 * ----------------------------------------------------
 * --> Descricao
 *	    Faz uma venda ou uma atualizacao de preco
 * ----------------------------------------------------
 * --> Parametros
 *      msg     : caixa de mensagem
 */
void sell_or_update(RequestMsgBox msg){
    char *reply_fifo = nameFifo(msg->pid,'r');
    int stock = -1, reply = open(reply_fifo,O_WRONLY);
    ReplyMsgBox rep_msg = malloc(sizeof(struct reply_msg_box));
    int try = 0;

    if( msg->nr_items >= 0 )
        stock = update_stock(msg->item_id, msg->nr_items);
    else
        insert_sell(msg->item_id, msg->nr_items);
    rep_msg->type = 'w';
    rep_msg->stock = get_stock(msg->item_id);
    while( (write(reply,rep_msg,sizeof(struct reply_msg_box))) == -1 && try++ < 50 )
        sleep(1);
    close(reply);
}

/*
 * --> Funcao
 *		agregate
 * ----------------------------------------------------
 * --> Descricao
 *	    Executa a agregacao
 * ----------------------------------------------------
 */ 
char* generate_agr_file(){
    time_t data = time(NULL);
    char* agr_file = malloc(50);
    strcat(agr_file,ctime(&data));
    return agr_file;
}

void agregate(int dxi, int dxj){
    int pid;
    char buf[80];
    char *agr_file = generate_agr_file();
    int fd_agr = open(agr_file, O_CREAT | O_RDWR, 0777);
    int fd_ven = open(VENDAS,O_RDONLY);
    lseek(fd_ven,dxi,SEEK_SET);
    SellRecord sr = malloc(sizeof(struct sell_record));

    int fd_in[2];
    int fd_out[2];

    pipe(fd_in);
    pipe(fd_out);

    pid = fork();
    if( pid > 0 ){
        close(fd_in[0]);
        close(fd_out[1]);
        while( dxi <= dxj && read(fd_ven,sr,sizeof(struct sell_record)) ){
            write(fd_in[1],sr,sizeof(struct sell_record));
            dxi += sizeof(struct sell_record);
        }
        close(fd_in[1]);
        while( read(fd_out[0],sr,sizeof(struct sell_record))){
            sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
		    write(fd_agr,buf,strlen(buf));
            buf[0] = 0;
        }
        close(fd_out[0]);
        close(fd_agr);
        close(fd_ven);
    }
    else{
        close(fd_in[1]);
        close(fd_out[0]);
        dup2(fd_in[0],0);
        dup2(fd_out[1],1);
        execlp("../Agregador/src/ag","../Agregador/src/ag",NULL);
        _exit(-1);
    }
}
/* --------------------------------------------------------------------------------- */
