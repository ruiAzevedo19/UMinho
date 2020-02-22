%{
     extern int yylex();
     extern int yylineno;
     extern char *yytext;
     void yyerror(char*);

     #include <stdlib.h>
     #include <stdio.h>
     #include <string.h>
     #include <ctype.h>


/* Estruturas de dados */
     typedef struct sinonimos{
          char *nome;
          struct sinonimos *next;
     }*Sinonimos;

     typedef struct palavra{
          char *nome;
          char *definicao;
          char *traducao;
          Sinonimos sinonimo;
          int referencia;
     }*Palavra;

     typedef struct dicionario{
          Palavra pal;
          struct dicionario *next;
     }*Dicionario;

/* Prototipos */
     Palavra create_word();
     void add_name(Palavra , char *);
     void add_def(Palavra , char *);
     void add_trd(Palavra , char *);
     void add_sin(Palavra , char *);
     void set_ref(Palavra);
     char *get_trd(char *, int *);
     Dicionario insert_word(Dicionario , Palavra );
     Palavra lookup(char *);

     Dicionario dic = NULL;
     Palavra pal = NULL;


%}

%union{
  char* texto;
}
%token DEF SIN TRD WORD
%type<texto> DEF SIN TRD WORD NOME ARGUMENTO

%%

/* ----------------------------- Gramatica ---------------------------------- */


DICIONARIO : PALAVRAS
           ;
PALAVRAS   : PALAVRAS PALAVRA                {dic = insert_word(dic,pal);}
           |
           ;
PALAVRA    : NOME ARGUMENTOS
           ;
NOME       : WORD                            {pal = create_word() ; add_name(pal,$1);}
           ;
ARGUMENTOS : ARGUMENTOS ARGUMENTO
           |
           ;
ARGUMENTO  : DEF                             {add_def(pal,$1);}
           | TRD                             {add_trd(pal,$1);}
           | SIN                             {add_sin(pal,$1);}
           ;

%%

#include "lex.yy.c"
/* ------------------------------ Codigo ------------------------------------ */

Palavra create_word(){
     Palavra p = malloc(sizeof(struct palavra));
     p->nome = NULL;
     p->definicao  = NULL;
     p->traducao  = NULL;
     p->sinonimo  = NULL;
     p->referencia  = 0;

     return p;
}

/* Adiciona o nome da palavra */
void add_name(Palavra p, char *name){
     p->nome = strdup(name);
}

/* Adiciona a definicacao de uma palavra */
void add_def(Palavra p, char *definicao){
     p->definicao = strdup(definicao);
}

/* Adiciona a traducao em ingles de uma palavra */
void add_trd(Palavra p, char *traducao){
     p->traducao = strdup(traducao);
}

/* Adiciona um sinonimo de um palavra */
void add_sin(Palavra p, char *sinonimo){
     Sinonimos s = malloc(sizeof(struct sinonimos));
     s->nome = strdup(sinonimo);
     s->next = p->sinonimo;
     p->sinonimo  = s;
}

/* Palavra mencionada */
void set_ref(Palavra p){
     p->referencia = 1;
}

/* Adiciona uma palavra ao dicionario */
Dicionario insert_word(Dicionario dic, Palavra p){
     Dicionario new = malloc(sizeof(struct dicionario));
     new->pal = p;
     new->next = dic;
     dic = new;

     return new;
}

char* get_trd(char *p, int *val){
    Dicionario tmp = dic;
    while(tmp){
      if(!strcmp(tmp->pal->nome, p)) {
          if(tmp->pal->referencia)
            *val = 1;
          else{
            *val = 0;
            tmp->pal->referencia = 1;
          }
          return tmp->pal->traducao;
      }
      tmp = tmp->next;
    }
    return NULL;
}

/* --------------------------------- Latex -----------------------------------*/

void initLatex(FILE *f){
     fprintf(f,"\\documentclass{article}\n");
     fprintf(f,"\\usepackage[bottom]{footmisc}\n\n");
     fprintf(f,"\\begin{document}\n");
}

void makeAppendix(FILE *f){
    Dicionario tmp = dic;

    fprintf(f,"\n\\appendix\n");
    fprintf(f,"\\section{Apendice}\n");
    fprintf(f,"\\begin{itemize}\n");

    while(tmp){
        if(tmp->pal->referencia == 1){
            fprintf(f,"\\item %s $\\to$ Def: %s\n",tmp->pal->nome,tmp->pal->definicao);
            tmp->pal->referencia = 0;
        }
        tmp = tmp->next;
    }
    fprintf(f,"\\end{itemize}\n");
    fprintf(f,"\n\\end{document}\n");
}

/* ---------------------------------- Main -----------------------------------*/

void yyerror(char *error){
  fprintf(stderr, "ERROR : %s \n", error);
}

int main(int argc, char **argv){

     char outfile[200];

     parseDic();

     yyin = fopen(argv[1], "r");
     yyparse();
     fclose(yyin);


     parseFiles();

     for(int i = 2; i < argc ; i++){
       yyin = fopen(argv[i], "r");
       if(yyin){

         strcat(outfile,argv[i]);
         strcat(outfile,".tex");
         yyout = fopen(outfile,"w");
         initLatex(yyout);
         yylex();
         makeAppendix(yyout);
         fclose(yyin);
         fclose(yyout);
       }
       memset(outfile,0,200);
     }

     return 0;
}
