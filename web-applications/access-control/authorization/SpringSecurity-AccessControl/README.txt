SpringSecurity: Access Control
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: https://localhost:8443/SpringSecurity-AccessControl/


How to define users and roles?
-------------------------------------------------------------------------------
To the spring-security.xml file, add:

	<authentication-manager>
		<authentication-provider>
			<password-encoder hash="bcrypt"/>
			<user-service>
				<user name="student" password="$2a$10$rw4FZiFAGyZcwpQYFl0PA.B0WiY3dLoO2/5ypZ5VNF.sUyiYN8TZm" authorities="ROLE_USER" />
				<user name="bart" password="$2a$10$H3y5wnm0gJ2GdL5T5lEH6ev/IroK3bcXMPcGKlsXSFoPwM7.1ts5q" authorities="ROLE_USER" />
				<user name="lisa" password="$2a$10$KUloDm3rBTIFdvItsVZ0aezVPBi73Bq3tU9opT0eS6jbdPJ5Vi52i" authorities="ROLE_USER, ROLE_ADMIN" />
				<user name="burns" password="$2a$10$FpDXVTqXlriWLh3yjoPwO.8kFJDGsiaATcH923v4JY8PN6h98pdai" authorities="ROLE_ADMIN" />
			</user-service>
		</authentication-provider>
	</authentication-manager>
	

How to define access control rules?
-------------------------------------------------------------------------------
To the existing <intercept-url> add some more of them:

	<http auto-config="true" use-expressions="true">
		<intercept-url pattern="/public/login.html" access="isAnonymous()"/>
		<intercept-url pattern="/user/**" access="hasAnyRole('USER','ADMIN')" requires-channel="https"/>
		<intercept-url pattern="/admin/**" access="hasRole('ADMIN') and hasIpAddress('127.0.0.1')" requires-channel="https"/>
		...
	</http>		
	
	
How to specify your own access denied page?
-------------------------------------------------------------------------------
Within the <http> element we can specify a corresponding Web page:
 
	<http auto-config="true" use-expressions="true">
		<access-denied-handler error-page="/public/access-denied.html" />		
		...
	</http>				




├── src
│   ├── main
│   │   └── webapp
│   │       ├── public
│   │       │   ├── access-denied.html
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   └── welcome.html
│   │       ├── user
│   │       │   └── info.html
│   │       ├── admin
│   │       │   └── configuration.html
│   │       └── WEB-INF
│   │           ├── spring-security.xml
│   │           └── web.xml



HTTP Transactions for an anonymous user
-------------------------------------------------------------------------------

GET /SpringSecurity-AccessControl/public/welcome.html HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
Cookie: JSESSIONID=_Uu-Cjjj845SjqxdCZLScM8lig_dWvYRQycWKVVp.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1


HTTP/1.1 200 OK
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Accept-Ranges: bytes
Date: Sat, 27 Apr 2019 17:43:36 GMT
Connection: close
Last-Modified: Tue, 13 Nov 2018 11:20:02 GMT
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Type: text/html
Content-Length: 293

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
	<h2>Welcome on the public page!</h2>


	<a href="/SpringSecurity-AccessControl/public/index.html">back</a>

</body>
</html>


HTTP Transactions for a user (student/student)
-------------------------------------------------------------------------------

GET /SpringSecurity-AccessControl/user/info.html HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1


HTTP/1.1 302 Found
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Set-Cookie: JSESSIONID=z4Kt4BWsqG6ofXQVBudWVI1kqmAN8ceIAV6I9Zyr.localhost; path=/SpringSecurity-AccessControl
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Location: https://localhost:8443/SpringSecurity-AccessControl/public/login.html
Date: Sat, 27 Apr 2019 17:49:31 GMT
Connection: close
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Length: 0


GET /SpringSecurity-AccessControl/public/login.html HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
Cookie: JSESSIONID=z4Kt4BWsqG6ofXQVBudWVI1kqmAN8ceIAV6I9Zyr.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1


HTTP/1.1 200 OK
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Accept-Ranges: bytes
Date: Sat, 27 Apr 2019 17:49:31 GMT
Connection: close
Last-Modified: Tue, 13 Nov 2018 11:20:02 GMT
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Type: text/html
Content-Length: 559

<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
</head>
<body onload='document.f.username.focus();'>
	<h3>Please login with Username and Password</h3>
	<form action="/SpringSecurity-AccessControl/login" method="POST">
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
</body>
</html>


POST /SpringSecurity-AccessControl/login HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/login.html
Content-Type: application/x-www-form-urlencoded
Content-Length: 46
Cookie: JSESSIONID=z4Kt4BWsqG6ofXQVBudWVI1kqmAN8ceIAV6I9Zyr.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1

username=student&password=student&submit=Login


HTTP/1.1 302 Found
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Set-Cookie: JSESSIONID=6m4QXi8aD45eSKb4B30KOy1mkKlwURRpdYFCER0B.localhost; path=/SpringSecurity-AccessControl
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Location: https://localhost:8443/SpringSecurity-AccessControl/user/info.html
Date: Sat, 27 Apr 2019 17:49:41 GMT
Connection: close
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Length: 0


GET /SpringSecurity-AccessControl/user/info.html HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/login.html
Cookie: JSESSIONID=6m4QXi8aD45eSKb4B30KOy1mkKlwURRpdYFCER0B.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1


HTTP/1.1 200 OK
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Accept-Ranges: bytes
Date: Sat, 27 Apr 2019 17:49:41 GMT
Connection: close
Last-Modified: Tue, 13 Nov 2018 11:20:02 GMT
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Type: text/html
Content-Length: 284

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Info Page</title>
</head>
<body>
	<h2>This is a user info page!</h2>

	<a href="/SpringSecurity-AccessControl/public/index.html">back</a>
</body>
</html>


GET /SpringSecurity-AccessControl/admin/configuration.html HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
Cookie: JSESSIONID=6m4QXi8aD45eSKb4B30KOy1mkKlwURRpdYFCER0B.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1


HTTP/1.1 403 Forbidden
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Accept-Ranges: bytes
Date: Sat, 27 Apr 2019 17:53:02 GMT
Connection: close
Last-Modified: Tue, 13 Nov 2018 11:20:02 GMT
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Type: text/html
Content-Length: 319

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Error Page</title>
</head>
<body>
	<h2>Access Denied!</h2>
	You are not allowed to enter this page...

	<a href="/SpringSecurity-AccessControl/public/index.html">back</a>

</body>
</html>


POST /SpringSecurity-AccessControl/logout HTTP/1.1
Host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
Content-Type: application/x-www-form-urlencoded
Content-Length: 0
Cookie: JSESSIONID=6m4QXi8aD45eSKb4B30KOy1mkKlwURRpdYFCER0B.localhost
DNT: 1
Connection: close
Upgrade-Insecure-Requests: 1

HTTP/1.1 302 Found
Expires: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Set-Cookie: JSESSIONID=6m4QXi8aD45eSKb4B30KOy1mkKlwURRpdYFCER0B.localhost; path=/SpringSecurity-AccessControl; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:00 GMT
X-XSS-Protection: 1; mode=block
Pragma: no-cache
X-Frame-Options: DENY
Location: https://localhost:8443/SpringSecurity-AccessControl/public/index.html
Date: Sat, 27 Apr 2019 17:53:42 GMT
Connection: close
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Content-Type-Options: nosniff
Content-Length: 0





