#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include "../../PubHeaders/modules/dialog.h"
#include "../../PubHeaders/modules/item.h"
#include "../../PubHeaders/modules/sell.h"

static const char *VENDAS = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/DataBase/vendas";
static const char *AG = "/Users/ruiazevedo/Desktop/Universidade/SO/GestaoVendas/Implementation/Agregador/src/ag";

int main(){
    int fd = open(VENDAS, O_RDONLY);
    int fdA = open("agregation", O_CREAT | O_RDWR,0777);
    int pid = fork();
    if( pid == 0 ){
        dup2(fd,0);
        dup2(fdA,1);
        execv(AG,NULL);
        _exit(-1);
    }
    else
    {
        wait(NULL);
    }
    SellRecord sr = malloc(sizeof(struct sell_record));
    char * buf = malloc(80);
    while( read(fdA,sr,sizeof(struct sell_record)) > 0 ){
		sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
		write(1,buf,strlen(buf));
		buf[0] = 0;
	}
    return 0;
}