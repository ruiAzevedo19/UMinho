%{
    #include <stdio.h>
    int yylex();
    void yyerror(char*);
%}

%union {int n;}
%token INT
%type <n> INT ints 

%% 

lista : '(' ints ')' { printf("Sum = %d\n", $2); return 0; }
      ;

ints : INT              {$$ = $1;}
     | INT ',' ints     {$$ = $1 + $3;}
     ;

%%

#include "lex.yy.c"

void yyerror(char *s){
    fprintf(stderr, "Linha %d: %s (%s)\n", yylineno, s, yytext);
}

int main(){
    yyparse();
    return 0;
}

