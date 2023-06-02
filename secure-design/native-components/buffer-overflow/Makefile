
OBJECTS = 
CFLAGS = -g -fno-stack-protector -z execstack -Wall
LDLIBS = 
CC=gcc


all: overwrite_local_vars overwrite_return_addr

overwrite_local_vars: overwrite_local_vars.c
	$(CC) $(CFLAGS) $(LDLIBS) overwrite_local_vars.c -o overwrite_local_vars 

overwrite_return_addr: overwrite_return_addr.c
	$(CC) $(CFLAGS) $(LDLIBS) overwrite_return_addr.c -o overwrite_return_addr 

clean:
	rm overwrite_local_vars overwrite_return_addr
