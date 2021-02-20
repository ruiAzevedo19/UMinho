CC=gcc
FLAGS= -Wall

all: cp cat cat2 nl 

cp:
	$(CC) $(FLAGS) -o cp cp.c

cat: 
	$(CC) $(FLAGS) -o cat cat.c

cat2: 
	$(CC) $(FLAGS) -o cat2 cat_v2.c readln.c

nl:
	$(CC) $(FLAGS) -o nl nl.c readln.c

clean:
	rm cp cat cat2 nl

