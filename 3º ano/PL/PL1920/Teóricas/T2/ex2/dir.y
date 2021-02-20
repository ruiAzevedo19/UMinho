%{
    #include <stdio.h>
    int yylex();
    void yyerror(char*);
%}

%union {char* n;}
%token ID
%type <n> ID elems elem arvd  

%% 

fs : arvd    { printf("%s\n", $1); } 
   | fs arvd { printf("%s\n", $2); } 
   ;

arvd : ID '(' elems ')' { asprintf(&$$,"%s<ul>%s</ul>", $1, $3); }
     | ID '(' ')'       { asprintf(&$$,"%s<ul></ul>", $1);   }
     ;

elems : elem           { $$ = $1; } 
      | elem ',' elems { asprintf(&$$,"%s\n%s", $1,$3); }
      ;

elem : ID    { asprintf(&$$,"<li>%s</li>", $1); }
     | arvd  { asprintf(&$$,"<li>%s</li>", $1); }
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

