# Transmitting Data via the Client

Sometimes data is passed as **hidden form fields** via the client. 
Hidden fields are included in the request like normal input data.

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

## Manipulating Hidden Fields

The hidden input fields are part of a HTML from and will be submitted like 
regular input fields:

```HTML
     <input type="hidden" name="data" value="28a76cd5ef9031c0"/>  
```

To modify the hidden fields, we can:

A) Use the **browser's web development tools** to modify the page:
```HTML
   <input type="hidden" name="data" value="0000000000000000"/>
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

data=28a76cd5ef9031c0&id=7&username=homer&password=moreduffbeer%21%21
```

C) Use another client:

```
$ curl -ki -X POST -d 'data=0000000000000000&id=7&username=homer&password=moreduffbeer%21%21' https://localhost:8443/controller
```

## References

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011
    * Chapter 5: Bypassing Client-Side Controls

*Egon Teiniker, 2017-2024, GPL v3.0*