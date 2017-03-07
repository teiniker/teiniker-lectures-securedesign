Servlet Session Management - Shopping Cart (View State)
-------------------------------------------------------------------------------

URL: https://localhost:8443/Servlet-SessionManagement-ViewState

To implement session management, a ViewState has been used. A hidden form field
called "state" is used to store the state of the session.

The following actions are handled by the "ControllerServlet":

o) Login
	- create a new ArrayList<Product> object
	- create a new state by passing a ArrayList<Product> object to the given
		listToString() method.
	- create a message for the web page e.g. "You logged in successfully!"
	
o) Logout
	- destroy the current session by setting the state to an empty string
	- create a message for the web page e.g. "You logged out successfully!"
	
o) Add
	- read the parameters "name", "quantity" and "state" from the request
	- convert the state string into an ArrayList<Product> by using the
	- create a new Product object and add it to the ArrayList<Product>
		given method listFromString()
	- convert the ArrayList<Product> back into the state string using the
		method listToString()	
	- create a message for the web page e.g.  "added: 5 cd to the cart"
		
Note that we skip the authentication to make the example simpler.