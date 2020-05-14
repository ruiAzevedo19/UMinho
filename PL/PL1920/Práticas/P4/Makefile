testaAL: ex1.flex
	flex ex1.flex
	cc -o testaAL lex.yy.c

ex1-inc: ex1-inc.flex ex1-inc.y
	flex ex1-inc.flex
	yacc ex1-inc.y
	cc y.tab.c -o ex1-inc
