%option noyywrap

%{
    #include "y.tab.h"	
%}

%%

[ \t\r\n]           ;

[()]                { return *yytext; }

[a-zA-Z]+           { yylval.vpal = strdup(yytext); return pal; }

[0-9]+              { yylval.vnum = atoi(yytext); return num; }

.                   { return ERRO; }


%%


