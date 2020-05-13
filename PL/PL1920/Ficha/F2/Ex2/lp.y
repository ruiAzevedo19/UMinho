%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO

%%

Lp : Parentesis     
   ;

Parentesis : Single Parentesis { printf("Frase válida!\n"); }
           |
           ;

Single : '(' Parentesis ')'
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

