%{
    extern int yylex();
    int yyerror();
    #include <stdio.h>
    #include <string.h>
%}

%union{
    float fvalue;
    char* svalue;
}

%token ERRO num string TRUE FALSE NULLVALUE
%type <fvalue> num
%type <svalue> string Obj SeqPair Pair Array Elems Value

%%

Json : Value { printf("%s\n",$1); }
     ;

Obj : '{' '}'           { $$ = strdup("<objeto/>"); }
    | '{' SeqPair '}'   { asprintf(&$$, "<objeto>%s</objeto>",$2); }
    ;

SeqPair : SeqPair ',' Pair  { asprintf(&$$,"%s\n%s",$1,$3); }
        | Pair              { $$ = strdup($1); }
        ;

Pair : string ':' Value     { asprintf(&$$,"<%s>%s<%s/>",$1,$3,$1); }
     ;

Array : '[' ']'         { asprintf(&$$,"<lista/>"); }
      | '[' Elems ']'   { asprintf(&$$,"<lista>%s<lista/>",$2); }
      ;

Elems : Elems ',' Value  { $$ = strdup("<item/>"); }
      | Value            { asprintf(&$$, "<item>%s</item>",$1); } 
      ;

Value : string      { asprintf(&$$,"<item>%s</item>",$1); }
      | num         { asprintf(&$$,"<item>%f</item>",$1); }
      | Obj         { asprintf(&$$,"%s",$1); }
      | Array       { asprintf(&$$,"%s",$1); }
      | TRUE        { asprintf(&$$,"<item>true</item>"); }
      | FALSE       { asprintf(&$$,"<item>false</item>"); } 
      | NULLVALUE   { asprintf(&$$,"<item>null</item>"); }
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
