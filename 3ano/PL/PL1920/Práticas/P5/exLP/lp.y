%{
    int yylex();
    void yyerror(char*);
    #include <stdio.h>
%}

%%

texto : lp { printf("ok\n"); }


lp : bpe lp     // bpe* (0..n)
   |
   ;

bpe : '(' lp ')'
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
