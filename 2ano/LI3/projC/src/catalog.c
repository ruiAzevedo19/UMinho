#include "../headers/catalog.h"
#include "../headers/collection.h"
#include "../headers/faturacao.h"

#include <glib.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <ctype.h>

struct catalog{
    GTree** clog;
    int size;
    int depth;
    int range;
    int elems;
};

static int hash(int depth, int range, char* code){
    int i, h = 0;
    
    for(i = 0; i < depth - 1; i++)
        h += range * (code[i] - 'A');
    h += code[i] - 'A';

    return h;
}

Catalog initCatalog(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun){
    int i;
    Catalog c = malloc(sizeof(struct catalog));

    c->size  =  (int)(pow(range, depth)+0.5);
    c->depth = depth;
    c->range = range;
    c->elems = 0;

    c->clog = malloc(sizeof(GTree*) * c->size);
    for(i = 0; i < c->size; i++)
        c->clog[i] = g_tree_new_full(compare_fun,NULL,free_key_fun,free_value_fun);

    return c;
}

int validate(int depth, int ri, int rs, char* line){
    int i, n, r = 1;

    for(i = 0; r && line[i] && i < depth; i++)
        if( !isalpha(line[i]) || !isupper(line[i]) )
            r = 0;
     r = (i < depth) ? 0 : 1;

     if( r ){
        for(i = depth; line[i]; i++)
            if( !isdigit(line[i]) )
                r = 0;
    }

    if( r ){
       n = atoi(line + depth);
       r = (n >= ri && n <= rs) ? 1 : 0;
    }
    return r;
}


int getSize(Catalog c){
    return c->size;
}

int getDepth(Catalog c){
    return c->depth;
}

int getRange(Catalog c){
    return c->range;
}

int getElems(Catalog c){
    return c->elems;
}

void* getValue(Catalog c, void* key){
    int h = hash(c->depth, c->range, key);
    return g_tree_lookup(c->clog[h],key);
}

void addKeyValue(Catalog c, void* key, void* value){
    int h = hash(c->depth, c->range, key);
    char* nr = strdup(key);
    g_tree_insert(c->clog[h],nr,value);
    c->elems++;
}

int containsKey(Catalog c, void* key){
    int h = hash(c->depth, c->range, key);
    gboolean exists = g_tree_lookup_extended(c->clog[h], key,NULL,NULL);
    return exists; 
}

void destroyCatalog(Catalog c){
    int i;
    for(i = 0; i < c->size; i++)
        g_tree_destroy(c->clog[i]);
    free(c->clog);
    free(c);        
}

void traverseCatalog(Catalog c, void* traverse_func, void* data){
    int i, n = (int)(pow(c->range, c->depth)+0.5);

    for(i = 0; i < n; i++)
        if( c->clog[i] )
            g_tree_foreach(c->clog[i], (GTraverseFunc)traverse_func,data);
}

int insert(char*key, __attribute__((unused))void* value, GTree* g){
    g_tree_insert(g,strdup(key),value);
    return 0;
}

GTree* cloneClog(Catalog c){
    int i, n = (int)(pow(c->range, c->depth)+0.5);
    GTree* g = g_tree_new_full((GCompareDataFunc)strcmp,NULL,(GDestroyNotify)free,NULL);

    for(i = 0; i < n; i++)
        if( c->clog[i] )
            g_tree_foreach(c->clog[i], (GTraverseFunc)insert,g);

    return g;
}

/* --- Funcionalidade ------------------------------------------------------ */

typedef struct col{
    Collection c;
    int header;
}*Col;

static int getCode(gpointer key, __attribute__((unused))gpointer value, Col col){
    addElem(col->c, col->header, key);
    return 0;
}

Collection elemByLetter(Catalog c, char letter){
    Collection collection = initCollection(1,4000);

    Col col = malloc(sizeof(struct col));
    col->c = collection;
    col->header = 0;

    int i, hash = c->range * ( letter - 'A' );

    for(i = 0; i < c->range; i++)
        if( c->clog[hash + i] )
            g_tree_foreach(c->clog[hash + i], (GTraverseFunc)getCode,col);
    
    collection = col->c;
    free(col);

    return collection;
}

/* --- Para a Querie 4 ------------------------------------------------------ */

Collection* prodNotBuyed(Catalog c, int filiais){
    Collection* collection = malloc(sizeof(Collection) * (filiais + 1));
    int i;

    for(i = 0; i < filiais + 1; i++) collection[i] = initCollection(1,500);
    for(i = 0; i < getSize(c); i++)
        if( c->clog[i] )
            g_tree_foreach(c->clog[i], (GTraverseFunc)addProdNotBuyedFiliais,collection);

    return collection;
}
