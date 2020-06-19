#ifndef _COLLECTION_H_
#define _COLLECTION_H_

typedef struct collection *Collection;

/**
 * Inicializa a estrutura de dados
 *
 * headers : numero de cabecalhos que a colecao tem
 * size : tamanho de alocacao inicial 
 *
 * return : colecao 
 */
Collection initCollection(int headers, int size);

/**
 * Destroi a estrutura de dados
 *
 * c : colecao a destruir
 */
void destroyCollection(Collection c);

/**
 * Adiciona um elemento a colecao
 *
 * c : colecao
 * header : cabecalho onde se pretende inserir a informacao
 * elem : elemento a inserir
 */
void addElem(Collection c, int header, char* elem);

/**
 * Devolve o numero de cabecalhos que a estrutura tem
 *
 * c : colecao
 *
 * return : numero de cabecalhos
 */
int getHeaders(Collection c);

/**
 * Devolve o numero maximo de elementos que um header tem 
 *
 * c : colecao 
 * 
 * return : numero maximo de elementos de um header 
 */
int getMaxHeaderIndex(Collection c);

/**
 * Devolve todos os indices dos cabecalhos 
 *
 * c : colecao
 *
 * return : indice do ultimo elemento inserido de todos os cabecalhos (corresponde ao numero de elementos de cada cabecalho) 
 */
int* getAllHeaderIndex(Collection);

/**
 * Devolve o indice do ultimo elemento inserido num determinado cabecalho
 *
 * c : colecao
 * header : cabecalho
 *
 * return : indice do ultimo elemento inserido num cabecalho (corresponde ao numero de elementos nesse cabecalho)
 */
int getHeaderIndex(Collection c, int header);

/**
 * Devolve uma copia de um cabecalho 
 *
 * c : colecao
 * header : cabecalho
 *
 * return : elementos de um determinado cabecalho
 */
char** getHeader(Collection c, int header);

/**
 * Devolve o elemento que está na posicao i de um determinado header
 *
 * c : colecao
 * header : cabecalho
 * i : posicao
 *
 * return : elemento na posicao i de um determinado header
 */
char* getHeaderElemIndex(Collection c, int header, int i);

/** 
 * Imprime um determinado cabecalho <-- apagar esta funcao posteriormente
 */
void printHeader(Collection c, int header);

#endif

