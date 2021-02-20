#ifndef _STATS_H_
#define _STATS_H_

#include <time.h>

typedef struct stats *Stats;

/**
 * Inicia a estrutura de estatisticas 
 */
Stats initStats();

/**
 * Destroi a estrutura de estatisticas
 *
 * stats : estrutura a destruir
 */
void destroyStats(Stats stats);

/**
 * Define o tempo de execucao de um determinado tipo 
 *
 * stats : estrutura de estatisticas 
 * type : tipo de dados 
 * load : tempo de execucao 
 */
void setLoadTime(Stats stats, int type, double load);

/** 
 * Devolve o tempo de execucao de um determinado tipo 
 *
 * stats : estrutura de estatisticas 
 * type : tipo de dados 
 * 
 * return : tempo de execucao de um determinado tipo
 */
double getLoadTime(Stats stats, int type);

/**
 * Define o numero de elementos validos de um determiando tipo 
 *
 * stats : estrutura de estatisticas 
 * type : tipo de dados 
 * validation : numero de elementos validos
 */
void setValidation(Stats stats, int type, int validation);

/**
 * Devolve o numero de elemento validos de um determinado tipo 
 *
 * stats : estrutura de estatisticas 
 * type : tipo de dados 
 */
int getValidation(Stats stats, int type);

/**
 * Define o tempo de execucao de uma query
 *
 * stats : estrutura de estatisticas
 * query : query que se pretende definir o tempo
 * start : tempo de inicio de carregamento
 * end : tempo de fim de carregamento
 */
void setQTime(Stats stats, int query, clock_t start, clock_t end);

/**
 * Devolve o tempo de execucao de uma determinada query
 *
 * stats : estrutura de estatisticas
 * query : query pretendida
 * 
 * return: tempo de execucao da query
 */
double getQTime(Stats stats, int query);

/**
 * Escreve o tempo de execucao de uma determinada query no ficheiro stats.txt
 *
 * stats : estrutura de estatisticas
 * query : query pretendida
 */
void writeQTime(Stats stats, int query);

/**
 * Escreve o tempo de carregamento da estrutura geral no ficheiro stats.txt
 *
 * stats : estrutura de estatisticas
 */
void writeLoad(Stats stats);

#endif


