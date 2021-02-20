#include "../headers/interface.h"
#include "../headers/view.h"
#include "../headers/controller.h"
#include "../headers/collection.h"
#include "../headers/page.h"
#include "../headers/parser.h"

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>

/*
#define CLIENT_PATH "db/Clientes.txt"
#define PRODUCT_PATH "db/Produtos.txt"
#define SALE_PATH "db/Vendas_1M.txt"
*/

static int isNumber(char* n){
    int i, r = 1;
    for(i = 0; n[i] && r; i++)
        if( !isdigit(n[i]) )
            r = 0;

    return r;
}

static SGV q1Controller(SGV sgv){
    char in[10];
    char path[200];
    char client[300];
    char product[300];
    char sales[300];
    FILE *c, *p, *s;    
    char *client_default, *product_default, *sales_default;
    int isRunning = 1;
    double pv, pi, cv, ci, pl, cl, sl, sv, si;

    while( isRunning ){
        clearScreen(); 
        printQueryHeader("Carregamento dos ficheiros de dados");
        if( getSGVLoaded(sgv) ){
            errorMessage("Os ficheiro já estão carregados!");
            return sgv;
        }
        printRequest("Quer carregar os ficheiros default? [s/n]");
        scanf("%s", in);
        printf("\n");
        
        if( *in == '0' )
            return sgv;

        if( !strcmp(in,"s") || !strcmp(in,"S") || !strcmp(in,"n") || !strcmp(in,"N") ){
            if( !strcmp(in,"s") || !strcmp(in,"S") ){
                client_default = getConfigurationClientsPath(sgv);
                product_default = getConfigurationProductsPath(sgv);
                sales_default = getConfigurationSalesPath(sgv);
                sgv = loadSGVFromFiles(sgv,client_default,product_default,sales_default);
                free(client_default) ; free(product_default) ; free(sales_default) ;
            }
            if( !strcmp(in,"n") || !strcmp(in,"N") ){
                printRequest("Clientes");
                scanf("%s",client);
                printRequest("Produtos");
                scanf("%s",product);
                printRequest("Vendas");
                scanf("%s",sales);

                FILE* c = fopen(client,"r");
                FILE* p = fopen(product,"r");
                FILE* s = fopen(sales,"r");

                if( !c || !p || !s ){
                    if( !c ) errorMessage("Ficheiro dos clientes não existe");
                    if( !p ) errorMessage("Ficheiro dos produtos não existe");
                    if( !s ) errorMessage("Ficheiro das vendas não existe");
                    if( c ) fclose(c);
                    if( p ) fclose(p);
                    if( s ) fclose(s);
                    return NULL;
                }
                sgv = loadSGVFromFiles(sgv,client,product,sales);
                fclose(c); fclose(p); fclose(s);
            }
            cv = getStatsValidation(sgv,0); ci = getStatsValidation(sgv,1);
            pv = getStatsValidation(sgv,2); pi = getStatsValidation(sgv,3);
            sv = getStatsValidation(sgv,4); si = getStatsValidation(sgv,5);
            cl = getStatsLoadTime(sgv,0); pl = getStatsLoadTime(sgv,1);
            sl = getStatsLoadTime(sgv,2);
            printLoadTime(pv,pi,cv,ci,pl,cl, sl, sv, si);
            printRequest("Prima qualquer tecla para sair");
            scanf("%s", in);
            isRunning = 0;
        }
        else{
            errorMessage("Formato inválido");
        }
    }
    return sgv;
}

