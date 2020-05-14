%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%union{
    int vint;
}

%token ERRO zero one AND OR NOT 
%type <vint> zero one Exp Termo Fator  

%%

Bool : Exp { printf("exp = %d\n", $1); }
     ;

Exp : Termo         { $$ = $1; }
    | Exp OR Termo  { $$ = $1 || $3; }
    ;

Termo : Fator            { $$ = $1; }
      | Termo AND Fator  { $$ = $1 && $3; }
      ;

Fator : zero        { $$ = $1; } 
      | one         { $$ = $1; }
      | NOT Fator   { $$ = !$2;}
      | '(' Exp ')' { $$ = $2; }
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

