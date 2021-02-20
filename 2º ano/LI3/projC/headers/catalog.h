#ifndef _CATALOG_H_
#define _CATALOG_H_

#define N 676

#include "../headers/collection.h"
#include <glib.h>

/** Estrutura de dados genérica que armazena catálogos **/
typedef struct catalog *Catalog;

/*
 * Inicializa um catálogo
 *
 * depth : profundidade do vetor (corresponde ao numero de letras de um codigo)
 * range : gama de valores das letras
 *
 * return : retorna um catalogo
 */
Catalog initCatalog(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun);

/**
 * Valida um codigo 
 *
 * depth : numero de letras do codigo 
 * ri : numero minimo do codigo 
 * rs : numero maixomo do codigo 
 * line : codigo a validar 
 *
 * return : 1 se o codigo e valido, 0 caso contrario
 */
int validate(int depth, int ri, int rs, char* line);

/*
 * Destroi um catalogo libertando toda a sua memoria
 *
 * c : catalogo a destruir
 */
void destroyCatalog(Catalog c);

/*
 * Devolve o campo size
 *
 * c : catalogo
 */
int getSize(Catalog c);

/*
 * Devolve o campo depth
 *
 * c : catalogo
 */
int getDepth(Catalog c);

/*
 * Devolve o campo range
 *
 * c : catalogo
 */
int getRange(Catalog c);

/*
 * Devolve o campo elems
 *
 * c : catalogo
 */
int getElems(Catalog c);

/*
 * Devolve o valor correspondente a uma chave
 *
 * c : catalogo
 * key : chave correspondente
 */
void* getValue(Catalog c, void* key);

/* 
 * Adiciona um codigo ao catalogo. Assume-se que o codigo ja esta validado
 *
 * c : catalogo
 * code : codigo a adicionar
 */
void addKeyValue(Catalog c, void* key, void* value);

/*
 * Verifica se um determinado codigo existe no catalogo
 * 
 * c : catalogo 
 * code : codigo do produto a verificar
 *
 * return : 1 se contem, 0 caso contrario
 */
int containsKey(Catalog c, void* key);

/*
 * Devolve uma cópia do catálogo 
 *
 * c : catalogo 
 *
 * return : copia do catalogo 
 */
GTree* cloneClog(Catalog c);

/*
 * Devolve os elementos que começam por uma determinada letra 
 *
 * c : catalogo
 * letter : letra pretendida 
 *
 * return : estrutura de resposta
 */  
Collection elemByLetter(Catalog c, char letter);

/**
 * Faz uma travessia ao catalogo 
 *
 * c : catalogo a fazer a travessia 
 * traverse_func : funcao a ser aplicada na travessia 
 * data : apontador para uma estrutura a ser usada na travessia
 */
void traverseCatalog(Catalog c, void* traverse_func, void* data);

/* Devolve o conjunto de produtos nao comprados
 *
 * c : catalogo 
 * filiais : numero de filiais 
 *
 * return : conjunto de collections 
 */
Collection* prodNotBuyed(Catalog c, int filiais);

#endif

