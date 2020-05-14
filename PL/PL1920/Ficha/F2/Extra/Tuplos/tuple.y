%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO num

%%

Z : Tuples  { printf("Tuple ok!\n"); }
  ;

Tuples : Tuple 
       | Tuples Tuple 

Tuple : '(' ')'
      | '(' Inner ')'
      ; 

Inner : num 
      | Inner ',' num
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

