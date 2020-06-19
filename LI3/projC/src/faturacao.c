#include "../headers/faturacao.h"
#include "../headers/catalog.h"
#include "../headers/estruturasAux.h"
#include "../headers/collection.h"
#include "../headers/heap.h"

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>

struct faturacao{
    int totalSells[12];
    double totalBillings[12];
    Catalog products;
    int filiais;
    int modos;
};

struct productInfo{
    /* 0..35 -> N || 36..71 -> P */
    int filiais;
    int* buyed;
    int* units;
    int* sells;
    double* billings;
};

struct totalSalesBillings{
    int sales;
    double billings;
};

TotalSalesBillings initTotalSalesBillings(int sales, double billings){
    TotalSalesBillings tsb = malloc(sizeof(struct totalSalesBillings));
    tsb->sales = sales;
    tsb->billings = billings;

    return tsb;
}

void destroyTotalSalesBillings(TotalSalesBillings tsb){
    free(tsb);
}

int getTSBSales(TotalSalesBillings tsb){
    return tsb->sales;
}

double getTSBBillings(TotalSalesBillings tsb){
    return tsb->billings;
}

static void destroyProductInfo(ProductInfo pi){
    free(pi->buyed);
    free(pi->billings);
    free(pi->sells);
    free(pi);
}

Faturacao initFaturacao(int filiais, int modos, int depth, int range, void* free_key_fun){
    int i;
    Faturacao f = malloc(sizeof(struct faturacao));
    f->filiais = filiais;
    f->modos = modos;
    for(i = 0; i < 12; i++) {
        f->totalSells[i] = 0;
        f->totalBillings[i] = 0;
    }
    f->products = initCatalog(depth,range,(GCompareFunc)strcmp,free_key_fun,destroyProductInfo);
    return f;
}

static ProductInfo initProductInfo(int filiais, int modos){
    int i,n;
    ProductInfo pi = malloc(sizeof(struct productInfo));
    pi->filiais = filiais;
    n = filiais * modos * 12;
    pi->buyed = malloc(sizeof(int) * filiais);
    for(i = 0; i < filiais; pi->buyed[i++] = 0);
    pi->units = malloc(sizeof(double) * n);
    pi->billings = malloc(sizeof(double) * n);
    pi->sells = malloc(sizeof(int) * n);
    for(i = 0; i < filiais * 12 * modos; i++){
        pi->units[i] = 0;
        pi->billings[i] = 0;
        pi->sells[i] = 0;
    }
    return pi;
}

void destroyFaturacao(Faturacao f){
    destroyCatalog(f->products);
    free(f);
}

void addProductSell(Faturacao f, char* key){
    ProductInfo pi = initProductInfo(f->filiais,f->modos);
    addKeyValue(f->products,key,pi);
}

void updateSell(Faturacao f, Venda v){
    double faturacao = v->priceUnit * v->unitCompra;
    int dx = 0;
    if( v->modo == 'P' )
        dx = f->filiais * 12;
    dx += (v->filial - 1) * 12 + (v->mes - 1);

    f->totalSells[v->mes - 1] += 1;
    f->totalBillings[v->mes - 1] += faturacao;
    ProductInfo pi = getValue(f->products,v->produto);
    pi->buyed[v->filial - 1] = 1;
    pi->units[dx] += v->unitCompra;
    pi->sells[dx] += 1;
    pi->billings[dx] += faturacao;
}

int countBuy(char* key, ProductInfo pi, int* v){
    int i,n = 0;
    for(i = 0; i < pi->filiais; i++)
        n += pi->buyed[i];
    if( n == 0 )
        (*v)++;

    return 0;
}

int productsNeverBoughtNumber(Faturacao f){
    int v = 0;
    traverseCatalog(f->products,countBuy,&v);
    return v;
}

int positionInfo(Faturacao f, char modo, int mes, int filial){
    int dx = 0;
    if( modo == 'P' )
        dx = f->filiais * 12;
    dx += (filial - 1) * 12 + (mes - 1);

    return dx;
}

Collection vendfatMesProd(Faturacao f, int mes, char* prod,char globORfil){
    int i,fil = 0;
    if(globORfil == 'F') fil = f->filiais;
    else if(globORfil == 'G') fil = 1;
    Collection collection = initCollection(4,fil);
    ProductInfo pInfo = getValue(f->products, prod);

    int sellTotN = 0;int sellTotP = 0;
    double billingTotN = 0; double billingTotP = 0; char info[30];
    switch(globORfil){
        case 'F':
            for(i = 1; i <= f->filiais; i++){
                sprintf(info, "%d", pInfo->sells[positionInfo(f,'N',mes,i)]);  addElem(collection, 0,info);
                sprintf(info, "%f", pInfo->billings[positionInfo(f,'N',mes,i)]);  addElem(collection, 1,info);
                sprintf(info, "%d", pInfo->sells[positionInfo(f,'P',mes,i)]);  addElem(collection, 2,info);
                sprintf(info, "%f", pInfo->billings[positionInfo(f,'P',mes,i)]);  addElem(collection, 3,info);
            }
            break;
        case 'G':
            for(i = 1; i <= f->filiais; i++){
                sellTotN += pInfo->sells[positionInfo(f,'N',mes,i)];
                billingTotN += pInfo->billings[positionInfo(f,'N',mes,i)];
                sellTotP += pInfo->sells[positionInfo(f,'P',mes,i)];
                billingTotP += pInfo->billings[positionInfo(f,'P',mes,i)];
            }
            sprintf(info, "%d", sellTotN); addElem(collection, 0, info);
            sprintf(info, "%f", billingTotN); addElem(collection, 1, info);
            sprintf(info, "%d", sellTotP); addElem(collection, 2, info);
            sprintf(info, "%f", billingTotP); addElem(collection, 3, info);
            break;
        default:
            break;
    }
    return collection;
}

int addProdNotBuyedFiliais(char* key, ProductInfo pi, Collection* col){
    int i;
    int notBuyedGLOB = 1;
    for(i = 0; i < pi->filiais; i++){
        if(!pi->buyed[i]) addElem(col[i], 0, key);
        else notBuyedGLOB = 0;
    }
    if(notBuyedGLOB) addElem(col[pi->filiais], 0, key);
    return 0;
}

Collection* notBuyed(Faturacao f){
    return prodNotBuyed(f->products, f->filiais);
}

TotalSalesBillings infoIntervMeses(Faturacao f, int mesIni, int mesFin){
    int i, sellsTot = 0; double billingsTot = 0;

    for(i = mesIni; i <= mesFin; i++){
        sellsTot += f->totalSells[i];
        billingsTot += f->totalBillings[i];
    }
    TotalSalesBillings tsb = initTotalSalesBillings(sellsTot, billingsTot);

    return tsb;

}



