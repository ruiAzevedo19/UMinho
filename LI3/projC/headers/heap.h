#ifndef _HEAP_H_
#define _HEAP_H_

#include "../headers/gestao.h"

typedef struct heap *Heap;

/**
 * Inicia a estrutura heap
 *
 * size : tamanho inicial
 * compare_func : funcao de comparacao
 *
 * return : estrutura heap
 */
Heap initHeap(int size, void* compare_func);

/**
 * Destroi a estrutura heap
 *
 * h : heap a destruir
 */
void destroyHeap(Heap h);

/**
 * Indice do ultimo elemento inserido na heap, corresponde ao numero de elementos da heap
 *
 * h : estrutura heap
 *
 * return : numero de elementos da heap
 */
int getHeapIndex(Heap p);

/**
 * Adiciona um elemento a heap
 *
 * h : estrutura heap
 * pa : estrutura a adicionar
 */
void addHeapElem(Heap h, ProductAmount pa);

/**
 * Extrai o elemento que está na cabeça da heap
 *
 * h : estrutura heap
 *
 * return : estrutura que está à cabeça
 */
ProductAmount extractMax(Heap p);

#endif
