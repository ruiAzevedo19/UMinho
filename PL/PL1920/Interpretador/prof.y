%{
#include <stdio.h>
#include <string.h>
#include "ler.h"

extern int yylex();
extern int yylineno;
extern char* yytext;

int tabSimb[26] = {0};
int yyerror();
int erroSem(char*);
%}

%union{
    int ivalue;
    char cvalue;
}
%token ERRO HALT PRINT READ SHOW SEP num id
%type <ivalue> num Exp Termo Fator
%type <cvalue> id
%%

Calc
    : ListaComandos
    ;

ListaComandos
    : ListaComandos Comando { printf("Reconheci um comando.\n"); }
    | Comando { printf("Reconheci um comando.\n"); }
    ;  

Comando
    : Print 
	| Read 
	| Show 
	| Halt 
    | Atrib
	;

Print
    : PRINT Exp SEP { printf(">> %d\n", $2); }
    ;

Read
    : READ id SEP {
        tabSimb[$2-'a'] = readInt();
        printf(">> %c: %d\n", $2, tabSimb[$2-'a']);
    }
    ;

Show 
   : SHOW SEP {
        for(int i=0;i<26;i++)
            if(tabSimb[i]!=0) printf(">> %c: %d\n", i+'a', tabSimb[i]);
    }
    ;

Halt
    : HALT SEP { printf(">> Até à próxima!\n"); return 0;}
    ;

Atrib 
    : id '=' Exp SEP { 
        tabSimb[$1-'a'] = $3; 
        printf(">> %c: %d\n", $1, $3);
    }
    ;

Exp
    : Exp '+' Termo {$$ = $1+$3;}
    | Exp '-' Termo {$$ = $1-$3;}
    | Termo {$$ = $1;}
    ;

Termo 
    : Termo '/' Fator {
        if($3 !=0 ) 
            $$ = $1 / $3;
        else 
            erroSem("Divisão por 0!");
    }
    | Termo '*' Fator {$$ = $1*$3;}
    | Fator {$$ = $1;}
    ;

Fator 
    : id {$$ = tabSimb[$1-'a'];}
    | num {$$ = $1;}
    | '(' Exp ')' {$$ = $2;}
    ;

%%
int main(){
    yyparse();
    return 0;
}

int erroSem(char *s){
    printf("Erro Semântico na linha: %d: %s\n", yylineno, s);
    return 0;
}

int yyerror(){
    printf("Erro Sintático ou Léxico na linha: %d, com o texto: %s\n", yylineno, yytext);
    return 0;
} 
