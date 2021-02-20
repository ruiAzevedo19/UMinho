#include "../headers/collection.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

struct collection{
    int headers;
    int* size;
    int* index;
    char*** content;
};

Collection initCollection(int headers, int size){
    int i,j;
    char** col;

    Collection c = malloc(sizeof(struct collection));
     
    c->headers = headers;
    c->size  = malloc(sizeof(int) * headers);
    for(i = 0; i < headers; c->size[i++] = size);

    c->index = malloc(sizeof(int) * headers);
    for(i = 0; i < headers; c->index[i++] = 0);
    
    c->content = malloc(sizeof(char**) * headers);
    for(i = 0; i < headers; i++)
        c->content[i] = malloc(sizeof(char*) * size);
    for(i = 0; i < headers; i++){
        col = c->content[i];
        for(j = 0; j < size; j++)
            col[j] = NULL;
    }

    return c;
}

void destroyCollection(Collection c){
    int i, j;
    char** col;

    for(i = 0; i < c->headers; i++){
        col = c->content[i];
        for(j = 0; j < c->index[i]; j++)
            free(col[j]);
        free(col);
    }
    free(c->content);
    free(c->size);
    free(c->index);
}

void addElem(Collection c, int header, char* elem){
    int i;
    char** h = c->content[header];
    char** n;
    if( c->index[header] == c->size[header] ){
        c->size[header] *= 2;
        n = malloc(sizeof(char*) * c->size[header]);
        for(i = 0; i < c->index[header]; i++)
            n[i] = h[i];
        c->content[header] = n;
    }
    h = c->content[header];
    h[c->index[header]++] = strdup(elem);
}

int getMaxHeaderIndex(Collection c){
    int i, max = -1;
    if( c->headers > 0 ){
        max = c->index[0];

        for(i = 1; i < c->headers; i++)
            if( c->index[i] > max )
                max = c->index[i];
    }
    return max;
}

int* getAllHeaderIndex(Collection c){
    int* index = malloc(sizeof(int) * c->headers); 
    int i;

    for(i = 0; i < c->headers; i++)
        index[i] = c->index[i];

    return index;
}

int getHeaders(Collection c){
    return c->headers;
}

int getHeaderIndex(Collection c, int header){
    return c->index[header];
}

char** getHeader(Collection c, int header){
    char** h1 = malloc(sizeof(char*) * c->index[header]);
    char** h2 = c->content[header];
    int i;

    for(i = 0; i < c->index[header]; i++)
        h1[i] = strdup(h2[i]);

    return h1;
}

char* getHeaderElemIndex(Collection c, int header, int i){
    char** h;
    char* elem = NULL;
    if( header >= 0 && header < c->headers ){
        h = c->content[header];
        if( i >= 0 && i <= c->index[header] && h[i] )
            elem = strdup(h[i]);
        else 
            elem = strdup(" ");
    }
    return elem;
}

void printHeader(Collection c, int header){
    int i;
    char** h = c->content[header];

    for(i = 0; i < c->index[header]; i++)
        printf("Elem = %s\n", h[i]);
}

