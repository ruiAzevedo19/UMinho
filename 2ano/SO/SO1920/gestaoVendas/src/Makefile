PROG = argus
DAEMON = argusd

OBJ_DIR = ../obj/
INC_DIR = ../headers/
SRC_DIR = 

CC = gcc
CFLAGS = -Wextra 

OBJS = $(addprefix $(OBJ_DIR),request.o answer.o parser.o )
PROG_OBJS = $(addprefix $(OBJ_DIR),argus.o argusRelay.o)
DAEMON_OBJS = $(addprefix $(OBJ_DIR), argusd.o controller.o daemonRelay.o agr.o)

vpath %.h $(INC_DIR)
vpath %.c $(SRC_DIR)

# --- Make both programs  --- #
all : $(PROG) $(DAEMON)       #
# --------------------------- #

# --- make single programs --- #
p : $(PROG)   # argus          #
d : $(DAEMON) # daemon         #
# ---------------------------- #

# --- make programs ---------------------------- #
$(DAEMON) : $(DAEMON_OBJS) $(OBJS)
	$(CC) $(CFLAGS) -o $@ $(DAEMON_OBJS) $(OBJS) 

$(PROG) : $(PROG_OBJS) $(OBJS) 
	$(CC) $(CFLAGS) -o $@ $(PROG_OBJS) $(OBJS) 
# ---------------------------------------------- #

# --- make tests ------------------------------- # 
testes : 
	gcc -o testBash testBash.c parser.c 
	gcc -o sleep sleep.c 
# ---------------------------------------------- #

# --- Make objs -------------------------------------------------------------- #
$(OBJ_DIR)argus.o : argus.c argusRelay.h 
	$(CC) $(CFLAGS) -o $@ -c $< 

$(OBJ_DIR)argusd.o : argusd.c controller.h request.h daemonRelay.h agr.h
	$(CC) $(CFLAGS) -o $@ -c $< 

$(OBJ_DIR)request.o : request.c request.h
	$(CC) $(CFLAGS) -o $@ -c $< 
	
$(OBJ_DIR)argusRelay.o : argusRelay.c argusRelay.h request.h answer.h parser.h
	$(CC) $(CFLAGS) -o $@ -c $<

$(OBJ_DIR)daemonRelay.o : daemonRelay.c daemonRelay.h parser.h 
	$(CC) $(CFLAGS) -o $@ -c $<

$(OBJ_DIR)answer.o : answer.c answer.h
	$(CC) $(CFLAGS) -o $@ -c $<

$(OBJ_DIR)controller.o : controller.c controller.h parser.h
	$(CC) $(CFLAGS) -o $@ -c $<

$(OBJ_DIR)parser.o : parser.c parser.h
	$(CC) $(CFLAGS) -o $@ -c $<

$(OBJ_DIR)agr.o : agr.c agr.h
	$(CC) $(CFLAGS) -o $@ -c $<
# ---------------------------------------------------------------------------- #

# --- Make w/ debug for each program --- #
dp : CFLAGS += -g  # add -g flag         #
dp : $(PROG)       # make debug argus    #
dd : CFLAGS += -g  # add -g flag         #
dd : $(DAEMON)     # make debug daemon   #
# -------------------------------------- #

# --- Clean all ------------------------------------------------------------------------------------------ #
clean :                                                                                                    #
	rm -f answer_fifo request_fifo $(PROG) $(DAEMON) $(OBJ_DIR)*.o log history idx output testBash sleep  #
# --- Clean argus ---------------------------------------------------------------------------------------- #
cp :                                                                                                       #
	rm -f $(PROG) $(PROG_OBJS)                                                                             #
# --- Clean daemon --------------------------------------------------------------------------------------- #
cd :                                                                                                       #
	rm -f $(DAEMON) $(DAEMON_OBJS) answer_fifo request_fifo                                                #
# --- Clean files ---------------------------------------------------------------------------------------- #
cf :                                                                                                       #
	rm -f log history idx output                                                                           #
# -------------------------------------------------------------------------------------------------------- #

