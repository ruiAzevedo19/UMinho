#include "../headers/catalog.h"
#include "../headers/gestao.h"
#include "../headers/estruturasAux.h"
#include "../headers/collection.h"

#include <time.h>
#include <stdlib.h>
#include <glib.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdio.h>

struct gestao{
    Catalog clients;
};

typedef struct prodInfo{
    /* i = 0 => N  ||  i = 1 => P */
    int buy[12];
    int total;
    int sells[2];
    int billings[2];
}*ProdInfo;

typedef struct clientInfo{
    int totalBuy[12];
    GTree* products;
}*ClientInfo;

struct cpNeverBought{
    int clients;
    int products;
};

struct totalBought{
    int n;
    int rows;
    int cols;
    int* table;
};

struct productAmount{
    char* product;
    double billing;
    int amount;
    int totalSales;
    int totalClients;
    int sales[3];
    int clients[3];
};

ProductAmount initProductAmount(char* product, double billing, int amount){
    ProductAmount p = malloc(sizeof(struct productAmount));
    p->product = strdup(product);
    p->billing = billing;
    p->amount = amount;

    return p;
} 

int getPATotalSales(ProductAmount pa){
    return pa->totalSales;
}

int getPATotalClients(ProductAmount pa){
    return pa->totalClients;
}

int getPASalesAt(ProductAmount pa, int i){
    return pa->sales[i];
}

int getPAClientsAt(ProductAmount pa, int i){
    return pa->clients[i];
}

void setPATotalSales(ProductAmount pa, int total){
    pa->totalSales = total;
}

void setPATotalClients(ProductAmount pa, int total){
    pa->totalClients = total;
}

void setPASalesAt(ProductAmount pa, int i, int sales){
    pa->sales[i] = sales;
}

void setPAClientsAt(ProductAmount pa, int i, int clients){
    pa->clients[i] = clients;
}

void destroyProductAmount(ProductAmount pa){
    free(pa->product);
    free(pa);
}

char* getProductAmountProduct(ProductAmount pa){
    return strdup(pa->product);
}

double getProductAmountBilling(ProductAmount pa){
    return pa->billing;
}

int getProductAmountAmount(ProductAmount pa){
    return pa->amount;
}

int sizeofProductAmount(){
    return sizeof(struct productAmount);
}

int comparePA(ProductAmount p1, ProductAmount p2){
    if( p1->billing > p2->billing )
        return 1;
    if( p1->billing < p2->billing )
        return -1;
    return 0;
}

int compProductAmount(ProductAmount p1, ProductAmount p2){
    if( p1->amount > p2->amount )
        return -1;
    if( p1->amount < p2->amount )
        return 1;
    return 0;
}

int comparePAmount(ProductAmount p1, ProductAmount p2){
    if( p1->amount > p2->amount )
        return 1;
    if( p1->amount < p2->amount )
        return -1;
    return 0;
}

TotalBought initTotalBought(int filiais){
    int i;
    TotalBought tb = malloc(sizeof(struct totalBought));
    tb->rows = 12;
    tb->cols = filiais;
    tb->n = filiais * 12;
    tb->table = malloc(sizeof(int) * filiais * 12);
    for(i = 0; i < tb->n; i++ )
        tb->table[i] = 0;
    return tb;
}

void destroyTotalBought(TotalBought tb){
    int i;
    free(tb->table);
    free(tb);
}

void setTotalBought(TotalBought tb, int filial, int month, int sell){
    if( filial >= 0 && filial < tb->cols && month >= 0 && month < 12)
        tb->table[tb->cols * month + filial] += sell;
}

int getTotalBought(TotalBought tb, int filial, int month){
    int sell = -1;
    if( filial >= 0 && filial < tb->cols && month >= 0 && month < 12)
        sell = tb->table[tb->cols * month + filial];
    return sell;
}

