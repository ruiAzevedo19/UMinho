#ifndef _GESTAO_H_
#define _GESTAO_H_

#include "../headers/estruturasAux.h"
#include "../headers/collection.h"
#include <glib.h>

typedef struct gestao *Gestao;

typedef struct cpNeverBought *CPNeverBought;

typedef struct totalBought *TotalBought;

typedef struct productAmount *ProductAmount;

/**
 * Inicia a estrutura ProductAmount
 *
 * product : codigo de produto
 * billing : faturacao
 * amount : quantidade vendida
 *
 * return : estrutura ProductAmount
 */
ProductAmount initProductAmount(char* product, double billing, int amount);

/**
 * Devolve o numero total de vendas
 *
 * pa : estrutura productAmount
 *
 * return : numero total de vendas
 */
int getPATotalSales(ProductAmount pa);

/**
 * Devolve o numero total de clientes que compraram um produto
 *
 * pa : estrutura productAmount
 *
 * return : numero total de clientes que compraram um produto
 */
int getPATotalClients(ProductAmount pa);

/**
 * Devolve o numero de vendas de uma filial
 *
 * pa : estrutura productAmount
 * i : filial
 *
 * return : numero de vendas de uma filial
 */
int getPASalesAt(ProductAmount pa, int i);

/**
 * Devolve o numero de clientes de uma filial
 *
 * pa : estrutura productAmount
 * i : filial
 *
 * return : numero de vendas
 */
int getPAClientsAt(ProductAmount pa, int i);

/**
 * Define o numero total de vendas
 *
 * pa : estrutura productAmount
 * total : numero total
 */
void setPATotalSales(ProductAmount pa, int total);

/**
 * Define o numero total de clientes
 *
 * pa : estrutura productAmount
 * total : numero total
 */
void setPATotalClients(ProductAmount pa, int total);

/**
 * Define o numero de vendas de uma filial
 *
 * pa : estrutura productAmount
 * i : filial
 * sales : vendas
 */
void setPASalesAt(ProductAmount pa, int i, int sales);

/**
 * Define o numero de clientes de uma filial
 *
 * pa : estrutura productAmount
 * i : filial
 * clients : clientes
 */
void setPAClientsAt(ProductAmount pa, int i, int clients);

/**
 * Destroi a estrutura ProductAmount
 *
 * pa : estrutura productAmount
 */
void destroyProductAmount(ProductAmount pa);

/**
 * Devolve o codigo de produto da estrutura productAmount
 *
 * pa : estrutura productAmount
 *
 * return : codigo de produto
 */
char* getProductAmountProduct(ProductAmount pa);

/**
 * Devolve a faturacao da estrutura productAmount
 *
 * pa : estrutura productAmount
 *
 * return : faturacao
 */
double getProductAmountBilling(ProductAmount pa);

/**
 * Devolve o numero de vendas da estrutura productAmount
 *
 * pa : estrutura productAmount
 *
 * return : numero de vendas
 */
int getProductAmountAmount(ProductAmount pa);

/**
 * Funcao de comparacao da estrutura ProductAmounts
 *
 * p1 : estrutura productAmount
 * p2 : estrutura productAmount
 *
 * return : relacao de comparacao
 */
int comparePA(ProductAmount p1, ProductAmount p2);

/**
 * Funcao de comparacao da estrutura ProductAmounts
 *
 * p1 : estrutura productAmount
 * p2 : estrutura productAmount
 *
 * return : relacao de comparacao
 */
int compProductAmount(ProductAmount p1, ProductAmount p2);

/**
 * Funcao de comparacao da estrutura ProductAmounts
 *
 * p1 : estrutura productAmount
 * p2 : estrutura productAmount
 *
 * return : relacao de comparacao
 */
int comparePAmount(ProductAmount p1, ProductAmount p2);

/**
 * Devolve o numero de bytes ocupados pela estrutura ProductAmount
 *
 * return : numero de bytes ocupados pela estrutura ProductAmount
 */
int sizeofProductAmount();

/**
 * Inicia a estrutura TotalBought
 *
 * filiais : numero de filiais
 *
 * return : estrutura TotalBought
 */
TotalBought initTotalBought(int filiais);

/**
 * Destroi a estrutura TotalBought
 *
 * tb : estrutura a destruir
 */
void destroyTotalBought(TotalBought tb);

/**
 * Define os campos da estrutura TotalBought
 *
 * tb : estrutura TotalBought
 * filial : filial a definir
 * month : mes
 * sell : numero de vendas
 */
