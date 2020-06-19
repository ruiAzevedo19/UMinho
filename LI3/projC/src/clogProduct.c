#include "../headers/clogProduct.h"
#include "../headers/catalog.h"
#include "../headers/collection.h"
#include <stdlib.h>

struct clogProduct{
    Catalog products;
};

ClogProduct initClogProduct(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun){
    ClogProduct p = malloc(sizeof(struct clogProduct));
    p->products = initCatalog(depth,range,compare_fun,free_key_fun,free_value_fun);
    return p;
}

void destroyClogProduct(ClogProduct c){
    destroyCatalog(c->products);
    free(c);
}

void addProduct(ClogProduct c, void* key){
    addKeyValue(c->products,key,NULL);
}

int containsProduct(ClogProduct c, void* key){
    return containsKey(c->products,key);
}

void traverseClogProducts(ClogProduct p, void* traverse_func, void* data){
    traverseCatalog(p->products,traverse_func,data);
}

Collection productByLetter(ClogProduct p, char letter){
    return elemByLetter(p->products,letter);
}
