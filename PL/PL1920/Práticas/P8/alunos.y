%{
    extern int yylex();
    int yyerror();
    #include <stdio.h>
    #include <string.h>
    int nalunos = 0;
%}

%union{
    char* svalue;
}

%token ERRO string DECL AALUNOS FALUNOS AALUNO FALUNO 
%token AID FID ANOME FNOME ANOTAS FNOTAS ANOTA FNOTA 
%token AUC FUC AVALOR FVALOR

%type <svalue> string Alunos Aluno IdAluno Nome Notas Nota Uc Valor

%%

Turma : DECL AALUNOS Alunos FALUNOS         { printf("%s\n", $3); }
      ;

Alunos : Alunos Aluno       { if($1) asprintf(&$$,"%s\n%s",$1,$2); else asprintf(&$$,"%s",$2); }
       |                    { $$ = 0; }
       ;

Aluno : AALUNO IdAluno Nome ANOTAS Notas FNOTAS FALUNO      { nalunos++; asprintf(&$$,"%s,%s,%s",$2,$3,$5); }
      ;

IdAluno : AID string FID        { asprintf(&$$, "%s", $2); }
        ;

Nome : ANOME string FNOME       { asprintf(&$$, "%s", $2); }
     ;

Notas : Notas Nota              { if($1) asprintf(&$$,"%s,%s",$1,$2); else asprintf(&$$,"%s",$2); }
      |                         { $$ = 0; }
      ;

Nota : ANOTA Uc Valor FNOTA     { asprintf(&$$,"%s,%s",$2,$3); }
     ;

Uc : AUC string FUC            { asprintf(&$$, "%s", $2); }
   ;

Valor : AVALOR string FVALOR   { asprintf(&$$, "%s", $2); }
      ;

%%

int main(){
    yyparse();
    return 0;
}

int yyerror(){
    printf("Erro sintático!\n");
    return 0;
}
