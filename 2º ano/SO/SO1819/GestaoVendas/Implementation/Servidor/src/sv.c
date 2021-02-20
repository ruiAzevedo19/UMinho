/*******************************************************************************
*
* NOME DO FICHEIRO :        sv.c
*
* DESCRIÇÃO :
*       Prototipos das funcoes publicas de parser.c
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include "../modules/sv_api.h"
#include "../../PubHeaders/modules/dialog.h"
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>

static const char *PUB_FIFO  = "../Fifo/pub_fifo";
static const char *LOG  = "../DataBase/logs";
static const char *VENDAS  = "../DataBase/vendas";

/*
static const char *PUB_FIFO = 
            "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Fifos/pub_fifo";

static const char *LOG = 
            "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/logs";

static const char *VENDAS = 
            "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/vendas";

*/
/* --------------------------------------------------------------------------------- */

int end = 0;

void sig_term_handler(){
    end = 1;
}

/* --------------------------------------------------------------------------------- */

int main(){
    /* sinais */
    signal(SIGTERM, sig_term_handler);
    int nr_proc = 0, dxi, dxj;
    /* Abre o fifo publico para receber pedidos de conexao */
    mkfifo(PUB_FIFO, 0666);
    int fd_log, fd_ven, pid, fifo = open(PUB_FIFO, O_RDONLY);
    RequestMsgBox msg;

    while( !end ){
        while ( (msg = readRequest(fifo)) == NULL) ;  
        switch( msg->type ){
            case 'l'  : /* Mensagem de log */
                        pid = fork();
                        nr_proc++;
                        if( pid == 0 ){
                            answerClient(msg);
                            _exit(0);
                            nr_proc--;
                        }
                        break;
            case 'v'  : /* Mensagem de venda */
                        sell_or_update(msg);
                        break;
            case 'a'  : /* Mensagem para executar o agregador */
                        nr_proc++;
                        fd_log = open(LOG, O_RDWR, 0777);
                        fd_ven = open(VENDAS,O_RDONLY);
                        read(fd_log,&dxi,sizeof(int));
                        dxj = lseek(fd_ven,0,SEEK_END);
                        close(fd_log);
                        fd_log = open(LOG, O_RDWR);
                        write(fd_log, &dxj, sizeof(int));
                        lseek(fd_log,0,SEEK_SET);
                        int x;
                        read(fd_log,&x,sizeof(int));
                        close(fd_log);
                        close(fd_ven);
                        pid = fork();
                        if( pid == 0 ){
                            agregate(dxi,dxj);
                            _exit(0);
                        }
                        nr_proc--;
                        break;  
        }
        free(msg);
    }
    unlink(PUB_FIFO);
    return 0;
}

/* --------------------------------------------------------------------------------- */
