program = boolean 

boolean : y.tab.o lex.yy.o
	gcc -o boolean y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : boolean.y 
	yacc -d boolean.y 

lex.yy.c : boolean.l y.tab.h 
	flex boolean.l

