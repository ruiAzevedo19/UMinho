#include "../headers/clogClient.h"
#include "../headers/catalog.h"

#include <stdlib.h>
#include <glib.h>

struct clogClient{
    Catalog clients;
};

ClogClient initClogClient(int depth, int range, void* compare_fun, void* free_key_fun, void* free_value_fun){
    ClogClient p = malloc(sizeof(struct clogClient));
    p->clients = initCatalog(depth,range,compare_fun,free_key_fun,free_value_fun);
    return p;
}

void destroyClogClient(ClogClient c){
    destroyCatalog(c->clients);
    free(c);
}

void addClient(ClogClient c, void* key){
    addKeyValue(c->clients,key,NULL);
}

int containsClient(ClogClient c, void* key){
    return containsKey(c->clients,key);
}

GTree* cloneClientClog(ClogClient c){
    return cloneClog(c->clients);
}

void traverseClogClients(ClogClient c, void* traverse_func, void* data){
    traverseCatalog(c->clients,traverse_func,data);
}
