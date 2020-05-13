%option noyywrap

%{
    float saldo = 0;
    int euros = 0;
    int centimos = 0;

    char* moedas[8] = {"1c","2c","5c","10c","20c","50c","1e","2e"};
    char* invalidCoins[100];
    int inv = 0;

    int valideCoin(char* coin){
        int v = 0;

        for(int i = 0; !v && i < 8; i++)
            if( !strcmp(coin,moedas[i]) )
                v = 1;
        return v;
    }

    void freeInvalidCoins(){
        for(int i = 0; i < inv; i++)
            free(invalidCoins[i]);
    }

    void coinsMessage(){
        printf("maq: \"");
        for(int i = 0; i < inv; i++)
            printf("%s ", invalidCoins[i]);
        printf("- moedas inválidas; saldo = %de%dc\"\n",euros,centimos);
    }
    
    void updateCoins(float s){
        saldo -= s;

        int intPart = (int)s;
        double floatPart = s - intPart;

        euros -= intPart;
        centimos -= (int)(floatPart * 100);
    }

    void change(){
        int coins[8];
        memset(coins,0,sizeof(int) * 8);
        int i = 0, j = 2, d, d1, d2, num;
        /* euros change */
        coins[0] = euros / 2;
        coins[1] = euros % 2;
        /* cents change */
        while(i < 2){
            if( !i )
                d = centimos / 10;
            else
                d = centimos % 10;
            if( d ){
                if( d >= 5 ){
                    coins[j] = 1;
                    d -= 5;
                }
                coins[j + 1] = d / 2;
                coins[j + 2] = d % 2;
            }
            j = 5;
            i++;
        }
        if( coins[0] ) printf("%dx2e ",  coins[0]);
        if( coins[1] ) printf("%dx1e ",  coins[1]);
        if( coins[2] ) printf("%dx50c ", coins[2]);
        if( coins[3] ) printf("%dx20c ", coins[3]);
        if( coins[4] ) printf("%dx10c ", coins[4]);
        if( coins[5] ) printf("%dx5c ",  coins[5]);
        if( coins[6] ) printf("%dx2c ",  coins[6]);
        if( coins[7] ) printf("%dx1c ",  coins[7]);
    }

%}

%x LEVANTAR POUSAR MOEDA T 

%%

LEVANTAR            { printf("maq: \"Introduza moedas\"\n"); BEGIN LEVANTAR; }

<LEVANTAR>MOEDA     { BEGIN MOEDA; }

<MOEDA>[0-9]+e      { 
                        if( valideCoin(yytext) ){
                            yytext[yyleng - 1] = 0; 
                            saldo += atoi(yytext);
                            euros += atoi(yytext);
                        }
                        else
                            invalidCoins[inv++] = strdup(yytext); 
                    }

<MOEDA>[0-9]+c      { 
                        if( valideCoin(yytext) ){
                            yytext[yyleng - 1] = 0; 
                            saldo += atoi(yytext) / 100; 
                            centimos += atoi(yytext);
                        }
                        else
                            invalidCoins[inv++] = strdup(yytext);
                    }

<MOEDA>\.           { coinsMessage(); freeInvalidCoins(); inv = 0; BEGIN LEVANTAR; }

<LEVANTAR>T=        { BEGIN T; }

<T>00[0-9]+         { 
                        if( yyleng != 12 ){
                            printf("maq: \"Número inválido\"\n");
                        }
                        else if( saldo < 1.5 ){ 
                            printf("maq: \"Saldo insuficiente\"\n");
                        }
                        else{
                            updateCoins(1.5);
                            printf("maq: \"saldo = %de%dc\"\n",euros,centimos);
                        }
                        BEGIN LEVANTAR;
                    }

<T>(601|641).*      { printf("maq: \"Os números vermelhos estão proibídos\"\n"); }

<T>2[0-9]+          { 
                        if( yyleng != 9 )
                            printf("maq: \"Número inválido\"\n");
                        else if( saldo - 0.25 < 0 ) 
                                printf("maq: \"Saldo insuficiente\"\n");
                             else{
                                updateCoins(0.25);
                                printf("maq: \"saldo = %de%dc\"\n",euros,centimos);
                             }
                        BEGIN LEVANTAR; 
                    }

<T>800[0-9]+        { if( yyleng != 9 ) printf("maq: \"Número inválido\"\n"); BEGIN LEVANTAR; }

<T>808[0-9]+        { 
                        if( yyleng != 9 )
                            printf("maq: \"Número inválido\"\n");
                        else if( saldo - 0.10 < 0 ) 
                                printf("maq: \"Saldo insuficiente\"\n");
                             else{ 
                                updateCoins(0.10);
                                printf("maq: \"saldo = %de%dc\"\n",euros,centimos);
                             }
                        BEGIN LEVANTAR; 
                    }

<T>\n               { BEGIN LEVANTAR; }

<LEVANTAR>POUSAR    { printf("maq: \"saldo = %de%dc; ",euros,centimos); printf("troco = "); change(); printf("; Volte sempre!\"\n"); }

<*>.|\n             { ; }

%%

int main(){
	yylex();
	return 0;
}

