# Function-Level Authorization

Function-level authorization (a.k.a. method-level security) in the context of REST 
services, is a security mechanism that **controls access to individual functions** or 
methods within your application. 

This approach allows for **fine-grained access control**, enabling you to specify exactly 
who can execute certain operations, often **based on the roles or permissions** assigned 
to the authenticated user.

Unlike coarse-grained access control, which might restrict access at the service or endpoint 
level, function-level authorization allows for more detailed restrictions. 
For example, within a single REST service, you might allow all authenticated users to read 
data (GET requests) but restrict data modification operations (POST, PUT, DELETE requests) 
to users with specific roles or permissions.

## Access the REST Service

Let's run the example:
```
$ mvn spring-boot:run
```

We can access the public `infos` resource **without authentication**.
```
$ curl -ki https://localhost:8443/infos

HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 36
Date: Sat, 16 Oct 2021 08:21:56 GMT

Public API Information (Version 1.0)
```

If we try the same with the `articles` resource, we get an **401 Unauthorized** error.
```
$ curl -ki https://localhost:8443/articles
```

If an authenticated user tries to call a function that is **not released for his role**
(`homer` is not in `ROLE_ADMIN`), he will get a **403 Forbidden** response.
```
$ curl -ki -u homer:homer https://localhost:8443/articles

HTTP/1.1 403 
Vary: Origin
Content-Type: application/json
Date: Mon, 12 Feb 2024 15:29:27 GMT

{"timestamp":"2024-02-12T15:29:27.658+00:00","status":403,"error":"Forbidden","path":"/articles"}
```

A call of an authenticated user with the **correct role** (`burns` is in `ROLE_ADMIN`) results 
in a **200 OK** response.

```
$ curl -i -k -u burns:burns https://localhost:8443/articles
HTTP/1.1 200 
Content-Type: application/json
Date: Mon, 12 Feb 2024 15:30:05 GMT

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":3336}]
```

## Implementation

To secure methods individually, we need to enable method-level security by adding 
`@EnableGlobalMethodSecurity` on a configuration class, specifying the type of security 
you want to enable (`prePostEnabled`, `securedEnabled`, etc.).

```Java
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig
{
}
```

The `prePostEnabled` attribute, when set to `true`, specifically enables the use of
`@PreAuthorize` and `@PostAuthorize` annotations, allowing for expression-based access 
control before and after method execution, respectively.

After enabling method-level security, we can use annotations to secure individual methods. 
For example, `@PreAuthorize` allows us to specify an expression that determines if a method 
can be executed.

```Java
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/articles")
    List<Article> findAll()
    {
        return repository.findAll();
    }
```

The `@PreAuthorize` annotation is used in Spring Security to define access-control expressions 
that are **evaluated before entering a method**, thus determining whether the current user is 
allowed to execute the method based on the specified conditions.
It provides a way to express security constraints on method execution using SpEL 
(Spring Expression Language).

The `@PostAuthorize` annotation in Spring Security is used to enforce security constraints 
after a method has executed, rather than before. It allows us to make access-control decisions 
based on the outcome of the method execution. 
This can be particularly useful when the decision to allow access depends on the value returned 
by the method or the state of the system after the method is executed.



## References
* [Spring @EnableMethodSecurity Annotation](https://www.baeldung.com/spring-enablemethodsecurity)
* [Spring: Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)

*Egon Teiniker, 2017-2025, GPL v3.0*