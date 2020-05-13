%option noyywrap

%{
	#include "y.tab.h"
%}

%%

[ \t\r\n]                   ;

[<>]                        return SIGNAL;

[()=*,]                     return *yytext;

(?i:select)                 return SELECT;

(?i:from)                   return FROM;

(?i:where)                  return WHERE;

(?i:order)\ (?i:by)         return ORDERBY;

(?i:group)\ (?i:by)         return GROUPBY;

(?i:and)                    return AND;

(?i:or)                     return OR;

\"[a-zA-Z]+\"               return string;

[a-zA-Z]+                   return id;

[0-9]+                      return num;

.                           return ERRO;

%%
