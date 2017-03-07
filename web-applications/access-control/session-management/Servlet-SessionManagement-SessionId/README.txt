Servlet Session Management - Shopping Cart (Session ID)
-------------------------------------------------------------------------------

URL: https://localhost:8443/Servlet-SessionManagement-SessionId

In the given "ControllerServlet" a session id based session management has been 
implemented to realize a simple shopping cart which stores a list of products 
into the current session.

The following actions are handled by the "ControllerServlet":

o) Login
	- create a new session
	- create a new List<Product> object and store it in the session object
	- create a message for the web page e.g. "You logged in successfully!"
	
o) Logout
	- destroy the current session
	- create a message for the web page e.g. "You logged out successfully!"
	
o) Add
	- read the parameters "name" and "quantity"	from the request
	- create a new Product object
	- if there is a valid session, add the new product to the List<Product>
	- create a message for the web page e.g.  "added: 5 cd to the cart"
		
You can access the Web application using the following URL:
	http://localhost:8080/LabExam-SecDesign-2015-05-29-G1-MyName
	
Note that we skip the authentication to make the example simpler.
	