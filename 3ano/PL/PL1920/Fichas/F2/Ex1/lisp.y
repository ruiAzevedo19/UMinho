%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
    int i = 0;
%}

%union{
    int vnum;
    char* vpal;
}

%token ERRO pal num 
%type <vnum> num
%type <vpal> pal

%%

Lisp : Lisp SExp 
     | SExp      { printf("Frase válida!\n"); } 
     ;

SExp : pal 
     | num 
     | '(' SExpLst ')'
     ;

SExpLst : SExp SExpLst 
        |
        ;

%%

int main(){
    yyparse();
    return 0;
}

int yyerror(){
    printf("Erro sintático!\n");
    return 0;
}

