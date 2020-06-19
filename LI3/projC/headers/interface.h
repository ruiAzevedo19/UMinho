#ifndef _INTERFACE_H_
#define _INTERFACE_H_

#define RANGE 26
#define PRODUCTS_DEPTH 2
#define CLIENTS_DEPTH 1
#define FILIAIS 3

#include "../headers/collection.h"
#include "../headers/clogProduct.h"
#include "../headers/clogClient.h"
#include "../headers/estruturasAux.h"
#include "../headers/gestao.h"
#include "../headers/faturacao.h"

#include <time.h>

/**
 * Estrutura que guarda toda a informacao do sistema de gestao de vendas
 */
typedef struct sgv *SGV;

/**
 * Inicializa a estrutura geral do sistema
 *
 * return : estrutura geral do sistema inicializada
 */
SGV initSGV();

/**
 * Adiciona um cliente ou um produto ao sistema
 *
 * sgv : estrutura geral do sistema
 * type : cliente ou produto
 * code : codigo do cliente ou produto
 */
void addPC(SGV sgv, char type, char* code);

/**
 * Adiciona a info de uma venda na modulo de faturação e filial
 *
 * sgv : estrutura geral do sistema
 * code : linha de venda
 */
void addSell(SGV sgv, Venda v);

/**
 * Destroi a estrutura geral do sistema libertando toda a sua memoria
 *
 * sgv : estrutura geral do sistema
 */
void destroySGV(SGV sgv);

/**
 * Verifica se as estruturas estao carregadas
 *
 * sgv : estrutura geral do sistema
 *
 * return : 1 se as estruturas estao carregadas
 */
int getSGVLoaded(SGV sgv);

/**
 * Define se as estruturas estao carregadas
 *
 * sgv : estrutura geral do sistema
 * load : flag
 */
void setSGVLoaded(SGV sgv, int load);

/**
 * Verifica se um produto existe no catalogo dos produtos
 *
 * sgv : estrutura geral do sistema
 * prod : produto a procurar

 * return : 1 se o produto existe
 */
int containProductSGV(SGV sgv, char* prod);

/**
 * Verifica se um cliente existe no catalogo dos produtos
 *
 * sgv : estrutura geral do sistema
 * cli : cliente a procurar

 * return : 1 se o cliente existe
 */
int containClientSGV(SGV sgv, char* cli);

/**
 * Carrega a estrutura sgv com os dados contidos nos ficheiros
 *
 * sgv : estrutura geral do sistema
 * fildesFolderPath : caminho da pasta dos ficheiros
 */
SGV loadSGVFromFiles(SGV sgv, char* clientsFilePath, char* productsFilePath, char* salesFilePath);

/**
 * Define os tempos de carregamento de produtos e clientes
 *
 * sgv : estrutura geral do sistema
 * catalog : tipo de catalogo
 * start : tempo de inicio
 * end : tempo de fim
 */
void setLoad(SGV sgv, char catalog, double time);

/**
 * Define o numero de elementos validos de um catalogo
 *
 * sgv : estrutura geral do sistema
 * catalog : catalogo
 * valid : numero de elementos validos
 */
void setValid(SGV sgv, char catalog, int valid);

/**
 * Define o numero de elementos invalidos de um catalogo
 *
 * sgv : estrutura geral do sistema
 * catalog : catalogo
 * invalid : numero de elementos invalidos
 */
void setInvalid(SGV sgv, char catalog, int invalid);

/**
 * Devolve o tempo de execucao de um determinado tipo
 *
 * sgv : estrutura geral do sistema
 * type : tipo de dados
 *
 * return : tempo de execucao de um determinado tipo
 */
double getStatsLoadTime(SGV sgv, int type);

/**
 * Devolve o numero de elemento validos de um determinado tipo
 *
 * sgv : estrutura geral do sistema
 * type : tipo de dados
 */
int getStatsValidation(SGV sgv, int type);

/**
 * Carrega os dados globais de um ficheiro
 *
 * sgv : estrutura geral do sistema
 *
 * return : estrutura geral do sistema
 */

SGV loadSGVConfiguration(SGV sgv);

/**
 * Devolve o caminho para o ficheiro de produtos
 *
 * sgv : estrutura geral do sistema
 *
 * return : caminho para o ficheiro de produtos
 */
char* getConfigurationProductsPath(SGV sgv);

/**
 * Devolve o caminho para o ficheiro de clientes
 *
 * sgv : estrutura geral do sistema
 *
 * return : caminho para o ficheiro de clientes
 */
char* getConfigurationClientsPath(SGV sgv);

