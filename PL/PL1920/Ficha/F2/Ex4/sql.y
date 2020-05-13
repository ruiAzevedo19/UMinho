%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO
%token SELECT FROM WHERE ORDERBY GROUPBY 
%token SIGNAL AND OR 
%token id num string 

%%

SQL : Sentence 
    ;

Sentence : SELECT ListField FROM ListTable                         { printf("Frase Válida!\n"); }
         | SELECT ListField FROM ListTable WHERE ListCond          { printf("Frase Válida!\n"); }
         | SELECT ListField FROM ListTable WHERE ListCond Arrange  { printf("Frase Válida!\n"); }
         ;

ListField : '*'
          | Fields
          ;

ListTable : Fields
          ;

Arrange : ORDERBY Fields
        | GROUPBY Fields 
        | ORDERBY Fields GROUPBY Fields
        | GROUPBY Fields ORDERBY Fields
        ;

ListCond : Term
         | Term AND '(' ListCond ')'
         | '(' ListCond ')' AND Term 
         | Term OR  '(' ListCond ')'
         | '(' ListCond ')' OR  Term
         | '(' ListCond ')' AND '(' ListCond')'
         | '(' ListCond ')'
         ;

Fields : id 
       | Fields ',' id 
       ;

Term : id SIGNAL num 
     | id '=' num 
     | id '=' string
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

