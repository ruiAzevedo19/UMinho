#ifndef _PAGE_H_
#define _PAGE_H_

#include "../headers/collection.h"

typedef struct page *Page;

/**
 * Inicio a estrutura Page
 *
 * c : colecao
 * pageElems : numero de paginas
 * offset : offset de leitura
 *
 * return : estrutura Page
 */
Page initPage(Collection c, int pageElems, int readOffset);

/**
 * Destroi a estrutura page
 *
 * p : estrutura page
 */
void destroyPage(Page p);

/**
 * Define a página atual
 *
 * p : estrutura page
 * page : pagina atual
 *
 * return : 1 se a pagina é válida
 */
int setCurrentPage(Page p, int page);

/**
 * Devolve a pagina atual
 *
 * return : pagina atual
 */
int getCurrentPage(Page p);

/**
 * Devolve o numero de paginas
 *
 * page : estrutura pagina
 *
 * return : numero de paginas
 */
int getNrPages(Page p);

/**
 * Devolve o numero de elementos de uma pagina
 *
 * page : estrutura page
 *
 * return : numero de elementos de uma pagina
 */
int getPageNrElems(Page p);

/**
 * Devolve uma pagina
 *
 * p : estrutura page
 * index : inicio da pagina
 *
 * return : uma pagina
 */
char** getSinglePage(Page p, int index);

#endif
