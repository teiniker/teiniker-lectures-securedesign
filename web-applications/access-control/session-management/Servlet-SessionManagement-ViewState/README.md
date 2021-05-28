# Servlet Session Management - Shopping Cart (View State)

URL: https://lab.se.org:8443/Servlet-SessionManagement-ViewState

To implement session management, a View State has been used. A hidden form field
called `state` is used to store the state of the session.

The following actions are handled by the `ControllerServlet`:

* Login
	- create a new ArrayList<Product> object
	- create a new state by passing a ArrayList<Product> object to the given
		listToString() method.
	- create a message for the web page e.g. "You logged in successfully!"
	
* Logout
	- destroy the current session by setting the state to an empty string
	- create a message for the web page e.g. "You logged out successfully!"
	
* Add
	- read the parameters "name", "quantity" and "state" from the request
	- convert the state string into an ArrayList<Product> by using the
	- create a new Product object and add it to the ArrayList<Product>
		given method listFromString()
	- convert the ArrayList<Product> back into the state string using the
		method listToString()	
	- create a message for the web page e.g.  "added: 5 cd to the cart"
		
Note that we skip the authentication to make the example simpler.

## References
* [OWASP: Session Management Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html)

* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
    * Chapter 2: Authentication and Session Management

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011
    * Chapter 7: Attacking Session Management

*Egon Teiniker, 2019-2021, GPL v3.0*