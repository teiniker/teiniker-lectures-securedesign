Example: Client-Side Controls - HMAC Signature Cookie
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-ClientSideControls-Signature/controller

HTTP GET Response:
------------------
HTTP/1.1 200 OK
Connection: close
Set-Cookie: signature=357519aac576722cdb25266ca86d460e084abca8      <====
Content-Type: text/html;charset=ISO-8859-1
Date: Tue, 16 Jun 2020 14:34:42 GMT

<html>
    <head>
    	<title>Servlet Client-Side Controls</title>
    </head>
    <body>
    ...
    </body>
</html>

HTTP POST Request:
------------------
POST /Servlet-ClientSideControls-Signature/controller HTTP/1.1
Host: lab.se.org:8443
User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:76.0) Gecko/20100101 Firefox/76.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded
Content-Length: 101
Origin: https://lab.se.org:8443
Connection: close
Referer: https://lab.se.org:8443/Servlet-ClientSideControls-Signature/controller
Cookie: signature=357519aac576722cdb25266ca86d460e084abca8              <====
Upgrade-Insecure-Requests: 1

role=user&firstName=SADASD&lastName=Simpson&username=student%27+%23&password=sfsdfasdfasdf&action=Add

