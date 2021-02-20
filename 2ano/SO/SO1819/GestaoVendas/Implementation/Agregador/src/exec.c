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

int off_set(int dx, int i, int nr_it){
    return dx + i * sizeof(struct sell_record) * (nr_it / 4);
}
int print; // <-----

void concurrent_agr(int dx, int nr_it){
    int fd = open(VENDAS, O_RDWR);
	int fd_agr = open("agr.txt", O_CREAT | O_WRONLY);
    SellRecord sr = malloc(sizeof(struct sell_record));
    dx = 0;
    int off, off1, pid;
    int i, dxs = dx, pids[5][2];
    /* Cria 4 pipes anonimos para enviar informacao aos filhos e um para receber informacao */
    for(i = 0; i < 5; i++)
        pipe(pids[i]);
    /* Fecha os lados de leitura para os pipes dos filhos e o de escrita do pipe de resposta */
 //   for(i = 0; i < 4; i++)
 //       close(pids[i][0]);
 //   close(pids[4][1]);

    lseek(fd,dx,SEEK_SET);
	char *buf = malloc(80);
    for(i = 0; i < 4; i++){
        off = off_set(dxs,i,nr_it);
		off1 = off_set(dxs, i + 1, nr_it);
		pid = fork();
		if( pid > 0 ){
			while( dx < off1 ){
				read(fd,sr,sizeof(struct sell_record));
				//sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
				//write(1,buf,strlen(buf));
				print = write(pids[i][1],sr,sizeof(struct sell_record));
				dx += sizeof(struct sell_record);
				buf[0] = 0;
			}
			close(pids[i][1]);
		}
        else{
			//
			//while( read(pids[i][0], sr,sizeof(struct sell_record)) > 0 ){
				//sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
				//write(1,sr,sizeof(struct sell_record));
			//}
			//close(pids[i][0]);
			dup2(pids[i][0],0);
			dup2(pids[4][1],1);
            execv(AG,NULL);
			_exit(-1);
        }
    }
	/*
	pid = fork();
	if( pid == 0 ){
		while( read(pids[4][0], sr,sizeof(struct sell_record)) > 0 ){
			sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
			write(1,sr,sizeof(struct sell_record));
		}*/
		/*
		dup2(pids[4][0],0);
		dup2(fd_agr,1);
		execv(AG,NULL);
		_exit(-1);
		*/
	//}

	//for(i = 0; i < 5; i++)
	//	wait(NULL);

	close(fd);
	close(fd_agr);
	
}

int main(){
	concurrent_agr(0,16);
	SellRecord sr = malloc(sizeof(struct sell_record));
	char *buf = malloc(80);
	int fd = open("agr.txt",O_RDONLY);

	while( read(fd,sr,sizeof(struct sell_record)) > 0 ){
		sprintf(buf,"%d %d %f\n",sr->item_id, sr->nr_items, sr->amount);
		write(1,buf,strlen(buf));
		buf[0] = 0;
	}
	close(fd);
	return 0;
}