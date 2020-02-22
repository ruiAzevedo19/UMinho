/*******************************************************************************
*
* NOME DO FICHEIRO :        ag.c
*
* DESCRIÇÃO :
*       Main do cliente de vendas
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

//        printf("N = %d  | Id : %d  | Quantidade = %d  |  Montante = %f\n", n,sr_search->item_id, sr_search->nr_items, sr_search->amount);

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include "../../PubHeaders/modules/sell.h"

static const char *PUB_FIFO = 
                "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/vendas";

/* --------------------------------------------------------------------------------- */

int main(){
    int nr_it = 0, n, fd = open("tmp", O_CREAT | O_RDWR, 0777);
    char sell[80];
    SellRecord sr_stdin  = malloc(sizeof(struct sell_record));
    SellRecord sr_search = malloc(sizeof(struct sell_record));

    while( (n = read(0,sr_stdin,sizeof(struct sell_record))) > 0){
        lseek(fd,sr_stdin->item_id * sizeof(struct sell_record),SEEK_SET);
        n = read(fd,sr_search,sizeof(struct sell_record));
        if( n > 0 ){
            sr_stdin->nr_items += sr_search->nr_items;
            sr_stdin->amount += sr_search->amount;
        }
        else
            nr_it++;
        lseek(fd,sr_stdin->item_id * sizeof(struct sell_record),SEEK_SET);
        write(fd,sr_stdin,sizeof(struct sell_record));
    }
char *buf = malloc(80);
    lseek(fd,0,SEEK_SET);
    for(int i = 0; i < nr_it; ){
        read(fd,sr_search,sizeof(struct sell_record));
        if( sr_search->nr_items > 0 && sr_search->nr_items > 0 ){
            //sprintf(buf,"%d %d %f\n",sr_search->item_id, sr_search->nr_items, sr_search->amount);
		    write(1,sr_search,sizeof(struct sell_record));
            //write(1,sr_search,sizeof(struct sell_record));
            i++;
        }
    }
	close(fd);

   remove("tmp");
    

    return 0;
}
/* --------------------------------------------------------------------------------- */
