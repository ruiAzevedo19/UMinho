#include "../headers/stats.h"
#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <time.h>

struct stats{
    int validation[6];
    double loadTime[3];
    double query_time[11];
    FILE* log;
};

Stats initStats(){
    int i;
    Stats stats = malloc(sizeof(struct stats));
    for(i = 0; i < 3; i++)
        stats->loadTime[i] = 0;
    for(i = 0; i < 6; i++)
        stats->validation[i] = 0;
    for(i = 0; i < 12; i++)
        stats->query_time[i] = 0;
    stats->log = fopen("stats/stats.txt", "a");

    return stats;
}

void destroyStats(Stats stats){
    fclose(stats->log);
    free(stats);
}

void setLoadTime(Stats stats, int type, double load){
    stats->loadTime[type] = load;
}

double getLoadTime(Stats stats, int type){
    return stats->loadTime[type];
}

void setValidation(Stats stats, int type, int validation){
    stats->validation[type] = validation;
}

int getValidation(Stats stats, int type){
    return stats->validation[type];
}

void setQTime(Stats stats, int query, clock_t start, clock_t end){
    stats->query_time[query - 2] = (double)(end - start) / CLOCKS_PER_SEC;
}

double getQTime(Stats stats, int query){
    return stats->query_time[query - 2];
}

void writeLoad(Stats stats){
    char load[500];
    sprintf(load,"\
--- Load Time --------------------\n\
[Clients]              %lf ms \n\
    Valid ------------ %d\n\
    Invalid ---------- %d\n\
[Products]             %lf ms \n\
    Valid ------------ %d\n\
    Invalid ---------- %d\n\
[Sells]                %lf ms \n\
    Valid ------------ %d\n\
    Invalid ---------- %d\n\
----------------------------------\n\n", stats->loadTime[0], stats->validation[0], stats->validation[1], stats->loadTime[1], stats->validation[2], stats->validation[3],stats->loadTime[2],stats->validation[4], stats->validation[5]);
    fwrite(load,strlen(load),1,stats->log);
}

void writeQTime(Stats stats, int query){
    char q[100];
    sprintf(q,"\
--- Query %d ----------------------\n\
Time = %lf\n\
----------------------------------\n\n",query,stats->query_time[query - 2]);
    fwrite(q,strlen(q),1,stats->log);
}

