# Static Analysis Using grep

The grep filter **searches a file for a particular pattern of characters**, and displays all lines 
that contain that pattern. The pattern that is searched in the file is referred to as the 
**regular expression** (grep stands for **global search for regular expression and print out**).


## Search for Known-Bad Operations and Types

We can use grep to **quickly search for problematic operations or types** (eg: gets(), printf(),
System.out.println(), etc.) in source directories.

We can try grep in our target project:
```
$ cd StaticAnalysis-Target/
```

First we look for **print statements**. This is usually an indicator of very poor code quality.
```
$ grep -i -n -r "System.out.println(" .
./src/main/java/org/se/lab/BadPractice.java:34:     System.out.println("Strings are equal!");
./src/main/java/org/se/lab/Security.java:15:        System.out.println("File exist!");
```

In **error handling**, we can see the use of **generic exception types** (or even the excessive 
use of checked exceptions such as `SQLException`).
```
$ egrep -i -n -r " Exception|RuntimeException" .
./src/main/java/org/se/lab/ErrorHandling.java:59:   throw new RuntimeException("Some bad things happened!");
```
Another problem is the usage of e.printStackTrace() in production code:
```
$ egrep -i -n -r ".printStackTrace()" .

```


In the context of **cryptography** we can look for **insecure classes or cipher configurations**.
```
$ grep -i -n -r "new Random()" .
./src/main/java/org/se/lab/Security.java:22:        Random r = new Random();
```

In **web applications**, we try to find **places where request parameters are processed**:
```
$ grep -i -n -r "getParameter" .
./src/main/java/org/se/lab/presentation/ControllerServlet.java:25:        String action = request.getParameter("action");        
./src/main/java/org/se/lab/presentation/commands/AddCommand.java:22:	  String firstName = req.getParameter("firstName");
./src/main/java/org/se/lab/presentation/commands/AddCommand.java:23:	  String lastName = req.getParameter("lastName");
./src/main/java/org/se/lab/presentation/commands/AddCommand.java:24:	  String username = req.getParameter("username");
./src/main/java/org/se/lab/presentation/commands/AddCommand.java:25:	  String password = req.getParameter("password");
./src/main/java/org/se/lab/presentation/commands/DeleteCommand.java:22:	  String id = req.getParameter("id");
./src/main/java/org/se/lab/presentation/commands/LoginCommand.java:20:	  String username = req.getParameter("username");
./src/main/java/org/se/lab/presentation/commands/LoginCommand.java:21:	  String password = req.getParameter("password");
```
```
$ grep -i -n -r "getRequestParameter" .
```


## References

* [grep command in Unix/Linux](https://www.geeksforgeeks.org/grep-command-in-unixlinux/)

*Egon Teiniker, 2016-2026, GPL v3.0*


