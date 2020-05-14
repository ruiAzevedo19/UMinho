%{
    #include "y.tab.h"
%}

%option yylineno
%x conteudo

%%
[ \t\n\r] ;

\<\?xml[^?]+\?\>  return DECL;

\<alunos\>        return AALUNOS;
\<\/alunos\>      return FALUNOS;
\<aluno\>         return AALUNO;
\<\/aluno\>       return FALUNO;
\<id\>            { BEGIN conteudo; return AID; }
\<\/id\>          return FID;
\<nome\>          { BEGIN conteudo; return ANOME; }
\<\/nome\>        return FNOME;
\<notas\>         return ANOTAS;
\<\/notas\>       return FNOTAS;
\<nota\>          return ANOTA;
\<\/nota\>        return FNOTA;
\<uc\>            { BEGIN conteudo; return AUC; }
\<\/uc\>          return FUC;
\<valor\>         { BEGIN conteudo; return AVALOR; }
\<\/valor\>       return FVALOR;

<conteudo>[^<]+   { BEGIN 0; yylval.svalue = strdup(yytext); return string; }

.                 return ERRO;
