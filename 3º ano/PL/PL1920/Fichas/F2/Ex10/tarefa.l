%option noyywrap yylineno

%{
	#include "y.tab.h"
%}

%%

[\{\}\<\>\,]                        return *yytext;

([0-1][0-9]|2[0-3]):[0-5][0-9]      return HORA;

[0-9]{1,2}\/[0-9]{1,2}              return DATA;

(urgente|normal|baixa)              return PRIORIDADE;

Func                                return FUNC;

Jobs                                return JOBS;

[0-9]+                              return id;

([a-zA-Z]+\ *)+                     return string;

[ \t\r\n]                           ;

%%

