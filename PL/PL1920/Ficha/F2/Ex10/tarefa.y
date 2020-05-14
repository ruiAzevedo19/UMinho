%{
    #include <stdio.h>
    extern int yylex();
    int yyerror(char*);
%}

%token ERRO 
%token HORA DATA
%token FUNC JOBS PRIORIDADE
%token id string 

%%

Tarefa : ListFunc ListJobs  { printf("Tarefa ok!\n"); }
       ;

ListFunc : FUNC '{' Funcs '}'
         ;

ListJobs : JOBS '{' Jobs '}'
         ;

Funcs : '<' InnerListF '>' 
      | Funcs ',' '<' InnerListF'>'
      ;

Jobs : '<' InnerListJ '>'
     | Jobs ',' '<' InnerListJ '>'
     ;

InnerListF : id ',' string ',' string 
           ;

InnerListJ : id ',' DATA ',' HORA ',' PRIORIDADE ',' string
           ;

%%

#include "lex.yy.c"

int main(){
    yyparse();
    return 0;
}

int yyerror(char* s){
    printf("Erro: %s na linha %d com a frase %s\n", s,yylineno,yytext);
    return 0;
}

