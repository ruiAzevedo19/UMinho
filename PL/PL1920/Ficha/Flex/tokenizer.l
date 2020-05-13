%option noyywrap

%{
	// C global variables and lib imports

%}

%%

[0-9]+      { printf("INT "); }

\(          { printf("AP ");  }

\)          { printf("FP ");  }

[\+\*\/-]   { printf("OP ");  } 

\n          { printf("\n");   }

.           ;

%%

int main(){
	yylex();
	return 0;
}

