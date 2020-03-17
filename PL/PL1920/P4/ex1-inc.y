%{
    int yylex();
    void yyerror(char*);
%}

%token INICIO FIM pal num 

%%

Frase : INICIO Lista FIM
      ;

Lista : Elem
      | Elem ',' Lista 
      ;

Elem : pal
     | num
     ;

%%

#include "lex.yy.c"

int main(){
    yyparse();
    return 0;
}

void yyerror(char *s){
    fprintf(stderr, "Linha %d: %s (%s)\n", yylineno, s, yytext);
}