static void q2Controller(SGV sgv){
    int isRunning = 1;
    char c[10];
    
    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Produtos começados por uma determinada letra");
        printRequest("Escolha uma letra");
        scanf("%s",c); 
        if( strlen(c) == 1 && *c == '0')
            return;
        if( strlen(c) == 1 && isalpha(*c) && isupper(*c) )
            break;
        else 
            return;
    }
    Collection collection = getProductsStartedByLetter(sgv,*c); 
    Page p = initPage(collection,20,1);
    while( isRunning ){
        clearScreen();
        printQueryHeader("Produtos começados por uma determinada letra");
        oneHeaderView("Produtos", p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}

static void q3Controller(SGV sgv){
    int isRunning = 1;
    char m[10]; char prod[40]; char mes[10]; char f[10]; int mesInt;
    int v;

    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Número total de vendas e total facturado de um produto num mês");
        printf("Insira um produto >> ");
        scanf("%s", prod);
        v = validate(getConfigurationProductsDepth(sgv),1000,9999,prod);
        if( v )
            v = containProductSGV(sgv,prod);
        while( !v ){
            printf("AVISO: O produto que inseriu não está disponivel no sistema\n");
            printf("Pretende sair? (0) ou Insira um produto >> ");
            scanf("%s", prod);
            if( strlen(prod) == 1 && *prod == '0' )
                return;
            v = validate(getConfigurationProductsDepth(sgv),1000,9999,prod);
            if( v )
                v = containProductSGV(sgv,prod);
        }
        printf("Insira um mês >> ");
        scanf("%s", mes);
        mesInt = atoi(mes);
        if( mesInt < 1 || mesInt > 12) return;

        printf("Pretende o resultado em modo global (G) ou filial a filial (F) >> ");
        scanf("%s",m);

        if( strlen(m) == 1 && *m == '0')
            return;
        if( strlen(m) == 1 && (*m == 'F' || *m == 'G'))
            break;
        else
            return;
    }
    Collection collection = getVendfatMesProd(sgv, mesInt, prod, m[0]);
    Page p = initPage(collection,1,1);
    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Total de vendas e total facturado de um produto num mês");
        switch(*m){
            case 'F':
                querie3View(*m,p);
                printf("\nFilial >> ");
                scanf("%s", f);
                if( strlen(f) == 1 && *f == '0' ){
                    destroyPage(p);
                    return;
                }
                if( isNumber(f) && atoi(f) >= 1  && atoi(f)<=3)
                    setCurrentPage(p,atoi(f));
                break;
            case 'G':
                querie3View(*m,p);
                printf("\nPrima qualquer tecla para sair >> ");
                scanf("%s", f);
                destroyPage(p);
                return;
            default:
                break;
        }
    }
}

static void q4Controller(SGV sgv){
    int isRunning = 1;
    char m[10]; char prod[40]; char mes[10]; char f[10] = "1"; int mesInt; char c[10];int pNow = 1;

    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Lista ordenada dos códigos dos produtos (e o seu número total) que ninguém comprou");
        printf("Pretende o resultado em modo global (G) ou filial a filial (F) >> ");
        scanf("%s",m);

        if( strlen(m) == 1 && *m == '0')
            return;
        if( strlen(m) == 1 && (*m == 'F' || *m == 'G'))
            break;
        else
            return;
    }
    Collection* collections = getProdNotBuyed(sgv);
    Page pGLOB = initPage(collections[FILIAIS],20,1);
    Page pFILS[FILIAIS];
    int i;
    for(i = 0; i<FILIAIS; i++) pFILS[i] = initPage(collections[i],20,1);

    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Produtos que ninguém comprou");
        switch(*m){
            case 'F' :
                printf("\t||   Total de produtos não comprados na Filial %d  ->  %d    \n",pNow,getHeaderIndex(collections[pNow - 1], 0));
                oneHeaderView("Produtos",pFILS[pNow-1]);
                printf("\nUsar as iniciais:\nF | para mudar de Filial \nP | para mudar de Página \n >> ");
                scanf("%s", f);
                if( strlen(f) == 1 && *f == '0' ){
                    free(collections);
                    return;
                }
                if( (*f) == 'F' && atoi(f+1) >= 1  && atoi(f+1) <= FILIAIS){
                    pNow = atoi(f+1);
                    setCurrentPage(pFILS[pNow-1],0);
                }
                else if( (*f) == 'P' && isNumber(f+1))
                    setCurrentPage(pFILS[pNow-1],atoi(f+1));
                break;
            case 'G' :
                printf("\t||   Total de produtos não comprados Globalmente  ->  %d     \n",getHeaderIndex(collections[FILIAIS], 0));
                oneHeaderView("Produtos", pGLOB);
                printf("\nPágina >> ");
                scanf("%s", c);
                if( strlen(c) == 1 && *c == '0' ){
                    free(collections);
                    return;
                }
                if( isNumber(c) )
                    setCurrentPage(pGLOB,atoi(c));
        }
    }
}

