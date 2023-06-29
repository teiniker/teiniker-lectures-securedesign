# Buffer Overflow 

A **buffer** is a temporary space in memory used to hold data.

A **buffer overflow** happens when data written to a buffer is larger 
than the size of the buffer, and due to insufficient bounds checking it
overflows and overwrites adjacent memory locations.

To analyze the stack usage, we have to **disable all OS and compiler preventions**.


## Compiler Flags

The following [GCC stack protector options](https://mudongliang.github.io/2016/05/24/stack-protector.html) can be used:

```
$ gcc -g -fno-stack-protector -z execstack -Wall  overwrite_local_vars.c -o overwrite_local_vars
```

* **-fstack-protector**\
    Emit extra code to check for buffer overflows, such as stack smashing
    attacks. This is done by adding a guard variable to functions with
    vulnerable objects.
    This includes functions that call alloca, and functions with buffers
    larger than 8 bytes.
    The guards are initialized when a function is entered and then checked
    when the function exits.
    If a guard check fails, an error message is printed and the program exits.

* **-fno-stack-protector**\
    Disable Stack Protector Check.

* **-z execstack**\
    By default, modern operating systems separate the stack memory from the 
    executable memory to prevent buffer overflow attacks. 
    When using gcc with the `-z execstack` flag, the resulting executable will 
    have an executable stack, allowing the code to be both writable and executable 
    in memory.


## Address Space Layout Randomization (ASLR)

ASLR hinders some types of security attacks by making it more difficult for an 
attacker to predict target addresses.

For example, attackers trying to execute **return-to-libc attacks** must locate
the code to be executed, while other attackers trying to **execute shellcode**
injected on the stack have to find the stack first.

In both cases, the operating system obscures related memory-addresses from 
the attackers. These values have to be guessed, and a mistaken guess is not 
usually recoverable due to the application crashing.

We can find out the address space for the stack with the following command:
```
$ cat /proc/self/maps
...
555555560000-555555581000 rw-p 00000000 00:00 0       [heap]
...
7ffffffdd000-7ffffffff000 rw-p 00000000 00:00 0       [stack]
```

Note that whenever we execute this command, we get different addresses 
for the stack and the heap memory segment.

We **turn off ASLR** by executing the following commands as **root user**:
```
# echo 0 > /proc/sys/kernel/randomize_va_space
```

Now, when we list the memory addresses thei are quite stable.
```
$ cat /proc/self/maps
```


## References
* [Address space layout randomization](https://en.wikipedia.org/wiki/Address_space_layout_randomization)
