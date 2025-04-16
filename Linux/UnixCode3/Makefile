CC = gcc
CFLAGS = -I.
SRC = src/myls.c
OBJ = myls
LIBS = error.c

$(OBJ):$(SRC) $(LIBS)
	$(CC) $(SRC) $(LIBS) -o $(OBJ) $(CFLAGS)

clean:
	rm -rf $(OBJ)
