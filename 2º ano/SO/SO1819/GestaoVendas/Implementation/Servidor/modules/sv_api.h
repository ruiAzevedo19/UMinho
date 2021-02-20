/*******************************************************************************
*
* NOME DO FICHEIRO :        ma_api.h
*
* DESCRIÇÃO :
*       Estruturas de dados e prototipos das funcoes publicas definidas em ma_api.c
*
* AUTORES :    Etienne Costa   |   Joana Cruz   |   Rui Azevedo
*
*******************************************************************************/

#ifndef SV_API_H
#define SV_API_H

#include "../../PubHeaders/modules/dialog.h"

void answerClient(RequestMsgBox);

void agregate(int dxi, int dxj);

void sell_or_update(RequestMsgBox msg);

void *create_shm(int size);

#endif

/* --------------------------------------------------------------------------------- */
