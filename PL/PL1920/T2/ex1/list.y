%{
    #include <stdio.h>
    int yylex();
    void yyerror(char*);
%}

%union {int n;}
%token INT
%type <n> INT elems elem lista  

%% 

axioma : lista        { printf("Sum = %d\n", $1); }
       | axioma lista { printf("Sum = %d\n", $2); }
       ;

lista : '(' elems ')' { $$ = $2; }
      | '(' ')'       { $$ = 0;  }
      ;

elems : elem           { $$ = $1; }
      | elem ',' elems { $$ = $1 + $3; } 
      ;

elem : INT    { $$ = $1; }
     | lista  { $$ = $1; }
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

