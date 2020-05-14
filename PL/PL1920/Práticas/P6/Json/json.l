%option noyywrap 

%{
    #include "y.tab.h"
%}

%% 

[ \t\n\r]       ;

[{}\[\],:]      return *yytext;

true            return TRUE;

false           return FALSE;

null            return NULLVALUE;

(\+|-)?[0-9]+(\.[0-9]+)?([eE][\+-][0-9]+)?      { yylval.fvalue = atof(yytext) ; return num; }

\"[^"]*\"       { yytext[yyleng - 1] = 0; yylval.svalue = strdup(yytext + 1) ; return string; }

. return        ERRO;

%%
