lp : y.tab.o lex.yy.o
	gcc -o lp y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : lp.y 
	yacc -d lp.y 

lex.yy.c : lp.l y.tab.h 
	flex lp.l

