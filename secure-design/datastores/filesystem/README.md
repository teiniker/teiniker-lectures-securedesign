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

We have to assume that some type of dangerous file will get uploaded to our server:
* **.html**, **.htm** files will be automatically rendered by our browser when we 
    try to download them. Any JavaScript contained in these files will run in the 
    same domain as the site itself (bypassing the browser’s Same Origin Policy).  
* **.jsp**, **.jspx**, or **.war** files can be executed on the server-side and 
    should be stored on a completely separate domain and served by a separate web 
    server that is not a Java-based server.
* Other types of files can also have **malicious content**. We have to check the
    content of files before other users download them. 


## Ability to Overwrite Other Files

**File path injection** can be used to read files that should not be accessible.
When a file is uploaded, the browser sends a multipart/form-data POST request 
to the server. The **filename** is included as a **parameter in the request**.


## Processing ZIP Files
A **zip bomb** (a malicious zip file that is of small size compressed but 
it is of a giant size uncompressed) could be used.


## Quota Overload DoS
If no limit is imposed, an attacker could **upload many terabytes** of files and 
consume all storage on our server, resulting in a denial of service.


## Securing File Upload

* **Validating File Extensions**: A step that should be performed is 
    **whitelist-based validation** to ensure that only certain file extensions 
    are allowed.

* **Verify File Content**: We also have to verify if the content of a file 
    actually match the given file type. 

* **Processing Archive Files**: Most zip or other archive APIs allow us to 
    inspect the total uncompressed size before conducting the uncompressing 
    action.
    
    A strong defense control should not only check the reported size prior 
    to unzipping, but also check that the actual unzipped size of each file 
    matches.

* **Object Reference Maps**: Another useful precaution is to not use the 
    actual file name provided by users to save the uploaded file. Instead, 
    we use a machine generated name that is stored in a database together 
    with the original filename. The filename is never revealed to the user, 
    and there is no opportunity for path manipulation.

* **Virus and Malware Detection**: Uploaded files should be immediately placed 
    into a quarantine directory where they are checked for malware. Once the 
    anti-virus check succeeds the file is ready to be used by other users for 
    download.

* **Quota Policy**: The following things should be considered when building a 
    quota policy:
    * Set a maximum total storage quota for each user. 
    * Limit the size of any single file to prevent an attacker from abusing 
        file verification mechanisms by passing in files that will consume 
        an huge amount of RAM or CPU to validate.
    * Limit the total number of files a user can upload to avoid slowing down 
        the file system on the server.


## References
* Jim Manico, August Detlefson. **Iron-Clad Java: Building Secure Web Applications**. Oracle Press, 2015
    * Chapter 8: Safe File Upload and File I/O

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011.
    * Chapter 10: Attacking Back-End Components    

*Egon Teiniker, 2017-2024, GPL v3.0*
