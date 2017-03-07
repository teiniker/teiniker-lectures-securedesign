SpringSecurity: Session Management
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SpringSecurity-CSRF-ShoppingCart-Solution/

	=> https://localhost:8443/SpringSecurity-CSRF-ShoppingCart-Solution/
	

How to configure CSRF protection in Spring Security?
-------------------------------------------------------------------------------

o) Within the spring-security.xml file:

	<http auto-config="true" use-expressions="true">
		<intercept-url pattern="/login.html*" access="isAnonymous()"/>
		<intercept-url pattern="/**" access="hasRole('USER')" requires-channel="https"/>
		
		<csrf/> <!-- !!!!! -->
		...
	</http>	
	
	
o) In each POST request add the following hidden form field:
	(Note that the page must be processed as a JSP)

	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
		<head>
			<title>Simple Shopping Cart</title>
		</head>
		<body>
			<h2>Shopping Cart:</h2>
			<form method="post" action="controller">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <!-- !!! -->
				...
			</form>
		</body>
	</html>		
			

Synchronizer Token Pattern
-------------------------------------------------------------------------------
To protect against CSRF attacks we need to ensure there is something in the 
request that the evil site is unable to provide.

One solution is to use the Synchronizer Token Pattern. This solution is to 
ensure that each request requires, in addition to our session cookie, a randomly 
generated token as an HTTP parameter. 
When a request is submitted, the server must look up the expected value for the 
parameter and compare it against the actual value in the request. If the values 
do not match, the request should fail.

HTTP Request:
	POST /SpringSecurity-CSRF-ShoppingCart-Solution/controller HTTP/1.1
	Host: localhost:8443
	User-Agent: Mozilla/5.0 (X11; Linux i686; rv:35.0) Gecko/20100101 Firefox/35.0
	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
	Accept-Language: en-US,en;q=0.5
	Accept-Encoding: gzip, deflate
	Referer: https://localhost:8443/SpringSecurity-CSRF-ShoppingCart-Solution/controller
	Cookie: JSESSIONID=rPgAv5FRrdQXL8XFO5inxIi_.localhost
	Connection: keep-alive
	Content-Type: application/x-www-form-urlencoded
	Content-Length: 77
	
	_csrf=e74cc7ff-fb0b-44fc-af70-53560fc88c21&name=bbbbb&quantity=999&action=Add

You will notice that we added the _csrf parameter with a random value. Now the evil 
website will not be able to guess the correct value for the _csrf parameter (which 
must be explicitly provided on the evil website) and the transfer will fail when the 
server compares the actual token to the expected token.

			