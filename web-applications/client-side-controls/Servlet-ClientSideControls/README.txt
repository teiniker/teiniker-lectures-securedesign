Servlet: Client-Side Controls
---------------------------------------------------------------------

How to setup the client-side?
---------------------------------------------------------------------
o) Run BurpSuite

o) Configure the browser to use the proxy

o) Use the browser to access the page:
	
	URL: http://localhost:8080/Servlet-ClientSideControls/controller


1. Hidden Form Fields
---------------------------------------------------------------------

 <input type="hidden" name="role" value="user"/>
 
 Change the value to "admin" by using:
 a) Firebug
 b) BurpSuite
 

2. HTTP Cookies 
---------------------------------------------------------------------

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: debug=false

Change the value to "true" by using BurpSuite:
a) Activate interception
b) Change value
c) forward


3. Opaque Data
---------------------------------------------------------------------
Base64 encode cookie value:
	Set-Cookie: debug="ZmFsc2U="

a) Set BurpSuite intercept is ON
b) Reload the page:  
	http://localhost:8080/Servlet-ClientSideControls/controller 
c) Change HTTP header in the response 
	Set-Cookie: debug="ZmFsc2U=" to Set-Cookie: debug="dHJ1ZQ=="
d) Add a new user in the browser 
e) Review Tomcat's console output	

Note that in base64 encoding:
	true  = "dHJ1ZQ=="
	false = "ZmFsc2U="


4. Validating Client-Generated Data
---------------------------------------------------------------------
The password field is validated against the following regex: 
	"[A-Za-z0-9_!]{4,}"

<input type="password" pattern="[A-Za-z0-9_!]{4,}" name="password">

If you enter an invalid password, e.g. "123" and submit you will see
an error message.

a) Set BurpSuite intercept is ON
b) Enter a valid password, e.g. "12345" and submit
c) Change the password:
	from  role=user&firstName=Homer&lastName=Simpson&username=homer&password=12345&action=Add
	to 	  role=user&firstName=Homer&lastName=Simpson&username=homer&password=x&action=Add	
d) Forward 
	
Note: Set debug = true



How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		
		
