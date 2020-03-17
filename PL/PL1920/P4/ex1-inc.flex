%option noyywrap yylineno

%{
//  --- defines gerados pelo yacc automaticamente
//    #define INICIO   5
//    #define FIM    6
//    #define Virg   7
//    #define pal    8
//    #define num    9
//    void error();
%}

%%

#.*           {}
[ \n]         {}
[,]           { return(yytext[0]); }
(?i:inicio)   { return(INICIO);      }
(?i:fim)      { return(FIM);       }
[a-zA-Z]+     { return(pal);       }
[0-9]+        { return(num);       }
.             { yyerror("Símbolo desconhecido!");           }

%%

//int main(){
//    int c;
//    while( (c = yylex()) )
//        printf("cod: %d, (%s)\n", c, yytext);
//    return 0;
//}

//void error(){
//    fprintf(stderr, "Linha %d: Simbolo desconhecido (%s)\n", yylineno, yytext);
//}

