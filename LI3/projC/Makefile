APP      = program

OBJ_DIR  = obj
INC_DIR  = headers
SRC_DIR  = src 

CC       = gcc
CFLAGS   = $(shell pkg-config --cflags glib-2.0) #-Wunreachable-code -O2 -Wuninitialized -Wall -Wextra
LIB      = $(shell pkg-config --libs glib-2.0)
LINK     =  -ansi
OBJS     = $(addprefix $(OBJ_DIR)/,main.o parser.o interface.o catalog.o estruturasAux.o clogProduct.o clogClient.o collection.o stats.o controller.o view.o page.o faturacao.o gestao.o heap.o) 

vpath %.h $(INC_DIR)
vpath %.c $(SRC_DIR)

$(APP) : create_stats $(OBJS)
	   $(CC)  $(CFLAGS) -o $(APP) $(OBJS) $(LIB) -lm

create_stats : 
	mkdir -p stats

debug : CFLAGS += -g
debug : $(APP)

$(OBJ_DIR)/main.o : main.c interface.h stats.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/parser.o : parser.c parser.h interface.h catalog.h estruturasAux.h 
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/interface.o : interface.c interface.h clogProduct.h clogClient.h catalog.h stats.h estruturasAux.h faturacao.o gestao.o
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/estruturasAux.o : estruturasAux.c estruturasAux.h 
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/catalog.o : catalog.c catalog.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/clogProduct.o : clogProduct.c clogProduct.h catalog.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@ 

$(OBJ_DIR)/clogClient.o : clogClient.c clogClient.h catalog.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/collection.o : collection.c collection.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/stats.o : stats.c stats.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/controller.o : controller.c controller.h view.h interface.h parser.h page.h collection.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/view.o : view.c view.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/page.o : page.c page.h collection.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/faturacao.o : faturacao.c faturacao.h catalog.h estruturasAux.h collection.h
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/gestao.o : gestao.c gestao.h estruturasAux.h catalog.h 
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

$(OBJ_DIR)/heap.o : heap.c heap.h gestao.h  
	$(CC) $(CFLAGS) $(LINK) -c $< -o $@

remake : clean $(APP)

.PHONY : clean 

clean : 
	  rm -f program $(OBJ_DIR)/*.o 

