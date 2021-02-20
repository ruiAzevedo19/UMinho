#include "../headers/interface.h"
#include "../headers/controller.h"
#define FILES_PATH "db/"
#include "../headers/catalog.h"
#include <stdlib.h>

int main(){
    int isRunning = 1;

    SGV sgv = initSGV();
    sgv = controller(sgv);
    destroySGV(sgv);

    return 0;
}
