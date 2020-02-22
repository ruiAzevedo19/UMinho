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

#ifndef DIALOG_H_
#define DIALOG_H_

/* Estrutura de uma mensagem de pedido ao servidor */
typedef struct request_msg_box{
    char type;       
    int pid; 
    int item_id;
    int price;      // manutencao de artigos
    int nr_items;   // venda de artigo || atualizacao de stock
}*RequestMsgBox;

/* Estrutura de uma mensagem de resposta ao cliente */
typedef struct reply_msg_box{
    char type;
    float price;
    int stock;
}*ReplyMsgBox;


/* --------------------------------------------------------------------------------- */

char *nameFifo(int pid, char type);

RequestMsgBox request_msg(char, int, int item_id, int price, int nr_items);

ReplyMsgBox reply_msg(float price, int stock);

int sendRequest(int fifo, RequestMsgBox msg);

int sendReply(int fifo, ReplyMsgBox msg);

RequestMsgBox readRequest(int fifo);

ReplyMsgBox readReply(int fifo);


/* --------------------------------------------------------------------------------- */

#endif
