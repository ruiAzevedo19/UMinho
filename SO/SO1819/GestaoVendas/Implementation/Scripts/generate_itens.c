/*******************************************************************************
*
* NOME DO FICHEIRO :        ma_api.c
*
* DESCRIÇÃO :
*       Declaração das funções que vão servir a manutenção de artigos
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#include "../Manutencao/modules/ma_api.h"
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <stdio.h>

/* --------------------------------------------------------------------------------- */

static float float_rand( float min, float max ){
    float scale = rand() / (float) RAND_MAX; /* [0, 1.0] */
    return min + scale * ( max - min );      /* [min, max] */
}

int main(){
    char *name;
    float price;
    int i;
    srand( time(NULL) );

    for(i = 0; i < 10; i++){
        sprintf(name,"Artigo %d", i);
        price = float_rand(0,100);
        insert_item(name,price);
    }
    return 0;
}
