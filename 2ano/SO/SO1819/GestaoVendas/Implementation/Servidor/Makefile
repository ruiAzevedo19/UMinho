# *****************************************************
# Parametros paara controlo da makefile

CC = gcc
CFLAGS = -std=c11 -Wall -Wextra -pedantic -O2
OBJ_MA = sv.o sv_api.o dialog.o item.o parser.o sell.o

# Caminhos para os modulos
PUB_API_SRC = ../PubHeaders/src/
PUB_API_MOD = ../PubHeaders/modules/

SRC 	= src/
MOD     = modules/
# *****************************************************

# Manutencao de artigos #
sv 		 : ${OBJ_MA} 
	       ${CC} ${CFLAGS} -o $@ ${OBJ_MA}
sv.o 	 : $(SRC)sv.c $(MOD)sv_api.h # $(PUB_API_MOD)parser.h
	   	   ${CC} ${CFLAGS} -c $(SRC)sv.c
sv_api.o : $(SRC)sv_api.c $(MOD)sv_api.h #$(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(SRC)sv_api.c
dialog.o : $(PUB_API_SRC)dialog.c $(PUB_API_MOD)dialog.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)dialog.c
item.o   : $(PUB_API_SRC)item.c $(PUB_API_MOD)item.h $(PUB_API_MOD)parser.h 
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)item.c 
parser.o : $(PUB_API_SRC)parser.c $(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)parser.c
sell.o   : $(PUB_API_SRC)sell.c $(PUB_API_MOD)item.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)sell.c
move	 :
		   mv *.o obj
clean :
	rm cv obj/*.o
 
