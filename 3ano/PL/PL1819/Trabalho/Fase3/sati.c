#include "y.tab.h"

int main(int argc, char* argv[]){

    if(argc < 2){
        printf("Número de argumentos inválido.\n");
        return -1;
    }
    parseDic(argv[1]);
    latex(argc - 1, argv + 1);

    return 0;
}
