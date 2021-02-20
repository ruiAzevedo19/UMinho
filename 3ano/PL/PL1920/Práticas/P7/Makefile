program = json

$(program) : y.tab.o lex.yy.o
	gcc -o $(program) y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : $(program).y 
	yacc -d $(program).y 

lex.yy.c : $(program).l y.tab.h 
	flex $(program).l

