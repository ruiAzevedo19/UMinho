#include "../headers/catalog.h"
#include "../headers/clogClient.h"
#include "../headers/clogProduct.h"
#include "../headers/faturacao.h"
#include "../headers/gestao.h"
#include "../headers/interface.h"
#include "../headers/parser.h"
#include "../headers/collection.h"
#include "../headers/stats.h"
#include "../headers/heap.h"

#include <stdlib.h>
#include <stdio.h>
#include <glib.h>
#include <time.h>
#include <string.h>

#define MODOS 2
#define FILIAIS 3
#define RANGE 26
#define PRODUCTS_DEPTH 2
#define CLIENTS_DEPTH 1

typedef struct configuration{
    char* client_path;
    char* product_path;
    char* sales_path;
    int modes;
    int branches;
    int range;
    int productsDepth;
    int clientsDepth;
}*Configuration;

struct sgv{
    int loaded;
    Configuration configuration;
    ClogProduct products;
    ClogClient clients;
    Faturacao faturacao;
    Gestao gestao[FILIAIS];
    Stats stats;
};


void free_key_fun(char* code){
    free(code);
}

static void destroyConfiguration(Configuration c){
    free(c->client_path);
    free(c->product_path);
    free(c->sales_path);
    free(c);
}

SGV initSGV(){
    int i;
    SGV sgv = malloc(sizeof(struct sgv));
    sgv->loaded = 0;
    sgv = loadSGVConfiguration(sgv);
    sgv->products = initClogProduct(sgv->configuration->productsDepth, sgv->configuration->range, (GCompareFunc)strcmp, free_key_fun, NULL);
    sgv->clients  = initClogClient(sgv->configuration->clientsDepth, sgv->configuration->range, (GCompareFunc)strcmp, free_key_fun, NULL);
    sgv->faturacao = initFaturacao(sgv->configuration->branches,sgv->configuration->modes,sgv->configuration->productsDepth,sgv->configuration->range,free_key_fun);
    for(i = 0; i < FILIAIS; i++)
        sgv->gestao[i] = initGestao(sgv->configuration->clientsDepth,sgv->configuration->range,(GCompareFunc)strcmp,free_key_fun);
    sgv->stats = initStats();
    return sgv;
}

void destroySGV(SGV sgv){
    int i;
    destroyClogProduct(sgv->products);
    destroyClogClient(sgv->clients);
    destroyFaturacao(sgv->faturacao);
    for(i = 0; i < sgv->configuration->branches; i++)
        destroyGestao(sgv->gestao[i]);
    destroyStats(sgv->stats);
    destroyConfiguration(sgv->configuration);
    free(sgv);
}

SGV loadSGVConfiguration(SGV sgv){
    int i = 0;
    FILE *c, *p, *s;
    FILE *config = fopen("db/config.txt","r");
    char line[100];
    Configuration conf = malloc(sizeof(struct configuration));

    while( i < 9 && fgets(line,100,config) ){
        switch( i ){
            case 0 : conf->client_path = strdup(strtok(line," \n\r"));
                     break;
            case 1 : conf->product_path = strdup(strtok(line," \n\r")); 
                     break;
            case 2 : conf->sales_path = strdup(strtok(line," \n\r")); 
                     break;
            case 3 : conf->modes = atoi(strtok(line," \n\r"));
                     break;
            case 4 : conf->branches = atoi(strtok(line," \n\r")); 
                     break;
            case 5 : conf->range = atoi(strtok(line," \n\r")); 
                     break;
            case 6 : conf->productsDepth = atoi(strtok(line," \n\r"));  
                     break;
            case 7 : conf->clientsDepth = atoi(strtok(line," \n\r"));  
                     sgv->configuration = conf;
                     break;
        }
        i++;
    }
    fclose(config);

    return sgv;
}


char* getConfigurationProductsPath(SGV sgv){
    return strdup(sgv->configuration->product_path);
}

char* getConfigurationClientsPath(SGV sgv){
    return strdup(sgv->configuration->client_path);
}

char* getConfigurationSalesPath(SGV sgv){
    return strdup(sgv->configuration->sales_path);
}

int getConfigurationProductsDepth(SGV sgv){
    return sgv->configuration->productsDepth;
}

int getConfigurationClientsDepth(SGV sgv){
    return sgv->configuration->clientsDepth;
}

int getSGVLoaded(SGV sgv){
    return sgv->loaded;
}

void setSGVLoaded(SGV sgv, int load){
    sgv->loaded = load;
}

int containProductSGV(SGV sgv, char* prod){
    return containsProduct(sgv->products,prod);
}

int containClientSGV(SGV sgv, char* cli){
    return containsClient(sgv->clients,cli);
}

void addPC(SGV sgv, char type, char* code){
    switch(type){
        case 'P' : addProduct(sgv->products,code);
                   addProductSell(sgv->faturacao, code);
                   break;
        case 'C' : addClient(sgv->clients,code);
    }
}

