#include "../headers/argusRelay.h"
#include "../headers/request.h"
#include "../headers/answer.h"
#include "../headers/parser.h"

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <signal.h>

static const char helpBash[] = "tempo-inactividade segs\n" 
                               "tempo-execucao segs\n"
                               "executar p1 | p2 ... | pn\n"
                               "listar\n"
                               "terminar p\n"
                               "historico\n"
                               "sair\n";

static const char helpCmd[] = "-i segs (tempo-inactividade)\n"
                              "-m segs (tempo-execucao)\n"
                              "-e p1 | p2 ... | pn (executar)\n"
                              "-l (listar)\n"
                              "-t p (terminar)\n"
                              "-r (historico)\n";

static const char prompt[] = "argus<$> ";

/* --- ArgusRelay structure ---------------------------------------------------------- */
struct argusRelay{      /*                                                             */
    int wr_fifo;        /* file descriptor for request named pipe                      */
    int rd_fifo;        /* file descriptor for answer named pipe                       */
};                      /*                                                             */
/* ----------------------------------------------------------------------------------- */

/* --- Construtor & Destructor ------------------------------------------------------- */
ArgusRelay initArgusRelay(int wr_fifo, int rd_fifo){
    ArgusRelay c = malloc(sizeof(struct argusRelay));
    c->wr_fifo = wr_fifo;
    c->rd_fifo = rd_fifo;
    return c;
}

void destroyArgusRelay(ArgusRelay c){
    free(c);
}

/* --- Static ------------------------------------------------------------------------ */

static Request translate(char** argv, int argc){
    char type = 'f';
    char* cmd = 0;
    int value = 0;
    Request r = 0;
    
    if( (!strcmp("-i",argv[0]) || !strcmp("tempo-inactividade",argv[0])) && argc == 2 && (value = atoi(argv[1])) >= 0 )
        type = 'i';
    if( (!strcmp("-m",argv[0]) || !strcmp("tempo-execucao",argv[0])) && argc == 2 && (value = atoi(argv[1])) >= 0 )
        type = 'm';
    if( (!strcmp("-e",argv[0]) || !strcmp("executar",argv[0])) && argc == 2 ){
        type = 'e';
        cmd = strdup(argv[1]);
    }
    if( (!strcmp("-l",argv[0]) || !strcmp("listar",argv[0])) && argc == 1 )
        type = 'l';
    if( (!strcmp("-t",argv[0]) || !strcmp("terminar",argv[0])) && argc == 2 && (value = atoi(argv[1])) >= 0 )
        type = 't';
    if( (!strcmp("-r",argv[0]) || !strcmp("historico",argv[0])) && argc == 1 )
        type = 'r';
    if( (!strcmp("-h",argv[0]) || !strcmp("ajuda",argv[0])) && argc == 1 )
        type = 'h';
    if( (!strcmp("-o",argv[0]) || !strcmp("output",argv[0])) && argc == 2 && (value = atoi(argv[1])) > 0 )
        type = 'o';

    return initRequest(type,cmd,value);
}

static char sendRequest(ArgusRelay c, char** argv, int argc, const char* help){
    Request r = 0;
    ssize_t rd, wr;
    char buffer[1024];

    switch( argc ){
        case 1 :
        case 2 :
        case 3 : r = translate(argv, argc);
                 if( getRequestType(r) == 'f' )
                    perror("Argumento inválido!");
                 else{
                    if( getRequestType(r) == 'h' )
                            printf("%s\n",help);
                    else
                        write(c->wr_fifo,r,requestSize());
                 } 
                break;
        default : perror("Argumento inválido!");         
    }
    return getRequestType(r);
}

static char* cpy(char buf[],int res, int N){
    int i;
    char* c = malloc(sizeof(char) * 512);
    for(i = 0; i < res && i < N; i++)
        c[i] = buf[i];
    c[i] = 0;

    return c;
}

static void receiveAnswer(ArgusRelay c, char type){
    char rd[512] = "init";
    char buf[512] = {0};
    char *out;
    ssize_t r,w;
    int n, res, div;
    char** argv;
    int argc, reads;
    Answer a;
    
    if( type == 'e' ){
        a = createAnswer();
        read(c->rd_fifo,a,answerSize());
        printf("nova tarefa #%d\n",getAnswerOutput(a));
        destroyAnswer(a);
    }
    if( type == 'l' || type == 'r' ){
        r = read(c->rd_fifo,&reads,sizeof(int));
        for(int i = 0; i < reads; i++){
            memset(rd,0,512);
            read(c->rd_fifo,&n,sizeof(int));
            r = read(c->rd_fifo,rd,n);
            printf("%s",rd);
        }
    }
    if( type == 'o' ){
        read(c->rd_fifo,&n,sizeof(int));
        div = n / 512;
        res = n % 512;
        for(int i = 0; i < div; i++){
            read(c->rd_fifo,buf,512);
            out = cpy(buf,512,512);
            printf("%s",out);
            free(out);
        }
        read(c->rd_fifo,buf,res);
        out = cpy(buf,res,512);
        printf("%s",out);
        free(out);
    }
}

/* --- Functionality --------------------------------------------- */

void cmdln(ArgusRelay c, char** argv, int argc){
    Answer a = createAnswer();
    char type;
    ssize_t n;

    type = sendRequest(c,argv,argc,helpCmd);
    receiveAnswer(c,type);
}

void bash(ArgusRelay c){
    char in[1024] = "init";
    char rd[512] = "init";
    char buf[512] = {0};
    ssize_t r,w;
    char** argv;
    int argc, reads;
    char type;

    while( strcmp("sair",in) ){
        write(1,prompt,strlen(prompt));
        r = readln(0,in,128); 
        in[r-1] = 0;
        if( r > -1 && strcmp("sair",in)){
            argv = words(in,&argc);
            type = sendRequest(c,argv,argc,helpBash);
            receiveAnswer(c,type);
            freeWords(argv, argc);
        }
        if( r == -1)
            perror("Erro a ler do stdin");
    }
}




