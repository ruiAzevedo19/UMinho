%option noyywrap

%{
	#include "y.tab.h"
%}

%%

[ \t\r\n]           ;

[()\[\].\-:,]        return *yytext;

unidade             return unidade;

peso                return peso;

volume              return volume;

[a-zA-Z]+           return string;

[0-9]+              { yylval.vint = atoi(yytext); return integer; }

[0-9]+\.[0-9]+      { yylval.vreal = atof(yytext); return floating; }

.                   return ERRO;

%%

