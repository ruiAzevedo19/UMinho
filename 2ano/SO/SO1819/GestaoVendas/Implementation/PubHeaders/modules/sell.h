/*******************************************************************************
*
* NOME DO FICHEIRO :        sell.h
*
* DESCRIÇÃO :
*       Prototipo das funcoes publicas do sell.c
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#ifndef SELL_H_
#define SELL_H_

/* --------------------------------------------------------------------------------- */

/* Estrutura de vendas */
typedef struct sell_record{
    int item_id;
    int nr_items;
    float amount;
}*SellRecord, SRecord;

int update_stock(int it_id, int nr_it);

int insert_srecord(SellRecord sr);

int insert_sell(int it_id, int nr_it);

/* --------------------------------------------------------------------------------- */

#endif 
