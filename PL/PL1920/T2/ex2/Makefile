E = dir

$E : $E.y $E.fl 
	   yacc $E.y 
	   flex $E.fl
	   cc y.tab.c -o $E

clean:
	rm -f y.tab.c lex.yy.c $E
