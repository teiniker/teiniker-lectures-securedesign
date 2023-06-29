#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) 
{
    if (argc != 2) 
    {
        printf("Usage: %s <command>\n", argv[0]);
        return EXIT_FAILURE;
    }

    // Extract the command from command-line argument
    char *command = argv[1];

    // Execute the command using system()
    int status = system(command);

    if (status == -1) 
    {
        printf("Failed to execute the command.\n");
    } 
    else 
    {
        printf("Command executed successfully.\n");
    }

    return EXIT_SUCCESS;
}