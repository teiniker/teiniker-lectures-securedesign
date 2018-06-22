SpringSecurity: CSRF Protection
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SpringSecurity-CSRF-ShoppingCart-Exercise/

	=> https://localhost:8443/SpringSecurity-CSRF-ShoppingCart-Exercise/
	



How to access the application from curl ?
-------------------------------------------------------------------------------
$ curl -i -s -k  -X POST -H 'Content-Type: application/x-www-form-urlencoded' --data-binary $'name=DVD&quantity=5&action=Add' https://localhost:8443/SpringSecurity-CSRF-ShoppingCart/controller

Note that without having a valid JSESSIONID, the request will be redirected to 
the login.html page.

HTTP Response:
	HTTP/1.1 302 Found
	Expires: 0
	Cache-Control: no-cache, no-store, max-age=0, must-revalidate
	X-Powered-By: Undertow/1
	Set-Cookie: JSESSIONID=DBt4-wOWatxG85R4Z-rveHcr.localhost; path=/SpringSecurity-CSRF-ShoppingCart
	Server: WildFly/8
	X-XSS-Protection: 1; mode=block
	Pragma: no-cache
	X-Frame-Options: DENY
=>	Location: https://localhost:8443/SpringSecurity-CSRF-ShoppingCart/login.html
	Date: Thu, 25 Jun 2015 20:01:34 GMT
	Connection: keep-alive
	X-Content-Type-Options: nosniff
	Strict-Transport-Security: max-age=31536000 ; includeSubDomains
	Content-Length: 0

If we are able to provide a valid JSESSIONID cookie, we can execute the operation:

$ curl -i -s -k  -X POST -H 'Content-Type: application/x-www-form-urlencoded' -b 'JSESSIONID=oPWZ8FQoH_EXb1SkXM6ELitY.localhost' --data-binary $'name=DVD&quantity=5&action=Add' https://localhost:8443/SpringSecurity-CSRF-ShoppingCart/controller

HTTP Response:
	HTTP/1.1 200 OK
	Expires: 0
	Cache-Control: no-cache, no-store, max-age=0, must-revalidate
	X-Powered-By: Undertow/1
	Server: WildFly/8
	X-XSS-Protection: 1; mode=block
	Pragma: no-cache
	X-Frame-Options: DENY
	Date: Thu, 25 Jun 2015 20:05:07 GMT
	Connection: keep-alive
	X-Content-Type-Options: nosniff
	Strict-Transport-Security: max-age=31536000 ; includeSubDomains
	Content-Type: text/html;charset=ISO-8859-1
	Content-Length: 907
	
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	  <head>
	  	<title>Simple Shopping Cart</title>
	  </head>
	  <body>
			<h2>Shopping Cart:</h2>
			<form method="post" action="controller">
	  		...
	  		</form>
	  </body>
	</html>  
	
	
How to run a CSRF attack?
-------------------------------------------------------------------------------

o) Login 
	URL: https://localhost:8443/SpringSecurity-CSRF-ShoppingCart/
	student/student
	
o) Add a product to the shopping cart

o) Open a new Browser tab and go to
	URL: http://localhost:8080/CSRF-Attack
		
		