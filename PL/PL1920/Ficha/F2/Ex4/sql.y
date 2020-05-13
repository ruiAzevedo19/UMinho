%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO
%token SELECT FROM AS WHERE ORDER GROUP BY
%token NOT AND OR 
%token id num string 

%%

SQL : SELECT SCols FROM Tables                  { printf("Frase válida!\n"); } 
    | SELECT SCols FROM Tables Filter           { printf("Frase válida!\n"); }      
    | SELECT SCols FROM Tables Filter Optns     { printf("Frase válida!\n"); }  
    ;

SCols : '*'
      | Ids 
      ;

Ids : id 
    | Ids ',' id 
    ;

Tables : Table
       | Tables ',' Table
       ;

Table : id 
      | id AS id 
      ;

Filter : WHERE '(' Exp ')'
       ;

Optns : ORDER BY Cols 
      | GROUP BY Cols 
      | ORDER BY Cols GROUP BY Cols 
      | GROUP BY Cols ORDER BY Cols 
      ;

Cols : Ids
     ;

Exp : Termo
    | Exp OR Termo 
    ;

Termo : Fator 
      | Termo AND Fator 
      

Fator : id '=' string 
      | id '=' num 
      | NOT Fator 
      | '(' Exp ')'
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