static void q5Controller(SGV sgv){
    int isRunning = 1;
    char c[10];

    Collection collection = getClientsOfAllBranches(sgv); 
    Page p = initPage(collection,20,1);
    while( isRunning ){
        clearScreen();
        printQueryHeader("Listar os clientes que compraram em todas as filiais");
        oneHeaderView("Clientes", p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}

static void q6Controller(SGV sgv){
    int isRunning = 1;
    char command[10];

    CPNeverBought cp = getClientsAndProductsNeverBoughtCount(sgv);
    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Número de clientes que não realizaram compras e número de produtos não comprados");
        char* info[2] = {"Clientes" , "Productos"}; 
        int value[2] = {getClientsNeverBought(cp),getProductsNeverBought(cp)};
        singleView(info, value,2);
        printRequest("\nSair");
        scanf("%s",command);
        break;
    }
    destroyCPNeverBought(cp);
}

static void q7Controller(SGV sgv){
    int i;
    int isRunning = 1;
    char client[7], command[10];

    while( isRunning ){
        clearScreen();
        printQueryHeader("Número total de produtos comprados por um determinado cliente");
        printRequest("Código do cliente");
        scanf("%s", client);
        if( validate(getConfigurationClientsDepth(sgv),1000,5000,client) ){
            if( containClientSGV(sgv,client) )
                break;
        }
        else{
            errorMessage("Código de cliente inexistente");
            printRequest("Deseja sair? [S/N]");
            scanf("%s", command);
            if( !strcmp(command,"S") || !strcmp(command,"s") )
                return;
        }
    }
    TotalBought tb = getProductsBoughtByClient(sgv,client);
    int* table = getTotalBoughtTable(tb);
    while( isRunning ){
        printf("\n");
        threeHeaderView(table,getTotalBoughtDim(tb));
        printRequest("Sair");
        scanf("%s",command);
        break;
    }
    free(table);
    destroyTotalBought(tb);
}

static void q8Controller(SGV sgv){
    int isRunning = 1;
    char m[10]; char mes[10]; char f[10]; int mesIni;int mesFin;

    while( isRunning ) {
        clearScreen();
        printQueryHeader("Total de vendas e total facturado num intervalo de meses");

        printRequest("Insira o mês inicial");
        scanf("%s", mes);
        mesIni = atoi(mes);
        if (strlen(mes) == 1 && *m == '0')
            return;
        while (!isNumber(mes) || (mesIni < 1 || mesIni > 12)) {
            printf("AVISO: O mês inicial que inseriu não é válido\n");
            printf("Pretende sair? (0) ou Insira um mes válido >> ");
            scanf("%s", mes);
            if (strlen(mes) == 1 && *mes == '0')
                return;
            mesIni = atoi(mes);
        }

        printf("Insira o mês final >> ");
        scanf("%s", mes);
        mesFin = atoi(mes);
        if (strlen(mes) == 1 && *m == '0')
            return;
        while (!isNumber(mes) || (mesFin < mesIni || mesFin > 12)) {
            printf("AVISO: O mês final que inseriu não é válido\n");
            printf("Pretende sair? (0) ou Insira um mes válido >> ");
            scanf("%s", mes);
            if (strlen(mes) == 1 && *mes == '0')
                return;
            mesFin = atoi(mes);
        }
        break;
    }
    TotalSalesBillings tsb = getSalesAndProfit(sgv, mesIni-1, mesFin-1);
    clearScreen();
    printQueryHeader("Total de vendas e total facturado num intervalo de meses");
    querie4View(mesIni,mesFin,getTSBSales(tsb),getTSBBillings(tsb));
    printRequest("\nPrima qualquer tecla para sair");
    scanf("%s", f);

    destroyTotalSalesBillings(tsb);

    return;
}

static void q9Controller(SGV sgv){
    int isRunning = 1;
    char c[10];
    char product[7], f[20];
    int filial, v1,v2;

    while( isRunning ){
        product[0] = 0;
        f[0] = 0;
        clearScreen(); 
        printQueryHeader("Compras relativas a um produto");
        printRequest("Código do produto");
        scanf("%s",product); 
        printRequest("Filial");
        scanf("%s",f); 
        filial = atoi(f);
        
        v1 = validate(getConfigurationProductsDepth(sgv), 1000,9999,product);
        v2 = filial > 0 && filial < 4;

        if( v1 )
            v1 = containProductSGV(sgv,product);

        if( !v1 ){
            errorMessage("Produto inexistente!");
            printRequest("Deseja sair? [S/N]");
            scanf("%s",c);
            if( *c == 'S' || *c == 's' )
                return;
        }
        if( !v2 ){
            errorMessage("Filial inválida, indique uma filial no intervalo [1..3]");
            printRequest("Deseja sair? [S/N]");
            scanf("%s",c);
            if( *c == 'S' || *c == 's' )
                return;
        }
        if( v1 && v2 )
            break;
    }
    isRunning = 1;
    Collection collection = getProductBuyers(sgv, product, filial); 
    Page p = initPage(collection,20,1);
    char* total[2] = {"Total em Normal","Total em Promoção"};
    int value[2] = {getHeaderIndex(collection,0), getHeaderIndex(collection,1)};
    while( isRunning ){
        clearScreen();
        printQueryHeader("Compras relativas a um produto");
        singleView(total,value, 2);
        printf("\n");
        twoHeaderView("Normal", "Promoção", p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}

static void q10Controller(SGV sgv){
    int isRunning = 1;
    char c[10];
    char client[6], m[20];
    int v1,v2;
    int month;

    while( isRunning ){
        clearScreen();
        printQueryHeader("Produtos mais comprados por um cliente");
        printRequest("Código do cliente");
        scanf("%s",client); 
        printRequest("Mês");
        scanf("%s",m); 
        month = atoi(m);

        v1 = validate(getConfigurationClientsDepth(sgv),1000,5000,client);
        v2 = month > 0 && month < 13;
        if( v1 )
            v1 = containClientSGV(sgv,client);
        if( v1 && v2 )
            break;
        else{
            if( !v1 )
                errorMessage("Cliente inexistente!");
            if( !v2 )
                errorMessage("Mês inválido, introduza um mês no intervalo [1..12]");
            printRequest("Deseja sair? [S/N]");
            scanf("%s",c);
            if( *c == 'S' || *c == 's' )
                return;
        }
    }
    Collection collection = getClientFavoriteProduct(sgv, client, month); 
    Page p = initPage(collection,20,1);
    while( isRunning ){
        printf("\e[2J\e[H");
        printQueryHeader("Produtos mais comprados por um cliente");
        oneHeaderView("Produtos", p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}

static void q11Controller(SGV sgv){
    int isRunning = 1;
    char c[10];
    char lim[20];
    int limit, valC, valL;
    while( isRunning ){
        clearScreen();
        printQueryHeader("Top dos produtos mais vendidos");
        printRequest("Limite");
        scanf("%s",lim); 
        limit = atoi(lim);
        valL = limit > 0;
        if( valL ){
            break;
        }
        else{
            errorMessage("Limite inválido!");
            printRequest("Deseja sair? [S/N]");
            scanf("%s",c);
            if( *c == 'S' || *c == 's' )
                return;
        }
        isRunning = 1;
    }
    Collection collection = getTopSelledProducts(sgv,limit); 
    Page p = initPage(collection,20,1);
    while( isRunning ){
        clearScreen();
        printQueryHeader("Top dos produtos mais vendidos");
        nineHeaderView(p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}

static void q12Controller(SGV sgv){
    int isRunning = 1;
    char c[10];
    char client[6], lim[20];
    int limit, valC, valL;
    while( isRunning ){
        clearScreen();
        printQueryHeader("Produtos que um cliente mais gastou dinheiro");
        printRequest("Código do cliente");
        scanf("%s",client); 
        printRequest("Limite");
        scanf("%s",lim); 
        limit = atoi(lim);
        valC = validate(getConfigurationClientsDepth(sgv),1000,5000,client);
        if( valC )
            valC = containClientSGV(sgv,client);
        valL = limit > 0;
        if( valC && valL ){
            break;
        }
        else{
            if( !valC )
                errorMessage("Cliente inexistente!");
            if( !valL )
                errorMessage("Limite inválido!");
            printRequest("Deseja sair? [S/N]");
            scanf("%s",c);
            if( *c == 'S' || *c == 's' )
                return;
        }
        isRunning = 1;
    }
    Collection collection = getClientTopProfitProducts(sgv,client,limit); 
    Page p = initPage(collection,20,1);
    while( isRunning ){
        clearScreen();
        printQueryHeader("Produtos que um cliente mais gastou dinheiro");
        oneHeaderView("Clientes", p);
        printRequest("\nPágina"); 
        scanf("%s", c);
        if( strlen(c) == 1 && *c == '0' )
            return;
        if( isNumber(c) )
            setCurrentPage(p,atoi(c));
    }
}


SGV controller(SGV sgv){
    int query, isRunning = 1;
    char q[5];
    while( isRunning ){
        mainMenuView();
        scanf("%s", q);

        if( isNumber(q) ){
            query = atoi(q);
            if( !query || query == 1 || getSGVLoaded(sgv) == 1 )
            switch( query ){
                case 0 : isRunning = 0;
                        break;
                case 1 : sgv = q1Controller(sgv);
                        break;
                case 2 : q2Controller(sgv);
                        break;
                case 3 : q3Controller(sgv);
                        break;
                case 4 : q4Controller(sgv);
                        break;
                case 5 : q5Controller(sgv);
                        break; 
                case 6 : q6Controller(sgv);
                        break;
                case 7 : q7Controller(sgv);
                        break;
                case 8 : q8Controller(sgv);
                        break;
                case 9 : q9Controller(sgv);
                        break;
                case 10 : q10Controller(sgv);
                        break;
                case 11 : q11Controller(sgv);
                        break;
                case 12 : q12Controller(sgv);
                          break;
                case 13 : destroySGV(sgv);
                          sgv = initSGV();
                          break;
                default : errorMessage("Selecione uma query no intervalo [1..12]"); 
            }
            else
                errorMessage("Ficheiros não carregados");
        }
        else{
            errorMessage("Comando inválido");
            isRunning = 1;
        }
    }
    return sgv;
}

