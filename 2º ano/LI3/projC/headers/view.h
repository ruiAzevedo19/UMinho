#ifndef _VIEW_H_
#define _VIEW_H_

#include "../headers/page.h"

/**
 * Imprime uma mensagem de erro
 *
 * message : mensagem de erro
 */
void errorMessage(char* message);

/**
 * Imprime o menu principal
 */
void mainMenuView();

/**
 * Imprime os dados de parsing
 *
 * pv : produtos validos
 * pi : produtos invalidos
 * cv : clientes validos
 * ci : clientes invalidos
 * sv : vendas validas
 * si : vendas invalidas
 * cl : tempo de carregamento do ficheiro de clientes
 * pl : tempo de carregamento do ficheiro de produtos
 * sl : tempo de carregamento do ficheiro de vendas
 */
void printLoadTime(int pv, int pi, int cv, int ci, double pl, double cl, double sl, int sv, int si);

/**
 * Imprime o cabecalho de uma query
 *
 * header : cabecalho da query
 */
void printQueryHeader(char* header);

/**
 * Imprime um ecra simples
 *
 * info : cabecalhos
 * value : valores correspondentes aos cabecalhos
 * n : numero de values
 */
void singleView(char** info, int value[], int n);

/**
 * Imprime uma tabela com um cabecalho
 *
 * header : cabecalho
 * p : pagina com os elementos a imprimir
 */
void oneHeaderView(char* header, Page p);

/**
 * Imprime uma tabela com dois cabecalhos
 *
 * header1 : primeiro cabecalho
 * header2 : segundo cabecalho
 * p : pagina com os elementos a imprimir
 */
void twoHeaderView(char* header1, char* header2, Page p);

/**
 * Imprime uma tabela com valores
 *
 * table : tabela a imprimir
 * numero de elementos
 */
void threeHeaderView(int* table,int n);

/**
 * Imprime uma tabela com nove cabecalhos
 *
 * p : pagina com os elementos a imprimir
 */
void nineHeaderView(Page p);

/**
 * Imprime os dados da query 3
 *
 * modo : modo de pagamento
 * p : pagina com os elementos a imprimir
 */
void querie3View(char modo, Page p);

/**
 * Imprime os dados da query 4
 *
 * mesIni : mes inicial
 * mesFin : mes final
 * sales : numero de vendas
 * billings : total faturado
 */
void querie4View(int mesIni, int mesFin, int sales, double billings);

/**
 * Imprime um pedido
 *
 * request : pedido a imprimir
 */
void printRequest(char* request);

/**
 * Limpa o ecra
 */
void clearScreen();

#endif
