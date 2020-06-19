#include "../headers/page.h"
#include "../headers/collection.h"
#include <stdlib.h>

struct page{
    int pageElems;
    int nrPages;
    int currentPage;
    int readOffset;
    Collection collection;
};

Page initPage(Collection c, int pageElems, int readOffset){
    Page p = malloc(sizeof(struct page));
    p->pageElems = pageElems;
    p->currentPage = 1;
    p->collection = c;
    p->readOffset = readOffset;
    int elems = getMaxHeaderIndex(c);
    p->nrPages = ( !(elems % pageElems) ) ? elems / pageElems : (elems / pageElems) + 1;

    return p;
}

int setCurrentPage(Page p, int page){
    int r = 0;
    if( page > 0 && page <= p->nrPages ){
        p->currentPage = page;
        r = 1;
    }
    return r;
}

int getCurrentPage(Page p){
    return p->currentPage;
}

int getNrPages(Page p){
    return p->nrPages;
}

int getPageNrElems(Page p){
    return p->pageElems;
}

char** getSinglePage(Page p, int index){
    char** page = malloc(sizeof(char*) * p->pageElems * getHeaders(p->collection) + 1);
    int i,j,k = 0, max = getMaxHeaderIndex(p->collection);

    for(i = (index - 1) * p->pageElems; i < max && i < index * p->pageElems; i++)
        for(j = 0; j < getHeaders(p->collection); j++)
            page[k++] = getHeaderElemIndex(p->collection,j,i);
    page[k] = 0;

    return page;
}

void destroyPage(Page p){
    destroyCollection(p->collection);
    free(p);
}
