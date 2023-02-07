# Example: Transmitting Data via the Client

The example  can be built and deployed with Maven:
```
$ mvn wildfly:deploy
```

URL: https://localhost:8443/Servlet-TransmittingData/

## Hidden Form Fields

We can send data to the client and back to the server via hidden form fields:
```
<input type="hidden" name="data" value="28a76cd5ef9031c0"/>
```

Unfortunately, they can be changed easily: 

* Use the **browser's web development tools** to modify the page.

* Use an **interception proxy** to modify the data in the HTTP request.  


## HTTP Cookies

Because the browser is adding cookies automatically to the request, we can use
them to carry data to the client and back to the server.

**HTTP Response**:
```
HTTP/1.1 200 OK
Set-Cookie: debug="ZmFsc2U="; Version=1; Discard
Content-Type: text/html;charset=ISO-8859-1
Content-Length: 1063
Connection: keep-alive
Date: Mon, 19 Jul 2021 17:50:43 GMT
```

**HTTP Request**
```
POST https://localhost:8443/Servlet-TransmittingData/controller HTTP/1.1
Referer: https://localhost:8443/Servlet-TransmittingData/
Content-Type: application/x-www-form-urlencoded
Content-Length: 95
Connection: keep-alive
Cookie: debug="ZmFsc2U="
Host: localhost:8443

data=28a76cd5ef9031c0&firstName=Homer&lastName=Simpson&username=homer&password=homer&action=Add
```

Again, we can manipulate the cookie values by using the **interception proxy**.
```
Cookie: debug="dHJ1ZQ=="
```

Note that in **base64 encoding**:
```
    true  = "dHJ1ZQ=="
    false = "ZmFsc2U="
```

## References:
* [Base64 encoder](https://www.base64encode.net/)

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011
    * Chapter 5: Bypassing Client-Side Controls


*Egon Teiniker, 2016-2023, GPL v3.0*
