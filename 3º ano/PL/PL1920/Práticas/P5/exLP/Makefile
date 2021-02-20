pl: lp.fl lp.y
	flex lp.fl 
	yacc -d lp.y
	cc y.tab.c lex.yy.c -o lp 

clean: 
	rm -f lp lex.yy.c y.tab.c y.tab.h
