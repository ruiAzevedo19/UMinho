%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%union{
    float vreal;
    int vint;
}

%token ERRO 
%token string integer floating  
%token unidade peso volume 
%type <vreal> floating  
%type <vint> integer 

%%

Shop : ShopList   { printf("Shopping list ok!\n"); }
     ;

ShopList : Section {printf("Section ok!\n");}
         ;

Section : '.' string ':' Items 
        ;

Items : Item 
      | Items Item
      ;

Item : '-' List 
     ;

List : '(' integer ',' string ',' floating ',' Quantity ')'
    

Quantity : '[' unidade ':' integer  ']'
         | '[' peso    ':' floating ']'   
         | '[' volume  ':' floating ']'

%%

int main(){
    yyparse();
    return 0;
}

int yyerror(){
    printf("Erro sintático!\n");
    return 0;
}

