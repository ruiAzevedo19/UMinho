#ifndef _CLOGPRODUCT_H_
#define _CLOGPRODUCT_H_

#include "catalog.h"
#include "collection.h"

typedef struct clogProduct *ClogProduct;

/*
 * Inicializa a estrutura de dados
 *
 * depth : profundidade (numero de letras)
 * range : quantidade diferente de letras
 * compare_fun : funcao de comparacao
 * free_key_fun : funcao de libertacao das chaves
 * free_value_fun : funcao de libertacao dos valores associados as chaves
 *
 * return : catalogo de produtos
 */
ClogProduct initClogProduct(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun);

/*
 * Destroi a estrutura de dados
 *
 * c : estrutura a destruir
 */
void destroyClogProduct(ClogProduct c);

/*
 * Adiciona um produto
 *
 * c: catalogo de produtos
 * key : produto a adicionar
 * value : valor correspondente
 */
void addProduct(ClogProduct c, void* key);

/**
 * Verifica se um produto existe
 *
 * key : produto
 *
 * return : 1 se existe, 0 caso contrario
 */
int containsProduct(ClogProduct c, void* key);

/*
 * Devolve os produtos que comecam por uma determinada letra
 *
 * p : catalogo de produtos
 * letter : letra a procurar
 *
 * return : estrutura answer
 */
Collection productByLetter(ClogProduct p, char letter);

/** Faz travessia ao catalogo de clientes
 *
 * c : catalogo de clientes
 * traverse_func : funcao para ser aplicada na travessia
 * data : apontador para uma estrutura a ser usada na travessia
 */
void traverseClogProducts(ClogProduct p, void* traverse_func, void* data);

#endif