int* getTotalBoughtTable(TotalBought tb){
    int i,j;
    int* table = malloc(sizeof(int) * tb->n);

    for(i = 0; i < tb->n; i++)
        table[i] = tb->table[i];

    return table;
}

int getTotalBoughtDim(TotalBought tb){
    return tb->n;
}

void free_key(char* code){
    free(code);
}

void destroyClientInfo(ClientInfo ci){
    int i;
    g_tree_destroy(ci->products);
    free(ci);
}

void destroyProdInfo(ProdInfo pi){
    free(pi);
}

ClientInfo initClientInfo(){
    int i;
    ClientInfo ci = malloc(sizeof(struct clientInfo));
    for(i = 0; i < 12; i++)
        ci->totalBuy[i] = 0;
    ci->products = g_tree_new_full((GCompareDataFunc)strcmp,NULL,(GDestroyNotify)free_key,(GDestroyNotify)destroyProdInfo);

    return ci;
}

ProdInfo initProdInfo(){
    int i;
    ProdInfo pi = malloc(sizeof(struct prodInfo));
    pi->total = 0;
    for(i = 0; i < 12; i++)
        pi->buy[i] = 0;
    for(i = 0; i < 2; i++)
        pi->sells[i] = pi->billings[i] = 0;

    return pi; 
}

Gestao initGestao(int depth, int range, void* compare_fun, void* free_key_fun){
    Gestao g = malloc(sizeof(struct gestao));
    g->clients = initCatalog(depth,range, compare_fun, free_key_fun, destroyClientInfo);   // mudar free value 

    return g;
}

void destroyGestao(Gestao g){
    destroyCatalog(g->clients);
    free(g);
}

CPNeverBought initCPNeverBought(int c, int p){
    CPNeverBought cp = malloc(sizeof(struct cpNeverBought));
    cp->clients = c;
    cp->products = p; 

    return cp;
}

void destroyCPNeverBought(CPNeverBought cp){
    free(cp);
}

int getClientsNeverBought(CPNeverBought cp){
    return cp->clients;
}

int getProductsNeverBought(CPNeverBought cp){
    return cp->products;
}

int containsGestaoClient(Gestao g, char* key){
    return containsKey(g->clients,key);
}

void updateClientSell(Gestao g, Venda v){
    ClientInfo ci = (ClientInfo)getValue(g->clients,v->cliente);
    ProdInfo pi;
    int i = (v->modo == 'N') ? 0 : 1;
    if( !ci ){
        ci = initClientInfo();
        pi = initProdInfo();
        pi->total = v->unitCompra;
        ci->totalBuy[v->mes - 1] = v->unitCompra;
        pi->buy[v->mes - 1] = v->unitCompra;
        pi->sells[i] = v->unitCompra;
        pi->billings[i] = v->unitCompra * v->priceUnit;
        g_tree_insert(ci->products,strdup(v->produto),pi);
        addKeyValue(g->clients,v->cliente,ci);
    }
    else{
        ci->totalBuy[v->mes - 1] += v->unitCompra;
        pi = g_tree_lookup(ci->products,v->produto);

        if( !pi ){
            pi = initProdInfo();
            pi->total = v->unitCompra;
            pi->buy[v->mes - 1] = v->unitCompra;
            pi->sells[i] = v->unitCompra;
            pi->billings[i] = v->unitCompra * v->priceUnit;
            g_tree_insert(ci->products,strdup(v->produto),pi);
        }
        else{
            pi->total += v->unitCompra;
            pi->buy[v->mes - 1] += v->unitCompra;
            pi->sells[i] += v->unitCompra;
            pi->billings[i] += v->unitCompra * v->priceUnit;
        }
    }
}

GTree* getGestaoClients(Gestao g){
    return cloneClog(g->clients);
}

