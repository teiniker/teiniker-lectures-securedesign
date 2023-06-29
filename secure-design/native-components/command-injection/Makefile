
CFLAGS = -g -Wall
LDLIBS = 
CC=gcc


all: execute-command execute-command-pipe command-injection

execute-command: execute-command.c
	$(CC) $(CFLAGS) $(LDLIBS) execute-command.c -o execute-command 

execute-command-pipe: execute-command-pipe.c
	$(CC) $(CFLAGS) $(LDLIBS) execute-command-pipe.c -o execute-command-pipe 

command-injection: command-injection.c
	$(CC) $(CFLAGS) $(LDLIBS) command-injection.c -o command-injection 

clean:
	rm execute-command execute-command-pipe command-injection
