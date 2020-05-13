haskell : y.tab.o lex.yy.o
	gcc -o haskell y.tab.o lex.yy.o -ll

y.tab.o : y.tab.c 
	gcc -c y.tab.c 

lex.yy.o : lex.yy.c 
	gcc -c lex.yy.c 

y.tab.c y.tab.h : haskell.y 
	yacc -d haskell.y 

lex.yy.c : haskell.l y.tab.h 
	flex haskell.l

