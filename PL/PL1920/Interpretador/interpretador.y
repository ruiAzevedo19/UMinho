%{
#include <stdio.h>
#include <string.h>

extern int yylex();
extern int yylineno;
extern char* yytext;

int yyerror();
int erroSem(char*);
%}

%union{
    int ivalue;
    char cvalue;
}
%token ERRO HALT PRINT READ SHOW SEP 
%token <ivalue> num 
%token <cvalue> id
%type <ivalue> Exp Termo Fator

%%

Calc
    : { printf("\tpushn 26\nstart\n"); } ListaComandos { printf("stop\n"); }
    ;

ListaComandos
    : ListaComandos Comando 
    | Comando               
    ;  

Comando
    : Print 
	| Read 
	| Show 
    | Atrib
	;

Print
    : PRINT Exp SEP { 
                       printf("\twritei\n"); 
                       printf("\tpushs \"\\n\"\n"); 
                       printf("\twrites\n"); 

                    }
    ;

Read
    : READ id SEP { 
                    printf("\tpushs \"<$> \"\n"); 
                    printf("\twrites\n"); 
                    printf("\tread\n"); 
                    printf("\tatoi\n"); 
                    printf("\tstoreg %d\n", $2 - 'a'); 
                  }
    ;

Show 
   : SHOW SEP { 
                    for(int i=0;i<26;i++) {
                       printf("\tpushs \",\"\n"); 
                       printf("\twrites\n"); 
                       printf("\tpushg %d\nwritei\n",i); 
                    }
              }
    ;

Atrib 
    : id '=' Exp SEP { printf("\tstoreg %d\n", $1 - 'a'); }
    ;

Exp
    : Exp '+' Termo { printf("\tadd\n"); }
    | Exp '-' Termo { printf("\tsub\n"); }
    | Termo 
    ;

Termo 
    : Termo '/' Fator {
        if($3 !=0 ) 
            printf("\tdiv\n");
        else 
            erroSem("Divisão por 0!");
    }
    | Termo '*' Fator { printf("\tmul\n"); }
    | Fator
    ;

Fator 
    : id  { printf("\tpushg %d\n", $1 -'a'); }
    | num { printf("\tpushi %d\n");}
    | '(' Exp ')' 
    ;

%%
int main(){
    yyparse();
    return 0;
}

int erroSem(char *s){
    printf("Erro Semântico na linha: %d: %s\n", yylineno, s);
    return 0;
}

int yyerror(){
    printf("Erro Sintático ou Léxico na linha: %d, com o texto: %s\n", yylineno, yytext);
    return 0;
} 
