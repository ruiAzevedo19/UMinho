%option noyywrap

%{
    #include "y.tab.h"	

%}

%%

[ \t\r\n]   ;

[()]        return *yytext;

.           return ERRO;

%%


