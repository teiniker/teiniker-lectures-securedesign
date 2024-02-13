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

## References

*Egon Teiniker, 2017-2024, GPL v3.0*