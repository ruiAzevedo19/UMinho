%{
    int yylex();
    void yyerror(char*);
    #include <stdio.h>
%}

%token TAG TXT OPENSLASH

%%


texto : xml { printf("ok\n"); }

xml : elem
    ;

elem : openTag inner closeTag

inner : TXT inner 
      | elem inner 
      |
      ;

openTag : '<' TAG '>'
        ;

closeTag : OPENSLASH TAG '>'
         ;
%%

int main(){
    yyparse();
    return 0;
}

void yyerror(char *s){
    extern int yylineno;
    extern char* yytext;
    fprintf(stderr, "Linha %d: %s (%s)\n", yylineno, s, yytext);
}

//axioma : '(' axioma ')' axioma 
//       |
//       ; 
