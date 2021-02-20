#include "../headers/parser.h"
#include "../headers/interface.h"
#include "../headers/catalog.h"
#include "../headers/clogProduct.h"
#include "../headers/clogClient.h"
#include "../headers/estruturasAux.h"

#include <time.h>
#include <ctype.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


int vendaValidate(SGV sgv, char* line, Venda v){
    int i=0;
    char * campoInserir = strtok(line," \r\n");
    while(campoInserir != NULL) {
        switch(i){
            case 0:
                v->produto = campoInserir;
                if((validate(PRODUCTS_DEPTH, 1000, 9999,v->produto) == 0) || (containProductSGV(sgv,v->produto) == 0)) { return 0; }
                break;
            case 1: 
                v->priceUnit = atof(campoInserir);
                if(v->priceUnit < 0.0f || v->priceUnit >= 1000.0f) {return 0;}
                break;
            case 2:
                v->unitCompra = atoi(campoInserir);
                if(v->unitCompra < 1 || v->unitCompra > 200) {return 0;}
                break;
            case 3:
                v->modo = campoInserir[0];
                if((strcmp(campoInserir,"N") == 0) || (strcmp(campoInserir,"P")) == 0){}
                else return 0;
                break;
            case 4:
                v->cliente = campoInserir;
                if((validate(CLIENTS_DEPTH, 1000, 5000,v->cliente) == 0) || containClientSGV(sgv, v->cliente) == 0) {
                    return 0;
                }
                break;
            case 5:
                v->mes = atoi(campoInserir);
                if(v->mes < 1 || v->mes > 12 ) {return 0;}
                break;
            case 6:
                v->filial = atoi(campoInserir);
                if(v->filial < 1 || v->filial > 3) {return 0;}
                break;
            case 7:
                return 0;
        }
        i++;
        campoInserir = strtok(NULL, " \r\n");
    }
    return 1;
}

static void loadSells(SGV sgv, char* path){
    FILE* p = fopen(path, "r");
    char line[100];
    int invalid = 0, valid = 0;
    Venda v = malloc(sizeof(struct venda));
    clock_t start, end;
    start = clock();
    while( (fgets(line,100,p)) ){
        if(vendaValidate(sgv,line,v)){
            addSell(sgv,v);
            valid++;
        }
        else
            invalid++;
    }
    end = clock();

    setValid(sgv,'V',valid);
    setInvalid(sgv,'V',invalid);
    setLoad(sgv,'V',(double)(end - start)/CLOCKS_PER_SEC);
    free(v);
    fclose(p);
    
}

static void loadPC(SGV sgv, char catalog, int depth, int ri, int rs, char* path){
    FILE* p = fopen(path, "r");
    char line[10];
    int invalid = 0, valid = 0;
    char* prod;
    clock_t start, end;
    
    start = clock();
    while( (fgets(line,10,p)) ){
        prod = strdup(strtok(line," \n\r"));
        if( validate(depth, ri, rs, prod) ){
            addPC(sgv,catalog,line);
            valid++;
        }
        else 
            invalid++;
        free(prod);
    }
    end = clock();
    
    setValid(sgv,catalog,valid);
    setInvalid(sgv, catalog, invalid);
    setLoad(sgv,catalog,(double)(end - start) / CLOCKS_PER_SEC);

    fclose(p);
}

void loadFiles(SGV sgv, char* clientsFilePath, char* productsFilePath, char* salesFilePath){
    loadPC(sgv, 'P', PRODUCTS_DEPTH, 1000, 9999, productsFilePath);
    loadPC(sgv, 'C', CLIENTS_DEPTH, 1000, 5000, clientsFilePath);
    loadSells(sgv, salesFilePath);
}

