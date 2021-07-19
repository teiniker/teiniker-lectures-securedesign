# HTTP Cookies

## Example: Servlet-Cookies

The example  can be built and deployed with Maven:
```
$ mvn wildfly:deploy
```

URL: http://localhost:8080/Servlet-Cookies/

After the client sends a POST request to the server, a **cookie is generated on the server-side** and inserted into 
the response header.

```Java
Cookie cookie = new Cookie("id", generateId());
cookie.setHttpOnly(true);
cookie.setSecure(true); 
response.addCookie(cookie);
```

**HTTP POST Request:**
```
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
```

**HTTP Response**
```
HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Set-Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5; HttpOnly
Server: WildFly/10
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 160
Date: Thu, 15 Mar 2018 16:56:35 GMT

<html>...</html>
```

If we follow the `cookies.html` link, the Browser will automatically add the cookie to the GET
request. The second Servlet displays the cookies and set them to `Max-Age=0` which removes
the cookies from the browser.

```Java
    Cookie[] cookies = request.getCookies();
    if(cookies != null)
    {
        for(Cookie c : cookies)
        {
            html.append(c.getName()).append(": ").append(c.getValue());
            c.setMaxAge(0);
            response.addCookie(c);
        }
    }
```

**HTTP GET Request**
```
GET /Servlet-Cookies/cookies.html HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/Servlet-Cookies/controller
Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5
Connection: close
```

**HTTP Response**
```
HTTP/1.1 200 OK
Connection: close
X-Powered-By: Undertow/1
Set-Cookie: id=b8d997617cec8bbafef0e06b017a1767a27203d5; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:00 GMT
Server: WildFly/10
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 182
Date: Thu, 15 Mar 2018 16:56:38 GMT

<html>...</html>
```

When we access the same page (), the Browser does not send the cookie again.
```
GET /Servlet-Cookies/cookies.html HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Referer: http://localhost:8080/Servlet-Cookies/controller
Connection: close
```

## Servlet API for Handling Cookies 

### HttpServletRequest:

* **Cookie[] getCookies()**\
    Returns an array containing all of the Cookie objects the client sent with this request.


### HttpServletResponse:

* **void addCookie(Cookie cookie)**\
    Adds the specified cookie to the response.


### Cookie:

* **Cookie(String name, String value)**\
    Constructs a cookie with the specified name and value.

* **void setValue(String newValue)**\
    Assigns a new value to this Cookie.

* **void setMaxAge(int expiry)**\
    Sets the maximum age in seconds for this Cookie.

* **void setDomain(String domain)**\
    Specifies the domain within which this cookie should be presented.

* **void setPath(String uri)**\
    Specifies a path for the cookie to which the client should return the cookie.

* **void setSecure(boolean flag)**\
    Indicates to the browser whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL.

* **void setHttpOnly(boolean isHttpOnly)**\
    Marks or unmarks this Cookie as HttpOnly.

## References
**Cookie Specification**
* [RFC6265: HTTP State Management Mechanism](https://datatracker.ietf.org/doc/html/rfc6265)

**Java API Documentation:**
* [HttpServletRequest](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServletRequest.html)
* [HttpServletResponse](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServletResponse.html)
* [Cookie](https://docs.oracle.com/javaee/7/api/javax/servlet/http/Cookie.html)


*Egon Teiniker, 2019-2021, GPL v3.0*
