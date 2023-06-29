#include <stdio.h>
#include <stdlib.h>

#define BUFFER_SIZE 256
#define COMMAND_SIZE 81

int main(int argc, char *argv[]) 
{
    if (argc != 2) 
    {
        printf("Usage: %s <filename>\n", argv[0]);
        return EXIT_FAILURE;
    }

    // Extract the command from command-line argument
    // Vulnerability: Missing input validation for argv[1] !!!
    char command[COMMAND_SIZE];
    snprintf(command, COMMAND_SIZE, "ls -l %s", argv[1]);

   // Open a pipe and execute the command
    FILE *pipe = popen(command, "r");
    if (pipe == NULL) 
    {
        printf("Failed to execute the command.\n");
        return 1;
    }

    // Read and display the output from stdout
    char buffer[BUFFER_SIZE];
    while (fgets(buffer, BUFFER_SIZE, pipe) != NULL) 
    {
        printf("%s", buffer);
    }

    // Close the pipe
    pclose(pipe);

    return EXIT_SUCCESS;
}