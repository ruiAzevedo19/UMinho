#ifndef PROJC_ESTRUTURASAUX_H
#define PROJC_ESTRUTURASAUX_H

typedef struct venda *Venda;

struct venda{
    char* produto;
    double priceUnit;
    int unitCompra;
    char modo;
    char* cliente;
    int mes;
    int filial;
};

#endif
