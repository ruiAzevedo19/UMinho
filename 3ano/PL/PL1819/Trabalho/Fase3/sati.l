%option noyywrap
%option yylineno

%{

#include "y.tab.h"

int flag;
char *traducao;
void underlineRef(char *, char *);
char *get_trd(char *, int *);
void underline(char *pt);
%}

STRING ([a-zA-Z0-9]|[áàâãéèêíóôõúçñÁÀÂÃÉÈÍÓÔÕÚÇ,-.])

%x DIC FILES def sin trd word

%%
<DIC>{
({STRING}|\ )+\{                                   {yytext[yyleng-1]='\0';
                                              yylval.texto = strdup(yytext);
                                              BEGIN (word); return WORD;}

<word>\}                                      {BEGIN DIC;}

<word>\n                                      {;}

<word>[\ \t]*def:[\ \t]*\"                    {BEGIN (def);}

<def>\"                                       {BEGIN (word);}

<def>({STRING}|\ )+                               {yylval.texto = strdup(yytext);
                                              return DEF;}

<word>[\ \t]*sin:[\ \t]*\"                    {BEGIN (sin);}

<sin>\"                                       {BEGIN (word);}

<sin>\n                                       {;}

<sin>({STRING}|\ )+                                {yylval.texto = strdup(yytext);
                                              return SIN;}

<word>[\ \t]*trd:[\ \t]*\"                    {BEGIN (trd);}

<trd>\"                                       {BEGIN (word);}

<trd>\n                                       {;}

<trd>({STRING}|\ )+                               {yylval.texto = strdup(yytext);
                                              return TRD;}

}
<FILES>{
{STRING}+               { traducao = get_trd(yytext, &flag);
                          if(traducao){
                              if( !flag )
                                underlineRef(yytext,traducao);
                              else 
                                underline(yytext);

                          }
                          else{
                               fprintf(yyout,"%s",yytext);
                          }
                        }
<*>.|\n                 {fprintf(yyout,"%s",yytext);}
}

%%


void parseDic(){
    BEGIN DIC;
}

void parseFiles(){
    BEGIN FILES;
}

void underlineRef(char *pt, char *en){
      fprintf(yyout,"\\underline{%s}\\footnote{%s}",pt,en);
}

void underline(char *pt){
    fprintf(yyout,"\\underline{%s}",pt);
}