void addSell(SGV sgv, Venda v){
      updateSell(sgv->faturacao,v);
      updateClientSell(sgv->gestao[v->filial - 1],v);
}

void setLoad(SGV sgv, char catalog, double time){
    switch(catalog){
        case 'P' : setLoadTime(sgv->stats,1,time);
                   break;
        case 'C' : setLoadTime(sgv->stats,0,time);
                   break;
        case 'V' : setLoadTime(sgv->stats,2,time);
    }
}

void setValid(SGV sgv, char catalog, int valid){
    switch( catalog ){
        case 'P' : setValidation(sgv->stats,2,valid);
                   break;
        case 'C' : setValidation(sgv->stats,0,valid);
                   break;
        case 'V' : setValidation(sgv->stats,4,valid);
    }
}

void setInvalid(SGV sgv, char catalog, int invalid){
    switch( catalog ){
        case 'P' : setValidation(sgv->stats,3,invalid);
                   break;
        case 'C' : setValidation(sgv->stats,1,invalid);
                   break;
        case 'V' : setValidation(sgv->stats,5,invalid);
    }
}

int getStatsValidation(SGV sgv, int type){
    return getValidation(sgv->stats,type);
}

double getStatsLoadTime(SGV sgv, int type){
    return getLoadTime(sgv->stats,type);
}

/* --- Query 1 ---------------------------------------------------------------------------------- */

SGV loadSGVFromFiles(SGV sgv, char* clientsFilePath, char* productsFilePath, char* salesFilePath){
    loadFiles(sgv,clientsFilePath, productsFilePath, salesFilePath);
    sgv->loaded = 1;
    writeLoad(sgv->stats);
    
    return sgv;
}

/* --- Query 2 ---------------------------------------------------------------------------------- */

Collection getProductsStartedByLetter(SGV sgv, char letter){
    clock_t start, end;

    start = clock();
    Collection c = productByLetter(sgv->products, letter);
    end = clock();

    setQTime(sgv->stats,2,start,end);
    writeQTime(sgv->stats, 2);

    return c;
}

/* --- Query 3 ---------------------------------------------------------------------------------- */

Collection getVendfatMesProd(SGV sgv, int mes, char* prod, char globORfil){
    clock_t start, end;

    start = clock();
    Collection c = vendfatMesProd(sgv->faturacao, mes, prod, globORfil);
    end = clock();

    setQTime(sgv->stats,3,start,end);
    writeQTime(sgv->stats, 3);

    return c;
}

/* --- Query 4 ---------------------------------------------------------------------------------- */

Collection* getProdNotBuyed(SGV sgv){
    clock_t start, end;

    start = clock();
    Collection* c = notBuyed(sgv->faturacao);
    end = clock();

    setQTime(sgv->stats,4,start,end);
    writeQTime(sgv->stats, 4);

    return c;
}


/* --- Query 5 ---------------------------------------------------------------------------------- */

typedef struct q3{
    SGV sgv;
    Collection c;
}*Q3;

int comp(char*key, void* value, Q3 q3){
    int i, n = 0;

    for(i = 0; i < FILIAIS; i++)
        n += containsGestaoClient(q3->sgv->gestao[i], key);

    if( n == FILIAIS )
        addElem(q3->c,0,key);

    return 0;
}

Collection getClientsOfAllBranches(SGV sgv){
    double begin,end;

    begin = clock();

    Collection c = initCollection(1,16834);
    Q3 q3 = malloc(sizeof(struct q3));
    q3->sgv = sgv;
    q3->c = c;

    traverseClogClients(sgv->clients,comp,q3);
    free(q3);

    end = clock();

    setQTime(sgv->stats,5,begin,end);
    writeQTime(sgv->stats, 5);

    return c;
}

/* --- Query 6 ---------------------------------------------------------------------------------- */

typedef struct q6{
    SGV sgv;
    int n;
}*Q6;

int q6Traverse(char* key, void* value, Q6 q6){
    int i, v;

    for(i = 0; i < FILIAIS; i++)
        v += !containsGestaoClient(q6->sgv->gestao[i], key);

    if( v==3 )
        q6->n++;

    return 0;
}

CPNeverBought getClientsAndProductsNeverBoughtCount(SGV sgv){
    double begin, end;
    char clients[100];
    char products[100];

    begin = clock();

    Collection c = initCollection(2,1);
    Q6 q6 = malloc(sizeof(struct q6));
    q6->sgv = sgv;
    q6->n = 0;

    traverseClogClients(sgv->clients,q6Traverse,q6);
    CPNeverBought cp = initCPNeverBought(q6->n,productsNeverBoughtNumber(sgv->faturacao));

    free(q6);

    end = clock();

    setQTime(sgv->stats,6,begin,end);
    writeQTime(sgv->stats, 6);

    return cp;
}


/* --- Query 7 ---------------------------------------------------------------------------------- */

