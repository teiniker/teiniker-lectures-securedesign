# HTTP Headers

HTTP headers let the client and the server pass **additional information with an HTTP request or response**.

## Example: Servlet-HttpHeaders

The example  can be built and deployed with Maven:
```
$ mvn wildfly:deploy
```
URL: http://localhost:8080/Servlet-HttpHeaders/

In this example, a HTTP request is sent to the server, the header information is displayed and an additional 
header is inserted into the response message.


```Java 
    Enumeration<String> headerNames = request.getHeaderNames();
    while(headerNames.hasMoreElements()) 
    {
      String headerName = (String)headerNames.nextElement();
      html.append(request.getHeader(headerName);
    // ...
    }
    //...
    response.setHeader("Refresh", "5");
```




## Servlet API for Handling HTTP Headers

### HttpServletRequest:

* **public String getHeader(String name)**\ 
	Returns the value of the specified request header as a String. 
	If the request did not include a header of the specified name, this method 
	returns null.
    If there are multiple headers with the same name, this method returns the 
    first head in the request.

* **public Enumeration<String> getHeaders(String name)**\ 
	Returns all the values of the specified request header as an Enumeration of 
	String objects.

* **public Enumeration<String> getHeaderNames()**\
	Returns an enumeration of all the header names this request contains. If the 
	request has no headers, this method returns an empty enumeration.

    
### HttpServletResponse

* **public void setHeader(String name, String value)**\
	Sets a response header with the given name and value.
    If the header had already been set, the new value overwrites the previous one.
    
* **public void addHeader(String name, String value)**\
	Adds a response header with the given name and value.
    This method allows response headers to have multiple values.
    
* **public String getHeader(String name)**\
	Gets the value of the response header with the given name.
	If a response header with the given name exists and contains multiple values, 
	the value that was added first will be returned.    				
				
* **public Collection<String> getHeaders(String name)**\ 
	Gets the values of the response header with the given name.
	
* **public Collection<String> getHeaderNames()**\
	Gets the names of the headers of this response.
	
* **public boolean containsHeader(String name)**\
	Returns a boolean indicating whether the named response header has already 
	been set.						
				
				
## References

**HTTP Header Specification**
* [RFC2616: Hypertext Transfer Protocol - HTTP/1.1](https://datatracker.ietf.org/doc/html/rfc2616#section-14)

**Java API Documentation:**
* [HttpServletRequest](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html)
* [HttpServletResponse](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletResponse.html)

*Egon Teiniker, 2019-2021, GPL v3.0*