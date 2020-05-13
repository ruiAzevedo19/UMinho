%option noyywrap yylineno

%{
	#include "y.tab.h"

%}

%%

[ \t\r]         ;

\n              return SEP;

[+*\-/=()]      return *yytext;

(?i:halt)       return HALT;
(?i:print)      return PRINT;
(?i:read)       return READ;
(?i:show)       return SHOW;

[0-9]+          { yylval.ivalue = atoi(yytext); return num; }
[a-z]           { yylval.cvalue = *yytext; return id; }

%%