TotalBought getProductsBoughtByClient(SGV sgv, char* client){
    double begin, end;
    int i;

    begin = clock();

    TotalBought tb = initTotalBought(FILIAIS);

    for(i = 0; i < sgv->configuration->branches; i++)
        productsBoughtByClient(tb,i,sgv->gestao[i], client);

    end = clock();

    setQTime(sgv->stats,6,begin,end);
    writeQTime(sgv->stats, 6);

    return tb;
}

/* --- Query 8 ---------------------------------------------------------------------------------- */

TotalSalesBillings getSalesAndProfit(SGV sgv, int minMonth, int maxMonth){
    clock_t start, end;

    start = clock();
    TotalSalesBillings tsb = infoIntervMeses(sgv->faturacao, minMonth, maxMonth);
    end = clock();

    setQTime(sgv->stats,8,start,end);
    writeQTime(sgv->stats, 8);

    return tsb;
}



/* --- Query 9 ---------------------------------------------------------------------------------- */

Collection getProductBuyers(SGV sgv, char* productID, int branch){
    double begin, end;

    begin = clock();

    Collection c = initCollection(2,5000);
    productBuyers(c,sgv->gestao[branch - 1], productID);

    end = clock();

    setQTime(sgv->stats,6,begin,end);
    writeQTime(sgv->stats, 6);

    return c;
}


/* --- Query 10 ---------------------------------------------------------------------------------- */

static int extract(ProductAmount pa, void* value, Collection c){
    char n[50];
    char* p = getProductAmountProduct(pa);
    addElem(c,0,p);
    free(p);

    return 0;
}

Collection getClientFavoriteProduct(SGV sgv, char* clientID, int month){
    int i;
    double begin, end;

    begin = clock();

    GTree* p = g_tree_new_full((GCompareDataFunc)compProductAmount,NULL,(GDestroyNotify)destroyProductAmount,NULL);
    Collection c = initCollection(1, 200);

    for(i = 0; i < sgv->configuration->branches; i++)
        clientFavoriteProduct(p,sgv->gestao[i],clientID,month);

    g_tree_foreach(p,(GTraverseFunc)extract,c);

    end = clock();

    g_tree_destroy(p);

    setQTime(sgv->stats,6,begin,end);
    writeQTime(sgv->stats, 6);

    return c;
}

/* --- Query 11 ---------------------------------------------------------------------------------- */

void productBuyersToCount(Collection c, SGV sgv, int filial, char* product){
    productBuyers(c,sgv->gestao[filial-1],product);
}

int getN(char*key, ProductAmount pa, Heap h){
    addHeapElem(h, pa);

    return 0;
}

Collection getTopSelledProducts(SGV sgv, int limit){
    int i, k;
    double begin, end;
    char* product;
    char value[100];
    ProductAmount pa;
    begin = clock();

    Heap h = initHeap(1000,compMostSell);
    Collection c = initCollection(9, limit);
    GTree* p = g_tree_new_full((GCompareDataFunc)strcmp,NULL,free,(GDestroyNotify)destroyProductAmount);

    for(i = 0; i < sgv->configuration->branches; i++)
        mostSellProducts(p,sgv->gestao[i],i);

    g_tree_foreach(p,(GTraverseFunc)getN,h);
    for(i = 0; i < limit && getHeapIndex(h) > 0; i++){
        pa = extractMax(h);
        product = getProductAmountProduct(pa);
        addElem(c,0,product);
        sprintf(value,"%d",getPATotalSales(pa));
        addElem(c,1,value);
        sprintf(value,"%d",getPATotalClients(pa));
        addElem(c,5,value);
        for(k = 0; k < sgv->configuration->branches; k++){
            sprintf(value,"%d",getPASalesAt(pa,k));
            addElem(c,2 + k,value);
            sprintf(value,"%d",getPAClientsAt(pa,k));
            addElem(c,6 + k,value);
        }
        destroyProductAmount(pa);
        free(product);
    }
    end = clock();
    destroyHeap(h);
    setQTime(sgv->stats,11,begin,end);
    writeQTime(sgv->stats, 11);

    return c;
}


/* --- Query 12 ---------------------------------------------------------------------------------- */


Collection getClientTopProfitProducts(SGV sgv, char* clientID, int limit){
    int i;
    double begin, end;

    begin = clock();

    Collection c = initCollection(1, 100);
    Heap h = initHeap(1000, comparePA);
    GTree* p = g_tree_new_full((GCompareDataFunc)strcmp,NULL,free,(GDestroyNotify)destroyProductAmount);

    for(i = 0; i < sgv->configuration->branches; i++)
        clientTopProfitProducts(p,sgv->gestao[i], clientID);

    char* product;
    g_tree_foreach(p,(GTraverseFunc)getN,h);
    for(i = 0; i < limit && getHeapIndex(h) > 0; i++){
        product = getProductAmountProduct(extractMax(h));
        addElem(c,0,product);
        free(product);
    }

    destroyHeap(h);
//   g_tree_destroy(p);
    
    end = clock();

    setQTime(sgv->stats,6,begin,end);
    writeQTime(sgv->stats, 6);

    return c;
}












