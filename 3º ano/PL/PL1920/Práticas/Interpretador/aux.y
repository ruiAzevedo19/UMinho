%{
    #include <string.h>
    #include <stdio.h>
    #include "read.h"

    extern int yylex();
    extern int yylineno;
    extern char* yytext;

    int yyerror();
    int erroSem(char* );
    int tabSimb[26] = {0};
%}

%union{
    int ivalue;
    char cvalue;
}

%token ERRO SEP HALT PRINT READ SHOW num id 
%type <ivalue> num Exp Termo Fator 
%type <cvalue> id

%%

Calc : ListaComandos
     ;

ListaComandos : ListaComandos Comando 
              | Comando     
              ;

Comando : Print 
        | Read 
        | Show 
        | Halt 
        | Atrib
        ;

Print : PRINT Exp SEP     { printf(">> %d\n", $2); }
      ;

Read : READ id SEP        { tabSimb[$1 - 'a'] = readInt(); printf(">> %c: %d\n", ... , ...); }
     ;

Show : SHOW SEP           { for(int i = 0; i < 26; i++) 
                                if(tabSimb[i]) 
                                    printf(">> %c: %d\n", i + 'a', tabSimb[i - 'a']);
                          }
     ;

Halt : HALT SEP           { printf(">> Até à próxima\n"); return 0;}
     ;

Atrib : id '=' Exp SEP    { tabSimb[$1-'a'] = $3; printf(">> %c: %d\n", $1, $3); } 
      ;

Exp : Exp '+' Termo       { $$ = $1 + $3; }
    | Exp '-' Termo       { $$ = $1 - $3; }
    | Termo               { $$ = $1; }
    ;

Termo : Termo '/' Fator   { $3 ? $$ = $1 / $3 : erroSem("Divisão por zero"); }
      | Termo '*' Fator   { $$ = $1 * $3; }
      | Fator             { $$ = $1; } 
      ;

Fator : id          { $$ = tabSimb[$1 - 'a']; }
      | num         { $$ = $1; } 
      | '(' Exp ')' { $$ = $2; }
      ;

%%

int main(){
    yyparse();
    return 0;
}

int erroSem(char* s){
    printf("Erro semântico ou léxico na linha %d, com o texto %s!\n", yylineno, s);
    return 0;
}

int yyerror(){
    printf("Erro sintático ou léxico na linha %d, com o texto %s!\n", yylineno, yytext);
    return 0;
}

