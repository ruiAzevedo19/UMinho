#include "../headers/view.h"
#include "../headers/page.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

void errorMessage(char* message){
    printf("\n/!\\  %s   /!\\\n", message);
    sleep(2);
}

void mainMenuView(){
    printf("\e[2J\e[H");
    printf("\n--------------------------------------------------------------------------------\n");

    printf("\t   _____          _          __      __            _                      \n");
    printf("\t  / ____|        | |         \\ \\    / /           | |                   \n");
    printf("\t | |  __  ___ ___| |_ __ _  __\\ \\  / ___ _ __   __| | __ _ ___          \n");
    printf("\t | | |_ |/ _ / __| __/ _` |/ _ \\ \\/ / _ | '_ \\ / _` |/ _` / __|        \n");
    printf("\t | |__| |  __\\__ | || (_| | (_) \\  |  __| | | | (_| | (_| \\__ \\       \n");
    printf("\t  \\_____|\\___|___/\\__\\__,_|\\___/ \\/ \\___|_| |_|\\__,_|\\__,_|___/  \n");

    printf("\n--------------------------------------------------------------------------------\n\n");

    printf("\t%02d.  Carregar ficheiros\n", 1);
    printf("\t%02d.  Produtos começados por uma determinada letra\n", 2);
    printf("\t%02d.  Total de vendas e total faturado de um determinado produto\n", 3);
    printf("\t%02d.  Listar produtos que ninguém comprou\n", 4);
    printf("\t%02d.  Listar os clientes que compraram em todas as filiais\n", 5);
    printf("\t%02d.  Clientes que não realizaram compras e produtos não comprados\n", 6);
    printf("\t%02d.  Total de produtos comprados\n", 7);
    printf("\t%02d.  Faturação e vendas totais\n", 8);
    printf("\t%02d.  Compras relativas a um produto\n", 9);
    printf("\t%02d.  Produtos mais comprados\n", 10);
    printf("\t%02d.  N produtos mais vendidos\n", 11);
    printf("\t%02d.  N produtos que um determinado cliente gastou mais dinheiro\n", 12);
    printf("\t%02d.  Libertar memória\n", 13);
    printf("\n\t\t\t\t\t\t\t\t\t0. Sair\n");

    printf("--------------------------------------------------------------------------------\n\n");

    printf("Introduza o comando >> ");
}

void printLoadTime(int pv, int pi, int cv, int ci, double pl, double cl, double sl, int sv, int si){
    printf("\
--- Load Time --------------------\n\
[Clients]     (%lf ms)\n\
    Valid     : %d\n\
    Invalid   : %d\n\
[Products]    (%lf ms)\n\
    Valid     : %d\n\
    Invalid   : %d\n\
[Sells]       (%lf ms)\n\
    Valid     : %d\n\
    Invalid   : %d\n\
----------------------------------\n\n",cl,cv,ci,pl,pv,pi,sl,sv,si);
}

void printQueryHeader(char* header){
    printf("--------------------------------------------------------------------------------\n\n");
    printf("         %s                                                             \n", header);
    printf("--------------------------------------------------------------------------------\n");
    printf("                                                                         0. Sair\n\n");
}

void singleView(char** info, int value[], int n ){
    int i;
    for(i = 0; i < n; i++)
        printf("\t%s : %d\n",info[i], value[i]);
}

void oneHeaderView(char* header, Page p){
    char** page = getSinglePage(p, getCurrentPage(p));
    int i,n = strlen(header);

    printf("Página %d de %d\n\n", getCurrentPage(p), getNrPages(p));
    printf("\t||   %s   ||\n", header);
    printf("\t");
    for(i = 0; i < n + 10; i++)
        printf("-");
    printf("\n");

    for(i = 0; page[i] ; i++){
        printf("\t||   %s     ||\n", page[i]);
        free(page[i]);
    }
    free(page);
}

