# *****************************************************
# Parametros paara controlo da makefile

CC = gcc
CFLAGS = -std=c11 -Wall -Wextra -pedantic -O2
OBJ_MA = cv.o cv_api.o dialog.o parser.o

# Caminhos para os modulos
PUB_API_SRC = ../PubHeaders/src/
PUB_API_MOD = ../PubHeaders/modules/

SRC 	= src/
MOD     = modules/
# *****************************************************

# Manutencao de artigos #
cv 		 : ${OBJ_MA} 
	       ${CC} ${CFLAGS} -o $@ ${OBJ_MA}
cv.o 	 : $(SRC)cv.c $(MOD)cv_api.h # $(PUB_API_MOD)parser.h
	   	   ${CC} ${CFLAGS} -c $(SRC)cv.c
cv_api.o : $(SRC)cv_api.c $(MOD)cv_api.h #$(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(SRC)cv_api.c
dialog.o : $(PUB_API_SRC)dialog.c $(PUB_API_MOD)dialog.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)dialog.c
parser.o : $(PUB_API_SRC)parser.c $(PUB_API_MOD)parser.h
		   ${CC} ${CFLAGS} -c $(PUB_API_SRC)parser.c
move	 :
		   mv *.o obj
clean :
	rm cv obj/*.o
 
