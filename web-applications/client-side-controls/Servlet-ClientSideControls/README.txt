Example: Client-Side Controls
---------------------------------------------------------------------

How to setup the client-side?
---------------------------------------------------------------------
o) Setup an interception proxy like ZAP

o) Use the browser to access the page:
	URL: http://localhost:8080/Servlet-ClientSideControls/controller

    $ curl --proxy localhost:8010 -k -i https://localhost:8443/Servlet-ClientSideControls/controller


1. Hidden Form Fields
---------------------------------------------------------------------

 <input type="hidden" name="role" value="user"/>
 
 Change the value to "admin" by using:
 a) Firefox: Developer / Inspector
 b) ZAP
 

2. HTTP Cookies 
---------------------------------------------------------------------

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: debug="ZmFsc2U="

Change the value to "true" by using BurpSuite:
a) Activate interception
b) Change value to "dHJ1ZQ=="
c) forward

Note that in base64 encoding:
	true  = "dHJ1ZQ=="
	false = "ZmFsc2U="

See: https://www.base64encode.net/


3. Validating Client-Generated Data
---------------------------------------------------------------------
The password field is validated against the following regex: 
	"[A-Za-z0-9_!]{12,}"

<input type="password" pattern="[A-Za-z0-9_!]{12,}" name="password">

If you enter an invalid password, e.g. "123" and submit you will see
an error message.

a) Set BurpSuite intercept is ON
b) Enter a valid password, e.g. "12345" and submit
c) Change the password:
	from  role=user&firstName=Homer&lastName=Simpson&username=homer&password=12345&action=Add
	to 	  role=user&firstName=Homer&lastName=Simpson&username=homer&password=x&action=Add	
d) Forward 
	
Note: Set debug = true


How to deploy the Web application?
-------------------------------------------------------------------------------
Make sure the the wildfly server is up and running.

$ mvn wildfly:deploy