void twoHeaderView(char* header1, char* header2, Page p){
    char** page = getSinglePage(p, getCurrentPage(p));
    int i,n = strlen(header1) + strlen(header2);

    printf("Página %d de %d\n\n", getCurrentPage(p), getNrPages(p));
    printf("\t||   %-7s   ||   %-7s   ||\n", header1,header2);
    printf("\t");
    for(i = 0; i < n + 17; i++)
        printf("-");
    printf("\n");

    for(i = 0; page[i] && page[i + 1] ; i+=2){
        printf("\t||   %-7s   ||   %-7s    ||\n", page[i], page[i + 1]);
        free(page[i]); free(page[i + 1]);
    }
    free(page);
}

void threeHeaderView(int* table, int n){
    int i,j;

    printf("||   %-5s   ||   %-12s  ||   %-12s  ||   %-12s  ||\n", "Mês","Filial 1", "Filial 2", "Filial 3");
    for(i = 0, j = 1; i < n; i += 3)
        printf("||   %-5d  ||   %-12d  ||   %-12d  ||   %-12d  ||\n",j++,table[i],table[i+1],table[i+2]);
}

void nineHeaderView(Page p){
    char** page = getSinglePage(p, getCurrentPage(p));
    int i,j;
    printf("Página %d de %d\n\n", getCurrentPage(p), getNrPages(p));
    for(i = 0; page[i] && page[i+1] && page[i+2] && page[i+3] && page[i+4] && page[i+5] && page[i+6] && page[i+7] && page[i+8] && page[i + 9] ; i+=9){
        printf("\t%s   ||   vendas totais = %s (Filial 1: %s , Filial 2: %s , Filial 3: %s)   ||   clientes totais = %s (Filial 1: %s , Filial 2: %s , Filial 3: %s)\n",page[i],page[i+1],page[i+2],page[i+3],page[i+4], page[i+5],page[i+6],page[i+7], page[i+8]);
        for(j = 0; j < 9; j++)
            free(page[i+j]);
    }
    free(page);
}

void querie3View(char modo, Page p){
    char** page = getSinglePage(p, getCurrentPage(p));
    int i;
    printf("Página %d de %d\n\n", getCurrentPage(p), getNrPages(p));
    if(modo == 'G') {printf("\t||   Global   ||\n");printf("\t");}
    else {printf("\t||   Filial %d  ||\n", getCurrentPage(p));printf("\t");}

    for(i = 0; i < 60; i++)
        printf("-");
    printf("\n");

    for(i = 0; page[i] ; i++){
        switch(i){
            case 0 :
                printf("\t||   Total de Vendas no Modo Normal        ->");
                break;
            case 1 :
                printf("\t||   Total de Faturação no Modo Normal     ->");
                break;
            case 2 :
                printf("\t||   Total de Vendas no Modo Promoção      ->");
                break;
            case 3 :
                printf("\t||   Total de Faturação no Modo Promoção   ->");
                break;
        }
        printf("   %s     \n", page[i]);
        free(page[i]);
    }
    free(page);
}


void querie4View(int mesIni, int mesFin, int sales, double billings){
    int i;
    printf("\t||   Info entre os meses [%d] e [%d]\t     ||\n", mesIni, mesFin);
    printf("\t");
    for(i = 0; i < 47; i++)
        printf("-");
    printf("\n");
    printf("\t||   Total de Vendas      -> %d \n",sales);
    printf("\t||   Total de Faturação   -> %.3f \n",billings);

}

void querie11View(Page p){
    char** page = getSinglePage(p, getCurrentPage(p));
    int i;

    printf("Página %d de %d\n\n", getCurrentPage(p), getNrPages(p));
    printf("\t||   %-7s   ||   %-7s   ||   %-7s   ||\n", "Produtos","Unidades","Clientes");
    printf("\t");
    for(i = 0; i < 51; i++)
        printf("-");
    printf("\n");

    for(i = 0; page[i] && page[i + 1] && page[i + 2] ; i+=3){
        printf("\t||   %-7s    ||   %-7s    ||   %-7s    ||\n", page[i], page[i + 1], page[i + 2]);
        free(page[i]); free(page[i + 1]); free(page[i + 2]);
    }
    free(page);
}


void printRequest(char* request){
    printf("%s >> ", request);
}

void clearScreen(){
    printf("\e[2J\e[H");
}









