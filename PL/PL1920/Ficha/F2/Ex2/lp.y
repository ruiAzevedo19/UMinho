%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO

%%

Parentesis : Parentesis '(' Parentesis ')' { printf("Frase válida!\n"); }    
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

