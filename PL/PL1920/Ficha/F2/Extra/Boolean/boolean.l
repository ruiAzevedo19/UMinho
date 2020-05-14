%option noyywrap

%{
	#include "y.tab.h"

%}

%%

[ \t\r\n]   ;

!           return NOT;

&&          return AND;

\|\|        return OR;

0           { yylval.vint = 0; return zero; }

1           { yylval.vint = 1; return one; }

.           return ERRO;

%%

