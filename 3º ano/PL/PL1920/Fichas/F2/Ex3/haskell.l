%option noyywrap

%{
    #include "y.tab.h"	
%}

%%

[ \t\r\n]   ;

[\[\].,]    return *yytext;

[0-9]+      { yylval.vnum = atoi(yytext); return num; }

.           return ERRO;

%%

