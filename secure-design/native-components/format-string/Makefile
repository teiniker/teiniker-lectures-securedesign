
CFLAGS = -g -fno-stack-protector -z execstack -Wall
LDLIBS = 
CC=gcc


all: fmt_string

fmt_string: fmt_string.c
	$(CC) $(CFLAGS) $(LDLIBS) fmt_string.c -o fmt_string 

clean:
	rm fmt_string
