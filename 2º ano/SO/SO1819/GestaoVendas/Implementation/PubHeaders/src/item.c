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

#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "../modules/item.h"
#include "../modules/parser.h"

/* --------------------------------------------------------------------------------- */

static const char *STRINGS = "../DataBase/strings";
static const char *ARTIGOS = "../DataBase/artigos";
static const char *STOCKS  = "../DataBase/stocks";

//static const char *STRINGS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/strings";
//static const char *ARTIGOS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/artigos";
//static const char *STOCKS  = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/stocks";

/* --------------------------------------------------------------------------------- */

/*
 * --> Funcao
 *		compact_str
 * ----------------------------------------------------
 * --> Descricao
 *		Compacta o ficheiro strings se o desperdicio for de 20%
 * ----------------------------------------------------
 */
static void compact_str(){
	int fd_art = open(ARTIGOS, O_RDONLY);
	int fd_tmp = open("../DataBase/tmpstr", O_CREAT | O_RDWR);

	char *name = malloc(100);
	ItemRecord it = malloc(sizeof(struct item_record));

	while( read(fd_art,it,sizeof(struct item_record)) ){
		name = get_name(it->ref);
		sprintf(name,"%s\n",name);
		it->ref = lseek(fd_tmp,0,SEEK_END);
		lseek(fd_art,-sizeof(struct item_record), SEEK_CUR);
		write(fd_art,it,sizeof(struct item_record));
		write(fd_tmp,name,strlen(name));
		name[0] = 0;
	}
	close(fd_art);
	close(fd_tmp);
	unlink("../DataBase/strings");
	rename("../DataBase/tmpstr","../DataBase/strings");
}

/*
 * --> Funcao
 *		insert_name
 * ----------------------------------------------------
 * --> Descricao
 *		Insere o nome no ficheiro ARTIGOS	
 * ----------------------------------------------------
 * --> Parametros
 *		nome  	: nome do artigo
 *		returns : retorna o byte onde começa o nome que inseriu
 */
int insert_name(char *nome){
	int fd, ref;
	char *norm;

	/* Abre o ficheiro STRINGS e coloca o fd no fim do ficheiro */
	fd  = open(STRINGS, O_CREAT | O_WRONLY,  0777);
	ref = lseek(fd,0,SEEK_END);

	if( ref > -1 ){
		norm = normalize(nome);
		write(fd,norm, strlen(norm));
		free(norm);
	}
	close(fd);

	return ref;
}

/*
 * --> Funcao
 *		insert_irecord
 * ----------------------------------------------------
 * --> Descricao
 *		Calcula o id do artigo e adiciona-o ao ficheiro ARTIGOS
 * ----------------------------------------------------
 * --> Parametros
 *		it 		: estrutura a escrever
 *		returns : 0 se a inserção foi bem sucedida, 1 caso contrário
 */
int insert_irecord(ItemRecord it){
	int suc = 1;

	int fd = open(ARTIGOS, O_CREAT | O_WRONLY);
	int nb = lseek(fd,0,SEEK_END);

	it->id = nb / sizeof(struct item_record);
	if( fd > -1 ){
		write(fd,it,sizeof(struct item_record));
		suc = 0;
	}
	close(fd);

	return it->id;
}

/*
 * --> Funcao
 *		insert_stock
 * ----------------------------------------------------
 * --> Descricao
 *		Insere o stock a 0
 * ----------------------------------------------------
 * --> Parametros
 *		it		: estrutura a escrever
 *		returns : 0 se a inserção foi bem sucedida, 1 caso contrário
 */
int insert_stock(){
	 int stock = 0, suc = 1;
	 int fd = open(STOCKS, O_CREAT | O_WRONLY);
	 if( lseek(fd,0,SEEK_END) > -1 ){
	 	write(fd,&stock,sizeof(int));
		suc = 0;
	 }
	 close(fd);

	 return suc;	 
 }

/*
 * --> Funcao
 *		insert_item
 * ----------------------------------------------------
 * --> Descricao
 *		Insere um artigo no ficheiro ARTIGOS e o respetivo
 * nome no ficheiro STRINGS
 * ----------------------------------------------------
 * --> Parametros
 *		nome  	: nome do artigo
 *		price 	: preco do artigo
 *		returns : 0 se a inserção foi bem sucedida, 1 caso contrário
 */
int insert_item(char *nome, float price){
	int id = -1;
	ItemRecord it = malloc(sizeof(struct item_record));
	it->price = price;

	if( (it->ref = insert_name(nome)) > -1 ){
		id = insert_irecord(it);
		insert_stock();
	}

	return id;
}

/*
 * --> Funcao
 *		get_nr_items
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve o numero de artigos 
 * ----------------------------------------------------
 * --> Parametros
 *		returns : numero de artigos
 */
static int get_nr_items(){
	int fd = open(ARTIGOS,O_RDONLY);
	int n = lseek(fd,0,SEEK_END);
	close(fd);
	return n / sizeof(struct item_record);

}

/*
 * --> Funcao
 *		update_name
 * ----------------------------------------------------
 * --> Descricao
 *		Atualiza o nome de um artigo no ficheiro STRINGS
 * ----------------------------------------------------
 * --> Parametros
 *		cod  	: codigo do artigo
 *		nome 	: novo nome do artigo
 *		returns : 0 se a alteracao foi bem sucedida, 1 caso contrário
 */
