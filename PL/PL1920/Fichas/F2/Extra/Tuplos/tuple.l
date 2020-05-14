%option noyywrap

%{
    #include "y.tab.h"	
%}

%%

[ \t\n\r]   ;

[(),]       return *yytext;

[0-9]+      return num;

.           return ERRO;

%%