/**
 * Devolve o caminho para o ficheiro de vendas
 *
 * sgv : estrutura geral do sistema
 *
 * return : caminho para o ficheiro de vendas
 */
char* getConfigurationSalesPath(SGV sgv);

/**
 * Devolve o numero de letras de um codigo de produto
 *
 * sgv : estrutura geral do sistema
 *
 * return : numero de letras diferentes de um codigo de produto
 */
int getConfigurationProductsDepth(SGV sgv);

/**
 * Devolve o numero de letras de um codigo de clientes
 *
 * sgv : estrutura geral do sistema
 *
 * return : numero de letras diferentes de um codigo de clientes
 */
int getConfigurationClientsDepth(SGV sgv);

/**
 * Devolve os produtos que comecam por uma determinada letra
 *
 * sgv : estrutura geral do sistema
 * letter : letra a procurar
 *
 * return : conjunto dos produtos que comecam pela letra (letter)
 */
Collection getProductsStartedByLetter(SGV sgv, char letter);

/**
 * Devolve o numero de clientes que compraram um produto numa filial
 *
 * sgv : estrutura geral do sistema
 * c : colecao
 * filial : filial a procurar
 * codigo do produto
 *
 * return : numero de clientes que compraram um produto numa filial 
 */
void productBuyersToCount(Collection c, SGV sgv, int filial, char* product);

/**
 * Devolve o numero total de vendas e total faturado por um determinado produto num determinado mes
 *
 * sgv : estrutura geral do sistema
 * mes : mes a procurar
 * prod : codigo do produto
 * globORfil : modo global ou filial a filial
 *
 * return : numero de produtos nunca comprados e o numero de clientes que nunca compraram
 */
Collection getVendfatMesProd(SGV sgv, int mes, char* prod, char globORfil);

/**
 * Devolve o numero de produtos nunca comprados
 *
 * sgv : estrutura geral do sistema
 *
 * return : numero de produtos nunca comprados
 */
Collection* getProdNotBuyed(SGV sgv);

/**
 * Devolve o conjunto de clientes que compraram em todas as filiais
 *
 * sgv : estrutura geral do sistema
 *
 * return : conjunto de clientes que compraram em todas as filias
 */
Collection getClientsOfAllBranches(SGV sgv);

/**
 * Devolve o numero de produtos nunca comprados e o numero de clientes que nunca compraram
 *
 * sgv : estrutura geral do sistema
 *
 * return : numero de produtos nunca comprados e o numero de clientes que nunca compraram
 */
CPNeverBought getClientsAndProductsNeverBoughtCount(SGV sgv);

/**
 * Devolve o numero total de produtos comprados por um cliente mes a mes, filial a filial
 *
 * sgv : estrutura geral do sistema
 * cliente : codigo do cliente
 *
 * return : numero total de produtos comprados por um cliente mes a mes, filial a filial
 */
TotalBought getProductsBoughtByClient(SGV sgv, char* client);

/**
 * Devolve as vendas e faturacao totais dum intervalo de meses
 *
 * sgv : estrutura geral do sistema
 * minMonth : mes inicial
 * maxMonth : mes final
 *
 * return : vendas e faturacao totais
 */
TotalSalesBillings getSalesAndProfit(SGV sgv, int minMonth, int maxMonth);

/**
 * Devolve os clientes que compraram um determinado produto
 *
 * sgv : estrutura geral do sistema
 * productID : codigo do produto
 * branch : filial a procurar
 *
 * return : conjunto com os clientes que compraram um determinado produto
 */
Collection getProductBuyers(SGV, char* productID, int branch);

/**
 * Devolve os produtos mais vendidos
 *
 * sgv : estrutura geral do sistema
 * limit : limite da procura
 *
 * return : conjunto com os produtos mais vendidos
 */
Collection getTopSelledProducts(SGV sgv, int limit);

/**
 * Devolve os produtos mais comprados por um cliente
 *
 * sgv : estrutura geral do sistema
 * clienteID : codigo do cliente
 * month : mes de procura
 *
 * return : conjunto com os produtos mais comprados por um cliente
 */
Collection getClientFavoriteProduct(SGV sgv, char* clientID, int month);

/**
 * Devolve os produtos que um cliente mais gastou dinheiro
 *
 * sgv : estrutura geral do sistema
 * clienteID : codigo do cliente
 * limit : limite da procura
 *
 * return : conjunto com os produtos em que um cliente mais gastou dinheiro
 */
Collection getClientTopProfitProducts(SGV sgv, char* clientID, int limit);

#endif
