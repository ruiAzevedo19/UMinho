/*******************************************************************************
*
* NOME DO FICHEIRO :        sell.c
*
* DESCRIÇÃO :
*       Declaração das funcoes referentes as vendas de artigos
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include "../modules/sell.h"
#include "../modules/item.h"

/* --------------------------------------------------------------------------------- */
static const char *STOCKS  = "../DataBase/stocks";
static const char *VENDAS  = "../DataBase/vendas";

//static const char *STOCKS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/stocks";
//static const char *VENDAS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/vendas";

/* --------------------------------------------------------------------------------- */

/*
 * --> Funcao
 *		update_stock
 * ----------------------------------------------------
 * --> Descricao
 *		Atualiza o stock de um artigo. Se o objetivo e 
 * fazer uma venda, entao e verificado se e possivel 
 * realiza-la. Se o objetivo e simplesmente aumentar
 * stock, entao nao e preciso fazer nehuma verificacao
 * e a atualizacao e feita de imediato.
 * ----------------------------------------------------
 * --> Parametros
 *		id		: id do artigo a alterar stock
 * 		nr_it   : quantidade de artigos a vender
 *		returns : retorna o numero de artigos possiveis vender
 */
int update_stock(int it_id, int nr_it){
    int stock = -1, off_set = it_id * sizeof(int);
    int fd = open(STOCKS, O_RDWR);
    
    if( lseek(fd,0,SEEK_END) < off_set )
        return stock;

    lseek(fd,off_set,SEEK_SET);
    read(fd,&stock,sizeof(int));
    lseek(fd,-sizeof(int),SEEK_CUR);
    if( nr_it < 0 ){
        nr_it = abs(nr_it);
        if( stock == 0 ){
            close(fd);
            return -1;
        }
        if( stock < nr_it )
            stock = 0;
        else
            stock -= nr_it;
    }
    else
        stock += nr_it;

    write(fd,&stock,sizeof(int));
    close(fd);

    return stock;
}

/*
 * --> Funcao
 *		insert_srecord
 * ----------------------------------------------------
 * --> Descricao
 *		Insere um record de vendas no ficheiro vendas
 * ----------------------------------------------------
 * --> Parametros
 *		sr      : sell record
 *      returns : 0 caso tenha inserido, 1 caso contrario
 */
int insert_srecord(SellRecord sr){
    int dx, n, suc = 1;
    int fdLog, fd = open(VENDAS, O_CREAT | O_WRONLY, 0777);

    n = lseek(fd,0,SEEK_END);
    printf("Valor do lseek das vendas = %d\n",n);
    switch( n ){
        case 0  : fdLog = open("../DataBase/logs", O_CREAT | O_RDWR, 0777);
                  dx = 0;
                  write(fdLog,&dx,sizeof(int));
                  write(fd,sr,sizeof(struct sell_record));
                  close(fdLog);
                  break;
        case -1 : 
                  break;

        default : write(fd,sr,sizeof(struct sell_record));
                  suc = 0;
    }
    close(fd);

    return suc;
}

/*
 * --> Funcao
 *		insert_sell
 * ----------------------------------------------------
 * --> Descricao
 *		Insere uma venda no ficheiro vendas
 * ----------------------------------------------------
 * --> Parametros
 *		it_id  	: id do artigo a vender
 *      nr_int  : quantidade de artigo a vender
 *		returns : retorna o byte onde começa o nome que inseriu
 */
int insert_sell(int it_id, int nr_it){
    SellRecord sr = NULL;
    int suc = 1;
    int stock   = update_stock(it_id,nr_it);
    float price = get_price(it_id);

    if( stock > -1 ){
        sr = malloc(sizeof(struct sell_record));
        sr->item_id  = it_id;
        sr->nr_items = abs(nr_it);
        sr->amount   = abs(nr_it) * price;
        if( insert_srecord(sr) )
            suc = 0;
        free(sr);
    }
    return suc;
}

/* --------------------------------------------------------------------------------- */
