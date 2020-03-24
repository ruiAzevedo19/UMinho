list : list.y list.fl 
	   yacc list.y 
	   flex list.fl
	   cc y.tab.c -o list

clean:
	rm -f y.tab.c lex.yy.c list
