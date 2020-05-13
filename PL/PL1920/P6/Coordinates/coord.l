%option noyywrap yylineno 

%{
    #include "y.tab.h"
%}

%%

[ \t\n\r]           ;

\(                  return *yytext;

\)                  return *yytext;

\,                  return *yytext;

(\+|-)?([0-9]|[1-9][0-9]|1[0-9][0-9])(\.[0-9]+)?      { yylval.vreal = atof(yytext) ; return num; }

.                   return ERRO;

%%
