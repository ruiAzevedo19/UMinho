%option noyywrap

%{
	#include "y.tab.h"
%}

%%

[ \t\r\n]                   ;


[()*,=]                      return *yytext;

(?i:select)                 return SELECT;

(?i:from)                   return FROM;

(?i:as)                     return AS;

(?i:where)                  return WHERE;

(?i:order)                  return ORDER;

(?i:group)                  return GROUP;

(?i:by)                     return BY;

(?i:and)                    return AND;

(?i:or)                     return OR;

(?i:not)                    return NOT;

\"[a-zA-Z]+\"               return string;

[a-zA-Z]+                   return id;

[0-9]+                      return num;

.                           return ERRO;

%%
