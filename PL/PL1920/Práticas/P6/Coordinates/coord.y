%{
    #include <stdio.h>
    extern int yylex();
    int yyerror();
%}

%union{
    float vreal;
}

%token ERRO num
%type <vreal> num

%%

SeqCoord : SeqCoord Coord 
         | Coord 
         ;

Coord : '(' num ',' num ')' { if( $2 >= -90 && $2 <= 90 && $4 >= -180 && $4 <= 180 )
                                printf("Input válido!\n");
                              else 
                                printf("Input inválido\n");
                            }
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
