program =interpretador 

interpretador : y.tab.o lex.yy.o read.o 
	gcc -o interpretador y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : interpretador.y 
	yacc -d interpretador.y 

lex.yy.c : interpretador.l y.tab.h 
	flex interpretador.l