/*! nao deixar inserir quando o id nao existe */
int update_name(int id, char *new_name){
	int suc = 1;
	int new_ref = insert_name(new_name);

	int fd = open(ARTIGOS,O_WRONLY);
	if( lseek(fd, id * sizeof(struct item_record) + sizeof(int), SEEK_CUR) > -1){
		write(fd, &new_ref, sizeof(int));
		suc = 0;
	}
	close(fd);

	return suc;
}

/*
 * --> Funcao
 *		update_price
 * ----------------------------------------------------
 * --> Descricao
 *		Atualiza o preco de um determinado artigo
 * ----------------------------------------------------
 * --> Parametros
 *		cod   	: codigo do artigo
 *		price 	: novo preco do artigo
 *		returns : 0 se a alteracao foi bem sucedida, 1 caso contrário
 */
int update_price(int id, float price){
	int suc = 1;
	int dx = id * sizeof(struct item_record) + 2 * sizeof(int);

	int fd = open(ARTIGOS, O_WRONLY);
	if( lseek(fd,dx,SEEK_CUR) > -1 ){
		write(fd,&price,sizeof(float));
		suc = 0;
	}
	close(fd);
	return suc;
}

/*
 * --> Funcao
 *		get_price
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve o preco de um determinado artigo
 * ----------------------------------------------------
 * --> Parametros
 *		cod   	: codigo do artigo
 *		returns : preco do artigo
 */
float get_price(int cod){
	float preco = -1;
	int off_set = cod * sizeof(struct item_record) + 2 * sizeof(int);
	int fd = open(ARTIGOS, O_RDONLY);

	if( lseek(fd,0,SEEK_END) < off_set){
		close(fd);
		return preco;
	}
	lseek(fd,off_set,SEEK_SET);
	read(fd,&preco,sizeof(float));
	close(fd);

	return preco;
}

/*
 * --> Funcao
 *		get_name
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve o nome de um artigo 
 * ----------------------------------------------------
 * --> Parametros
 *		cod     : codigo do artigo
 *		returns : nome do artigo
 */
char *get_name(int ref){
	char *nome = NULL;
	/* Abre o ficheiro strings e coloca o fd no inicio do nome a ler */
	int fd = open(STRINGS, O_RDONLY);
	if( lseek(fd,ref,SEEK_CUR) > -1){
		nome = malloc(30);
		readln(fd,nome,30);
	}
	close(fd);

	return nome;
}

/*
 * --> Funcao
 *		get_irecord
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve um registo de um artigo do ficheiro ARTIGOS
 * ----------------------------------------------------
 * --> Parametros
 *		cod   	: codigo do artigo
 *		returns : registo do artigo com codigo igual a cod
 */
ItemRecord get_irecord(int cod){
	ItemRecord it = NULL;
	int nb, offset = cod * sizeof(struct item_record);

	int fd = open(ARTIGOS, O_CREAT | O_RDONLY);
	nb = lseek(fd,0,SEEK_END);
	
	if( offset < nb ){
		lseek(fd,offset,SEEK_SET);
		it = malloc(sizeof(struct item_record));
		read(fd,it,sizeof(struct item_record));
	}
	close(fd);

	return it;	
 }

/*
 * --> Funcao
 *		get_stock
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve o stock de um artigo
 * ----------------------------------------------------
 * --> Parametros
 *		cod     : codigo do artigo
 *		returns : stock de um artigo
*/
int get_stock(int cod){
	int fd  = open(STOCKS, O_RDONLY);
	int stk = -1;
	
	if( lseek(fd,cod*sizeof(int),SEEK_CUR) > -1 )
		read(fd,&stk,sizeof(int));
	close(fd);

	return stk;
}

/*
 * --> Funcao
 *		normalize_item
 * ----------------------------------------------------
 * --> Descricao
 *		Cria uma string contendo toda a informacao do artigo
 * 	pronta para dispor no ecra
 * ----------------------------------------------------
 * --> Parametros
 *		it      : artigo a normalizar
 *		returns : string do artigo normalizada
 */
char *normalize_item(Item it){
	char *norm = malloc(200);
	sprintf(norm, "---------- Artigo %d ----------\nID    =	%d\nNome  =	%s\nPreço =	%.2f\nStock =	%d\n-------------------------------\n", it->id, it->id, it->name, it->price, it->stock);
	return norm;
 }

/*
 * --> Funcao
 *		get_artigo
 * ----------------------------------------------------
 * --> Descricao
 *		Devolve um artigo com toda a sua informação
 * ----------------------------------------------------
 * --> Parametros
 *		cod     : codigo do artigo
 *		returns : Artigo com toda a sua informacao
 */
Item get_artigo(int cod){
	Item item = NULL;
	ItemRecord it;
	char *nome;

	if( (it = get_irecord(cod)) ){
		if( (nome = get_name(it->ref)) ){
			item = malloc(sizeof(struct item));
			item->id    = cod;
			item->name  = nome;
			item->price = it->price;
			item->stock = get_stock(cod);
		}
	}

	return item;
}

/* --------------------------------------------------------------------------------- */


