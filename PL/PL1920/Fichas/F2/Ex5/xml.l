%option noyywrap

%{
    #include "y.tab.h"
%}

%%

[ \t\n\r]                   ;

\<\?xml[^?]+\?\>            return DEC;

\<                          return OPEN_BRACKETS;

\>                          return CLOSE_BRACKETS;

\<\/                        return OPEN_END_BRACKETS;

[=]                         return *yytext; 

\"[a-zA-Z0-9]+\"            return valor;

[a-zA-Z]+                   return id;

%%

int main(){
	yylex();
	return 0;
}

