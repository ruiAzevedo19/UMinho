xml: xml.fl xml.y
	flex xml.fl 
	yacc -d xml.y
	cc y.tab.c lex.yy.c -o xml 

clean: 
	rm -f xml lex.yy.c y.tab.c y.tab.h
