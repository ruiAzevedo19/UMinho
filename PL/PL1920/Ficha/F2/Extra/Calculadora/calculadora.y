%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%union{
    int vint;
}

%token ERRO num
%type <vint> num Exp Termo Fator Calc 

%%

Calc : Exp { printf("exp = %d\n", $1); }
     ;

Exp : Termo         { $$ = $1; }
    | Exp '+' Termo { $$ = $1 + $3; }
    | Exp '-' Termo { $$ = $1 - $3; }
    ;

Termo : Fator           { $$ = $1; }
      | Termo '*' Fator { $$ = $1 * $3; }
      | Termo '/' Fator { if( $3 ) $$ = $1 / $3; else yyerror(); }
      ;

Fator : num          { $$ = $1; }
      | '(' Exp ')'  { $$ = $2; }
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

