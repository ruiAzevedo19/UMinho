#ifndef _CLOGCLIENT_H_
#define _CLOGCLIENT_H_

#include <glib.h>
#include "catalog.h"

typedef struct clogClient *ClogClient;

/**
 * Inicializa a estrutura catalogo de clientes
 *
 * depth : quantidade de letras que compoem o codigo
 * range : gama de valores das letras
 * compare_fun : funcao de comparacao
 * free_key : funcao para libertar as chaves
 */
ClogClient initClogClient(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun);

/**
 * Destroi a estrutura do catalogo de clientes
 *
 * c : catalogo a destruir
 */
void destroyClogClient(ClogClient c);

/**
 * Adiciona um cliente ao catalogo de clientes
 *
 * c : catalogo de clientes
 * key : cliente a adicionar
 */
void addClient(ClogClient c, void* key);

/**
 * Verifica se existe um cliente no catalogo
 *
 * c : catalogo de clientes
 * key : cliente a procurar
 *
 * return : 1 se existe, 0 caso contrario
 */
int containsClient(ClogClient c, void* key);

/**
 * Retorna uma copia do catalogo 
 *
 * c : catalogo de clientes 
 *
 * return : arvore clonada 
 */
GTree* cloneClientClog(ClogClient c);

/** Faz travessia ao catalogo de clientes 
 *
 * c : catalogo de clientes 
 * traverse_func : funcao para ser aplicada na travessia 
 * data : apontador para uma estrutura a ser usada na travessia 
 */
void traverseClogClients(ClogClient c, void* traverse_func, void* data);

#endif
