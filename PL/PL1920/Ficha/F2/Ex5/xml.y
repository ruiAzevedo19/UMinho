%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%token ERRO 
%token DEC OPEN_BRACKETS CLOSE_BRACKETS OPEN_END_BRACKETS
%token id PCDATA valor

%%

XML : DEC PageContent
    ;

PageContent : OPEN_BRACKETS id CLOSE_BRACKETS Content OPEN_END_BRACKETS id CLOSE_BRACKETS 
            ;

Content : 
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

