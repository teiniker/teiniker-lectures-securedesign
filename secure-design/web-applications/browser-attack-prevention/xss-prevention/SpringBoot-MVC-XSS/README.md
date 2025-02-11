# Cross-Site-Scripting


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

## XSS Attacks

Here are some examples of XSS attack strings:

* Execute a reflected XSS attack:
```
<script>alert('XSS');</script>
```

* Read cookie values:
  First, we create a cookie which can be read by a attacker.
```
URL: https://localhost:8443/set-cookie

<script>alert(document.cookie);</script>
```

## XSS Prevention

* **HTML Encoding embedded data**:
  All data embedded in a HTML page must be HTML encoded. 
  
  This is done by the Thymeleaf template engine. 
  We should avoid using `th:utext` (which outputs unescaped HTML) and 
  instead use `th:text`, which automatically escapes any HTML characters. 

  ```Java
      <div th:text="'Welcome, ' + ${username}"></div>
  ```

* **HTTPOnly Cookies**:
  Cookies should be marked as **HTTPOnly** to prevent JavaScript from 
  accessing them.
  This can be done by setting the HttpOnly flag in the cookie.
  In addition, the **Secure** flag should be set to prevent the cookie 
  from being sent over an unencrypted connection.

  ```Java
    Cookie cookie = new Cookie("SESSIONID", "A887B32D3520132167568B487BDAF2F4");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    response.addCookie(cookie);
  ```

*Egon Teiniker, 2017-2025, GPL v3.0*