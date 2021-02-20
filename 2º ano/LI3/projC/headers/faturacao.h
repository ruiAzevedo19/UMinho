#ifndef _FATURACAO_H
#define _FATURACAO_H

#include "../headers/estruturasAux.h"
#include "../headers/collection.h"
#include "../headers/heap.h"

typedef struct faturacao *Faturacao;

typedef struct productInfo *ProductInfo;

typedef struct totalSalesBillings *TotalSalesBillings;

/** Inicializa a estrutura TotalSalesBillings
 *
 * sales : numero de vendas
 * billings : faturacao
 */
TotalSalesBillings initTotalSalesBillings(int sales, double billings);

/** Destroi a estrutura TotalSalesBillings
 *
 * tsb : estrutura TotalSalesBillings
 */
void destroyTotalSalesBillings(TotalSalesBillings tsb);

/**
 * Devolve as vendas da estrutura TotalSalesBillings
 *
 * tsb : estrutura TotalSalesBillings
 *
 * return : numero de vendas
 */
int getTSBSales(TotalSalesBillings tsb);

/**
 * Devolve a faturacao da estrutura TotalSalesBillings
 *
 * tsb : estrutura TotalSalesBillings
 *
 * return : faturacao
 */
double getTSBBillings(TotalSalesBillings tsb);

/**
 * Inicia a estrutura Faturacap
 *
 * filiais : numero de filiais
 * modos : numero de modos de pagamento
 * depth: profundidade de um codigo
 * range : gama de valores de uma codigo
 * free_key_fun : funcao de libertacao das chaves
 *
 * return : estrutura faturacao
 */
Faturacao initFaturacao(int filiais, int modos, int depth,int range,void* free_key_fun);

/**
 * Destroi a estrutura de Faturacao
 *
 * f : estrutura Faturacao
 *
 */
void destroyFaturacao(Faturacao f);

/**
 * Adiciona um produto a faturacao com a respetiva chave
 *
 * f : estrutura Faturacao
 * key : codigo do produto
 */
void addProductSell(Faturacao f, char* key);

/**
 * Atualiza a chave correspondente a um codigo
 *
 * f : estrutura Faturacao
 * v : estrutura de vendas
 */
void updateSell(Faturacao f, Venda v);

/**
 * Devolve o numero de produtos nao comrpados
 *
 * f : estrutura da faturacao
 *
 * return : numero de produtos nao comprados
 */
int productsNeverBoughtNumber(Faturacao f);

/**
 * Devolve uma colecao com a faturacao de um mes de um deerminado produto
 *
 * f : estrutura Faturacao
 * mes : mes a procurar
 * prod : codigo do produto
 * globORfil : resultado global ou por filial
 *
 * return : colecao com a faturacao de um mes de um produto
 */
Collection vendfatMesProd(Faturacao f, int mes, char* prod,char globORfil);

/**
 * Adiciona os produtos nao comprados a uma colecao
 *
 * key : codigo do produto
 * pi : value correspondente a um produto
 * col[] : colecoes
 *
 * return : numero total
 */
int addProdNotBuyedFiliais(char* key, ProductInfo pi, Collection col[]);

/**
 * Devolve um conjunto de colecoes com produtos não comprados
 *
 * f : estrutura faturacao
 *
 * return : conjunto de colecoes
 */
Collection* notBuyed(Faturacao f);

/**
 * Devolve o total de vendas e total faturado num intervalo de meses relativo a um produto
 *
 * f : estrutura Faturacao
 * mesIni : mes inicial
 * mesFin : mes final
 *
 * return : total de vendas e total faturado num intervalo de meses
 */
TotalSalesBillings infoIntervMeses(Faturacao f, int mesIni, int mesFin);

// int getUnidadesFilialProd(ProductInfo p, int filial);

#endif
