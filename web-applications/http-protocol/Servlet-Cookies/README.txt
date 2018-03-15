Servlet: HTTP Cookies
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Cookies/


1. Browser loads HTML form
--------------------------
GET /Servlet-Cookies/ HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Connection: close


HTTP/1.1 200 OK
Connection: close
Last-Modified: Fri, 24 Nov 2017 12:24:36 GMT
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: text/html
Content-Length: 1168
Accept-Ranges: bytes
Date: Thu, 15 Mar 2018 16:56:27 GMT

<html>...</html>

		
2. Browser submits HTTP form and server generates a cookie
----------------------------------------------------------

POST /Servlet-Cookies/controller HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded
Content-Length: 62
Referer: http://localhost:8080/Servlet-Cookies/
Connection: close

username=student&password=student&usergroup=Guest&action=Login


HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Set-Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5; HttpOnly
Server: WildFly/10
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 160
Date: Thu, 15 Mar 2018 16:56:35 GMT

<html>...</html>


3. Browser requests another page and sends cookie automatically + server deletes cookie
---------------------------------------------------------------------------------------

GET /Servlet-Cookies/cookies.html HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/Servlet-Cookies/controller
Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5
Connection: close


HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Set-Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:00 GMT
Server: WildFly/10
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 182
Date: Thu, 15 Mar 2018 16:56:38 GMT

<html>...</html>


4. Browser requests same page (without cookie)

GET /Servlet-Cookies/cookies.html HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/Servlet-Cookies/controller
Connection: close


HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Server: WildFly/10
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 127
Date: Thu, 15 Mar 2018 16:56:42 GMT

<html>...</html>


Cookie APIs
-------------------------------------------------------------------------------

HttpServletRequest
https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServletRequest.html

    Cookie[] getCookies()
    Returns an array containing all of the Cookie objects the client sent with this request.


HttpServletResponse
https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServletResponse.html

    void addCookie(Cookie cookie)
    Adds the specified cookie to the response.


Cookie
https://docs.oracle.com/javaee/7/api/javax/servlet/http/Cookie.html

    Cookie(String name, String value)
    Constructs a cookie with the specified name and value.

    void setValue(String newValue)
    Assigns a new value to this Cookie.


    void setMaxAge(int expiry)
    Sets the maximum age in seconds for this Cookie.

    void setDomain(String domain)
    Specifies the domain within which this cookie should be presented.

    void setPath(String uri)
    Specifies a path for the cookie to which the client should return the cookie.


    void setSecure(boolean flag)
    Indicates to the browser whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL.

    void setHttpOnly(boolean isHttpOnly)
    Marks or unmarks this Cookie as HttpOnly.
