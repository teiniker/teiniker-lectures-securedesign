Servlet: HTTP Headers
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-HttpHeaders/


Servlet API for Handling HTTP Headers
-------------------------------------------------------------------------------
HttpServletRequest:
-------------------
public String getHeader(String name); 
	Returns the value of the specified request header as a String. 
	If the request did not include a header of the specified name, this method 
	returns null.
    If there are multiple headers with the same name, this method returns the 
    first head in the request.

public Enumeration<String> getHeaders(String name); 
	Returns all the values of the specified request header as an Enumeration of 
	String objects.

public Enumeration<String> getHeaderNames();
	Returns an enumeration of all the header names this request contains. If the 
	request has no headers, this method returns an empty enumeration.

    
HttpServletResponse:
--------------------
public void setHeader(String name, String value);
	Sets a response header with the given name and value.
    If the header had already been set, the new value overwrites the previous one.
    
public void addHeader(String name, String value);
	Adds a response header with the given name and value.
    This method allows response headers to have multiple values.
    
public String getHeader(String name);
	Gets the value of the response header with the given name.
	If a response header with the given name exists and contains multiple values, 
	the value that was added first will be returned.    				
				
public Collection<String> getHeaders(String name); 
	Gets the values of the response header with the given name.
	
public Collection<String> getHeaderNames();
	Gets the names of the headers of this response.
	
public boolean containsHeader(String name);
	Returns a boolean indicating whether the named response header has already 
	been set.						
				
				
How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-8.2.0.Final</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		

		
		
		