# *****************************************************
# Parametros paara controlo da makefile

CC = gcc
CFLAGS = -std=c11 -Wall -Wextra -pedantic -O2
OBJ_MA = ma.o ma_api.o item.o parser.o dialog.o

# Caminhos para os modulos
PUB_API_SRC = ../PubHeaders/src/
PUB_API_MOD = ../PubHeaders/modules/
SRC 	= src/
MOD     = modules/
# *****************************************************

# Manutencao de artigos #
ma 		 : ${OBJ_MA} 
	       ${CC} ${CFLAGS} -o $@ ${OBJ_MA}
ma.o 	 : $(SRC)ma.c $(MOD)ma_api.h $(PUB_API_MOD)parser.h
	   	   ${CC} ${CFLAGS} -c $(SRC)ma.c
ma_api.o : $(SRC)ma_api.c $(MOD)ma_api.h $(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(SRC)ma_api.c
parser.o : $(PUB_API_SRC)parser.c $(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)parser.c
item.o   : $(PUB_API_SRC)item.c $(PUB_API_MOD)item.h $(PUB_API_MOD)parser.h 
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)item.c 
dialog.o : $(PUB_API_SRC)dialog.c $(PUB_API_MOD)dialog.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)dialog.c
move	 :
		   mv *.o obj
clean :
	rm ma obj/*.o
 
