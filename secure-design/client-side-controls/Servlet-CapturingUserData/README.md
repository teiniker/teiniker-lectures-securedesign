# Capturing User Data

The example  can be built and deployed with Maven:
```
$ mvn wildfly:deploy
```

URL: http://localhost:8080/Servlet-CapturingUserData


The input fields are validated in the browser by using `maxlength="16"` and `pattern="[A-Za-z0-9_!]{12,}"`
**constraints**.
```
    <input type="text" name="firstName" maxlength="16"/>
    <input type="text" name="lastName" maxlength="16"/>
    <input type="text" name="username" pattern="[A-Za-z0-9_.]{5,16}"/>
    <input type="password" name="password" pattern="[A-Za-z0-9_!#]{12,}"/>
```

If a user enters invalid data and clicks the submit button, he or she will see
an error message.

To **bypass the client-side validation**, we can:

A) Use the **browser's web development tools** to modify the page.
```
    <input type="text" name="firstName" />
    <input type="text" name="lastName" />
    <input type="text" name="username" />
    <input type="password" name="password" />
```

B) Use an **interception proxy** to modify the data in the HTTP request.
```
POST https://localhost:8443/Servlet-CapturingUserData/controller HTTP/1.1
Referer: https://localhost:8443/Servlet-CapturingUserData/
Content-Type: application/x-www-form-urlencoded
Content-Length: 83
Connection: keep-alive
Host: localhost:8443

firstName=Homer&lastName=Simpson&username=homer&password=homer123&action=Add
```

C) Use **another client**.
```
$ curl -i -k -X POST -d 'firstName=Homer&lastName=Simpson&username=homer&password=123&action=Add' https://localhost:8443/Servlet-CapturingUserData/
```

## References
* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011 
    * Chapter 5: Bypassing Client-Side Controls


*Egon Teiniker, 2016-2023, GPL v3.0*
