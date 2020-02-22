CC=gcc

install: sati.l sati.y
	flex sati.l
	yacc -d -v sati.y
	$(CC) -o sati y.tab.c

.PHONY: clean

clean:
	rm sati y.tab.c y.tab.h lex.yy.c