void productsBoughtByClient(TotalBought tb, int filial, Gestao g, char* client){
    int i;
    char v[20];
    ClientInfo ci = (ClientInfo)getValue(g->clients,client);

    for(i = 0; i < 12; i++)
        setTotalBought(tb, filial, i, ci->totalBuy[i]);
}

typedef struct pb{
    Collection c;
    char* prod;
}*PB;

int pbTraverse(char* key, ClientInfo ci, PB pb){
    ProdInfo pi = g_tree_lookup(ci->products,pb->prod);
    if( pi ){
        if( pi->sells[0] )
            addElem(pb->c,0,key);
        if( pi->sells[1] )
            addElem(pb->c,1,key);
    }
    return 0;
}

void productBuyers(Collection c, Gestao g, char* prod){
    PB pb = malloc(sizeof(struct pb));
    pb->c = c;
    pb->prod = prod;

    traverseCatalog(g->clients,pbTraverse,pb);

    free(pb);
}

typedef struct treeMonth{
    GTree* p;
    int month;
}*TreeMonth;

static int extractProduct(char* product, ProdInfo pi, TreeMonth tm){
    if( pi->buy[tm->month - 1] ){
        ProductAmount pa = initProductAmount(product,0,pi->buy[tm->month - 1]);
        g_tree_insert(tm->p,pa,NULL);
    }
    return 0;
}

void clientFavoriteProduct(GTree* p, Gestao g, char* clientID, int month){
    ClientInfo ci = (ClientInfo)getValue(g->clients, clientID);
    TreeMonth tm = malloc(sizeof(struct treeMonth));
    tm->month = month;
    tm->p = p;

    g_tree_foreach(ci->products,(GTraverseFunc)extractProduct,tm);

    free(tm);
}


static int extractProductBilling(char* key, ProdInfo pi, GTree* p){
    ProductAmount pa = g_tree_lookup(p,key);
    if( !pa ){
        pa = initProductAmount(key,pi->billings[0] + pi->billings[1],0);
        g_tree_insert(p,key,pa);
    }
    else
        pa->billing += pi->billings[0] + pi->billings[1];
   return 0; 
}

void clientTopProfitProducts(GTree* p, Gestao g, char* clientID){
    ClientInfo ci = (ClientInfo)getValue(g->clients,clientID);

    g_tree_foreach(ci->products,(GTraverseFunc)extractProductBilling,p);
}

typedef struct q12{
    GTree* p;
    int filial;
}*Q12;

int compMostSell(ProductAmount p1, ProductAmount p2){
    if( p1->totalSales > p2->totalSales )
        return 1;
    if( p1->totalSales < p2->totalSales )
        return -1;
    return 0;
}

int add(char*key, ProdInfo pi, Q12 q12){
    int i,sells;
    ProductAmount pa = g_tree_lookup(q12->p,key);
    
    for(i = 0; i < 12; i++)
        sells += pi->buy[i];

    if( !pa ){
        pa = malloc(sizeof(struct productAmount));
        pa->product = strdup(key);
        pa->totalSales = pi->total;
        pa->totalClients = 1;
        for(i = 0; i < 3; i++){
            pa->clients[i] = 0;
            pa->sales[i] = 0;
        }
        pa->clients[q12->filial] = 1;
        pa->sales[q12->filial] = pi->total;
        g_tree_insert(q12->p,strdup(key),pa);
    }
    else{
        pa->totalSales += pi->total;
        pa->totalClients += 1;
        pa->clients[q12->filial] += 1;
        pa->sales[q12->filial] += pi->total;
    }
    return 0;
}

int mostSell(char* key, ClientInfo ci, Q12 q12){
    g_tree_foreach(ci->products,(GTraverseFunc)add,q12);

    return 0;
}

void mostSellProducts(GTree* p, Gestao g, int filial){
    Q12 q12 = malloc(sizeof(struct q12));
    q12->p = p;
    q12->filial = filial;
    traverseCatalog(g->clients,(GTraverseFunc)mostSell,q12);
    free(q12);
}









