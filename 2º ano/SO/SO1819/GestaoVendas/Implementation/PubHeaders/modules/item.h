/*******************************************************************************
*
* NOME DO FICHEIRO :        item.h
*
* DESCRIÇÃO :
*       Prototipos das funcoes publicas de item.c
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#ifndef ITEM_H
#define ITEM_H

/* --------------------------------------------------------------------------------- */

/* Estrutura de um artigo */
typedef struct item{
     int id;             // codigo do artigo
     char *name;         // nome do artigo
     float price;        // preco do artigo
     int stock;          // quantidade em stock do artigo
}*Item;

/* Estrutura de um record de artigo */
typedef struct item_record{
	int id;
	int ref;
	float price;
}*ItemRecord, IRecord;

typedef struct log_box{
     int nr_it;
     int nr_str;
}*Log;

/* --------------------------------------------------------------------------------- */

int insert_name(char *nome);

int insert_irecord(ItemRecord it);

int insert_stock();

int insert_item(char *nome, float price);

int update_name(int id, char *new_name);

int update_price(int id, float price);

float get_price(int cod);

char *get_name(int ref);

ItemRecord get_irecord(int cod);

int get_stock(int cod);

Item get_artigo(int cod);

char *normalize_item(Item it);
#endif

/* --------------------------------------------------------------------------------- */
