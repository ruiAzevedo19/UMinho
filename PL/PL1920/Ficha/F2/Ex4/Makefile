sql : y.tab.o lex.yy.o
	gcc -o sql y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : sql.y 
	yacc -d sql.y 

lex.yy.c : sql.l y.tab.h 
	flex sql.l

