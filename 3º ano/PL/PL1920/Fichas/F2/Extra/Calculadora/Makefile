program = calculadora 

calculadora : y.tab.o lex.yy.o
	gcc -o calculadora y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : calculadora.y 
	yacc -d calculadora.y 

lex.yy.c : calculadora.l y.tab.h 
	flex calculadora.l

