/*******************************************************************************
*
* NOME DO FICHEIRO :        cv.c
*
* DESCRIÇÃO :
*       Main do cliente de vendas
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include "../../PubHeaders/modules/parser.h"
#include "../modules/cv_api.h"
#include "../../PubHeaders/modules/dialog.h"
#include <string.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>

static const char *PUB_FIFO = 
                "../Fifos/pub_fifo";

//static const char *PUB_FIFO = 
//                "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Fifos/pub_fifo";

/* --------------------------------------------------------------------------------- */

int main(){
    int n;
    char *buf = malloc(80);
    /* Criacao dos fifos de comunicacao e abertura do fifo publico */
    int fifo = open(PUB_FIFO,O_WRONLY);
    int pid = getpid();
    char *request_fifo = nameFifo(pid,'s'), *reply_fifo = nameFifo(pid,'r');
    mkfifo(request_fifo, 0666); mkfifo(reply_fifo, 0666);

    /* Criar mensagem de log e enviar para o servidor */
    RequestMsgBox log = request_msg('l',pid,-1,-1,-1);
    sendRequest(fifo, log);
    free(log);

    /* Troca de mensagens entre cliente e servidor */
    int request = open(request_fifo, O_WRONLY);
    int reply   = open(reply_fifo, O_RDONLY);
    n = read(reply,buf,8);          // Espera por resposta ao pedido
    if( !strcmp(buf,"Sucesso") ) 
            communicate(fifo,request,reply);
    else
        printf("Falha no serviço de comunicação!\n");

    unlink(request_fifo);
    unlink(reply_fifo);
    //free(buf);
    //free(request_fifo);
    //free(reply_fifo);
    
    return 0;
}

/* --------------------------------------------------------------------------------- */
