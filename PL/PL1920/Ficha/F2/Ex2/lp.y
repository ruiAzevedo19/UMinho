%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO

%%

Z : Parentesis  { printf("Frase válida!\n"); }    
  ;

Parentesis : '(' Parentesis ')' Parentesis 
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

