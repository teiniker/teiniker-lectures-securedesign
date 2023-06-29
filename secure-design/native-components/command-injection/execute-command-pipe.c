#include <stdio.h>
#include <stdlib.h>

#define BUFFER_SIZE 256

int main(int argc, char *argv[]) 
{
    if (argc != 2) 
    {
        printf("Usage: %s <command>\n", argv[0]);
        return EXIT_FAILURE;
    }

    // Extract the command from command-line argument
    char *command = argv[1];

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