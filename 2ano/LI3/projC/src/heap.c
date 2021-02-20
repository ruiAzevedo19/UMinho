#include "../headers/heap.h"
#include "../headers/gestao.h"
#include <stdlib.h>
#include <string.h>

#define PARENT(i) (i-1)/2
#define LEFT(i) 2*i + 1
#define RIGHT(i) 2*i + 2

struct heap{
    int size;
    int index;
    ProductAmount* content;
    int (*compare_func)(ProductAmount p1, ProductAmount p2);
};

Heap initHeap(int size, void* compare_func){
    Heap h = malloc(sizeof(struct heap));
    h->size = size;
    h->index = 0;
    h->content = malloc(sizeofProductAmount() * size);
    h->compare_func = compare_func;

    return h;
}

void destroyHeap(Heap h){
    int i;

    for(i = 0; i < h->index; i++)
        destroyProductAmount(h->content[i]); 
    free(h);
}

int getHeapIndex(Heap h){
    return h->index;
}

static void swap(Heap h, int i, int j){
    ProductAmount pa = h->content[i];
    h->content[i] = h->content[j];
    h->content[j] = pa;
}

static void bubbleUp(Heap h, int i){
    while(i > 0 && h->compare_func(h->content[i],h->content[PARENT(i)]) == 1){
        swap(h,i, PARENT(i)); 
        i = PARENT(i);
    }
}

static void bubbleDown(Heap h, int i, int n){
    int max;
    if( LEFT(i) >= n )
        return ;
    max = (h->compare_func(h->content[LEFT(i)],h->content[i]) == 1) ? LEFT(i) : i ;
    if( RIGHT(i) < n && h->compare_func(h->content[2*i+2],h->content[max]) == 1)
        max = RIGHT(i);
    if( max != i ){
        swap(h,i,max);
        bubbleDown(h,max,n);
    }
}

void addHeapElem(Heap h, ProductAmount pa){
    int i;
    char* product = getProductAmountProduct(pa);
    double billing = getProductAmountBilling(pa);
    int amount = getProductAmountAmount(pa);
    ProductAmount p = initProductAmount(product,billing,amount);
    setPATotalSales(p,getPATotalSales(pa));
    setPATotalClients(p,getPATotalClients(pa));
    for(i = 0; i < 3; i++){
        setPASalesAt(p,i,getPASalesAt(pa, i));
        setPAClientsAt(p,i,getPAClientsAt(pa, i));
    }
    free(product);
    if( h->index == h->size ){
        h->content = realloc(h->content, 2 * h->size * sizeofProductAmount());
        h->size = 2 * h->size;
    }
    h->content[h->index] = p;
    bubbleUp(h, h->index++);
}

ProductAmount extractMax(Heap h){
    int i,n = h->index;
    ProductAmount pa = NULL;
    char* product;
    int amount;
    double billing;
    if( h->index > 0){
        product = getProductAmountProduct(h->content[0]);
        amount = getProductAmountAmount(h->content[0]);
        billing = getProductAmountBilling(h->content[0]);
        pa = initProductAmount(product,billing,amount);
        setPATotalSales(pa,getPATotalSales(h->content[0]));
        setPATotalClients(pa,getPATotalClients(h->content[0]));
        for(i = 0; i < 3; i++){
            setPASalesAt(pa,i,getPASalesAt(h->content[0], i));
            setPAClientsAt(pa,i,getPAClientsAt(h->content[0], i));
        }
        free(product);
        h->content[0] = h->content[h->index - 1];
        h->index--;
        bubbleDown(h,0, n);
    }
    return pa;
}

