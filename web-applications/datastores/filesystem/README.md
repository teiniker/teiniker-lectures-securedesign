# File I/O 

File input and output refers to the process of reading, writing, or otherwise managing files during operation
of a web application.

All file I/O requires special security handling precautions for save use.

## Path Traversal (File Path Injection)

The goal of a path traversal attack is to gain access to resources that should not
be accessible.

The classic definition involves **reading** resources that are outside of the web application
folder, but it can actually be equally dangerous to read or **write** files inside and outside the 
web application folder.

The key indicator of a file path injection vulnerability is an application that accepts `../` as 
part of a URL or filename parameter.

If a web application is running as the root user, there is almost no limit to what data could
be exfiltrated from a server.
The `/etc/shadow` file containing a server's password hashes or configuration files (like 
`standalone.xml`) containing cretentials to a database are just some of the things that 
attackers would love to get their hands on.

The key to defending against file path injection is to first **properly canonicalize**, and then
**validate**, any given file path.

_Example_: Canonicalize and verify path
```Java
    File f = new File(BASE_DIRECTORY, "../../se/lab/FileManager.java");
    File baseDir = new File(BASE_DIRECTORY);    
    String basePath = baseDir.getCanonicalPath();
    Assert.assertFalse(f.getCanonicalPath().startsWith(basePath));
```


## Null Byte Injection

Null byte injection occurs when untrusted data somehow corrupt a file I/O operation,
allowing the attacker to bypass input validation of a filename.

Many Java operations are actually wrappers around native C functions.
C interprets the null byte as a control character that means "stop processing this string".

When out-of-date versions of the Java Runtime Environment receive a null byte as part 
of a file path operation, validation can fail.

_Example_: Null byte in a crafted file path
```
    "../../../../etc/shadow%00.jpg"
```

This vulnerability was **fixed in Java SE 7 Update 40** and **Java SE 8** by explicitly checking for
the presence of a null byte in many file operations. 

## Not Properly Closing Resources
It is important to properly close file handlers to prevent a denial of service (DoS).

The problem is that file I/O operations can throw lots of exceptions.
If an exception had occured anywhere in a method it could short-circuit the call to close
the stream.

In order to fix this issue, we need to make sure that we close any open resource in a **finally block** 
to ensure that those calls get executed regardless of the outcome of the `try` block.

Since Java 7, we can also use **try-with-resources** to ensure the closing of resources.

_Example_: Using try-with-resources to close an input stream.
```Java
    File file = new File(BASE_DIRECTORY, name);
    try (BufferedReader in = new BufferedReader(new FileReader(file)))
    {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null)
        {
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }
    catch (IOException e)
    {
        throw new IllegalStateException("Can't access file " + name, e);
    }
```


# File Upload
Open source libraries such as **Apache Commons FileUpload** are used server side to process the 
**multipart form submission** that are the heart of a file upload mechanism.

Building a secure file upload mechanism requires several layers of defense, because 
this feature can be abused by attackers in a wide variety of ways.

## Upload of Dangerous Content

## Ability to Overwrite Other Files

## Processing ZIP Files

## Quota Overload DoS



## References
* Jim Manico, August Detlefson. **Iron-Clad Java: Building Secure Web Applications**. Oracle Press, 2015
    * Chapter 8: Safe File Upload and File I/O