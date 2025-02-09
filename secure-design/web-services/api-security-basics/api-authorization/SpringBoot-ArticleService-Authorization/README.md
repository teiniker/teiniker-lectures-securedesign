# Example: SpringBoot Authorization

## Setup Spring Security Authorization

We use the **Spring Security** module to realize Basic Authentication.
Thus, we include a dependency to `pom.xml`:

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

Now we can add a `SecurityConfig` class. All security related settings can be found there
(compare this with the `spring-security.xml` we used in our web applications):

```Java 
```

## Access the REST Service

Let's run the example:
```
$ mvn spring-boot:run
```

We can access the public `infos` resource **without authentication**.
```
$ curl -i -k https://localhost:8443/infos

HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 36
Date: Sat, 16 Oct 2021 08:21:56 GMT

Public API Information (Version 1.0)
```

If we try the same with the `articles` resource, we get an **401 Unauthorized** error.
```
$ curl -i -k https://localhost:8443/articles
```

Thus, **only an authenticated user** in the role `USER` has access to the `articles` resource.
```
$ curl -i -k -u homer:homer https://localhost:8443/articles

HTTP/1.1 200 
Content-Type: application/json
Date: Sat, 16 Oct 2021 08:26:54 GMT

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":3336}]
```

A user like `burns` is not allowed to access the `articles` service
because he is not in the role `USER`:

```Bash
$ curl -i -k -u burns:burns https://localhost:8443/articles

HTTP/1.1 403 
Content-Type: application/json
Date: Sun, 09 Feb 2025 20:20:02 GMT

{"timestamp":"2025-02-09T20:20:02.954+00:00","status":403,"error":"Forbidden","path":"/articles"}
```

## References

[YouTube: Secure REST Controllers](https://youtu.be/OYr9HUPmhSw)

*Egon Teiniker, 2017-2025, GPL v3.0*