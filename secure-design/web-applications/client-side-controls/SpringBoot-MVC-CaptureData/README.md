# Capturing User Data

Capturing and validating data on the client side enables a range of attacks 
and manipulations.

## Build and Run

We build and run the application like any Spring-Boot application:
```
$ mvn spring-boot:run 
```

## Accessing the Web Application

The running web application can be accessed by a browser: 
```
URL: https://localhost:8443/index.html
```

## Bypass HTML Constraints

The input fields are validated in the browser by using HTML constraints:
```HTML
    <input type="number" id="id" name="id" min="1" step="1" required>
    <input type="text" id="username" name="username" pattern="[a-z_.]{4,8}" required>
    <input type="password" id="password" name="password" minlength="10" required>
```
If a user enters invalid data and clicks the submit button, he or she will see an error message.

To bypass the client-side validation, we can:

A) Use the **browser's web development tools** to modify the page:
```HTML
    <input type="number" id="id" name="id">
    <input type="text" id="username" name="username">
    <input type="password" id="password" name="password">
```

B) Use an **interception proxy** to modify and send again the HTTP request:
```
POST https://localhost:8443/controller HTTP/1.1
host: localhost:8443
User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101 Firefox/102.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Referer: https://localhost:8443/index.html
Content-Type: application/x-www-form-urlencoded
Content-Length: 46
Origin: https://localhost:8443
Connection: keep-alive

id=7&username=marge&password=morebeer%21%21%21
```

C) Use another client:

```
$ curl -ki -X POST -d 'id=1&username=homer&password=love4duffbeer' https://localhost:8443/controller
```

## References

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011
    * Chapter 5: Bypassing Client-Side Controls

*Egon Teiniker, 2017-2024, GPL v3.0*