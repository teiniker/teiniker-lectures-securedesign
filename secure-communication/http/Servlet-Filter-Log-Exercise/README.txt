Exercise: Servlet Filter Logger (Common Logging Format)
-------------------------------------------------------------------------------

Implement a Servlet Filter called "LogFilter" which generates a Common Log Format
message.

Collect the following data from the request and response objects:
i) Request object
    Client IP
    hard-code "- -"
    Time Stamp
    Request method and query string (request.getMethod(), request.getRequestURI(),
        request.getQueryString())
    Protocol

ii) Response object
    Status code
    Size of content

Tip: Use the HttpServletRequest type.


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://lab.se.org:8080/Servlet-Filter-Log-Exercise/

Console:
127.0.0.1 - - 17/Apr/2015:08:48:57 +0200 "GET /Servlet-Filter-Log/controller?username=student&password=student&action=Login HTTP/1.1" 200 127
