%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%union{
    float vnum;
}

%token ERRO num
%type <vnum> num

%%

Haskell : Lists Haskell  { printf("Frase válida!\n"); }
        | Lists          { printf("Frase válida!\n"); }
        ;

Lists : '[' ']' 
      | '[' List ']'
      ;

List : Terminal
     | List ',' Terminal
     ;

Terminal : num 
         | num '.''.' num
         | Lists
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