void setTotalBought(TotalBought tb, int filial, int month, int sell);

/**
 * Devolve o total comprado numa determinada filial e num determinado mes
 *
 * tb : estrutura TotalBought
 * filial : filial
 * month : mes
 *
 * return : total comprado
 */
int getTotalBought(TotalBought tb, int filial, int month);

/**
 * Devolve todas as vendas totais
 *
 * tb : estrutura a destruir
 *
 * return : todas as vendas totais
 */
int* getTotalBoughtTable(TotalBought tb);

/**
 * Devolve o numero de elementos do conteudo
 *
 * tb : estrutura a destruir
 *
 * return : numero de elementos do conteudo
 */
int getTotalBoughtDim(TotalBought tb);


/**
 * Inicia a estrutura gestao
 *
 * depth : numero de letras diferentes do codigo de clientes
 * range : gama de valores do codigo
 * funcao de comparacao de codigos de clientes
 * funcao de libtertacao de chaves
 *
 * return : estrutura gestao
 */
Gestao initGestao(int depth, int range, void* compare_fun, void* free_key_fun);

/**
 * Inicia a estrutura cpNeverBought
 *
 * c : clientes
 * p : produtos
 *
 * return : estrutura CPNeverBought
 */
CPNeverBought initCPNeverBought(int c, int p);

/**
 * Devolve o numero de clientes da estrutura CPNeverBought
 *
 * cp : estrutura CPNeverBought
 *
 * return : numero de clientes
 */
int getClientsNeverBought(CPNeverBought cp);

/**
 * Devolve o numero de produtos da estrutura CPNeverBought
 *
 * cp : estrutura CPNeverBought
 *
 * return : numero de produtos
 */
int getProductsNeverBought(CPNeverBought cp);

/**
 * Destroi a estrutura gestao
 *
 * g : estrutura a destruir
 */
void destroyGestao(Gestao g);

/**
 * Destroi a estrutura CPNeverBought
 *
 * cp : estrutura a destruir
 */
void destroyCPNeverBought(CPNeverBought cp);

/**
 * Verifica se existe um cliente na gestao
 *
 * g : estrutura gestao
 * key : codigo do cleinte
 *
 * return : 1 se existe
 */
int containsGestaoClient(Gestao g, char* key);

/**
 * Adiciona um cliente a gestao
 *
 * g : estrutura gestao
 * key : codigo de cliente
 */
void addClientGestao(Gestao g, char* key);

/**
 * Atualiza a informação relativa a um cliente
 *
 * g : estrutura gestao
 * v : estrutura de vendas
 */
void updateClientSell(Gestao g, Venda v);

/**
 * Devolve uma copia dos clientes que existem na gestao
 *
 * g : estrutura gestao
 *
 * return : arvore com os codigos de clientes
 */
GTree* getGestaoClients(Gestao g);

/**
 * Carrega a estrutura TotalBought com o numero total de produtos comprados por um cliente
 *
 * g : estrutura gestao
 * i : filial
 * tb : estrutura a preencher
 * clientID : codigo do cliente
 */
void productsBoughtByClient(TotalBought tb,int i,Gestao g,char* productID);

/**
 * Prrenche a collection com os comprados de um determinado produto
 *
 * g : estrutura a gestao
 * c : colecao a preencher
 * productID : codigo do produto
 */
void productBuyers(Collection c, Gestao g, char* productID);

/**
 * Preenche a arvore p com a correspondencia cliente->produtos favoritos
 *
 * g : estrutura a gestao
 * p : arvore a preencher
 * clientID : codigo do cliente
 * mes : mes
 */
void clientFavoriteProduct(GTree* p, Gestao g, char* clientID, int month);

/**
 * Preenche a arvore p com a correspondencia cliente->produtos comprados
 *
 * g : estrutura a gestao
 * p : arvore a preencher
 * clientID : codigo do cliente
 */
void clientTopProfitProducts(GTree* p, Gestao g, char* clientID);

/**
 * Funcao de comparacao da estrutura ProductAmounts
 *
 * p1 : estrutura productAmount
 * p2 : estrutura productAmount
 *
 * return : relacao de comparacao
 */
int compMostSell(ProductAmount p1, ProductAmount p2);

/**
 * Prrenche a arvore p com os valores totais de vendas
 *
 * g : estrutura a gestao
 * p : arvore a preencher
 * filial : atual filial
 */
void mostSellProducts(GTree* p, Gestao g, int filial);

#endif
