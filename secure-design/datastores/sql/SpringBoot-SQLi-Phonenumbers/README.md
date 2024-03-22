# SpringBoot Basic Authentication

## Setup HTTP Basic Authentication

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
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // User Creation
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder)
    {
        // InMemoryUserDetailsManager
        UserDetails admin = User.withUsername("burns")
                .password(encoder.encode("burns"))
                .build();

        UserDetails user = User.withUsername("homer")
                .password(encoder.encode("homer"))
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
```

* The `filterChain()` method configures a `SecurityFilterChain`. The `SecurityFilterChain` is 
    essentially a chain of filters that Spring Security uses to apply various security 
    rules to HTTP requests. The method takes an instance of `HttpSecurity` as a parameter, 
    which is a builder object used to configure the security directives that the filter 
    chain should enforce:
    * `@Bean`: This annotation tells Spring that the method returns an object that 
        should be registered as a bean in the Spring application context. 
        Beans are objects that are managed by Spring and can be injected into 
        other parts of the application as dependencies.

    * `csrf().disable()`: This line disables Cross-Site Request Forgery (CSRF) 
      protection for the application (see web applications). 

    * `authorizeRequests()`: This method call begins the configuration of authorization 
      rules for HTTP requests.

    * `anyRequest().authenticated()`: This line specifies that any request to the 
      application must be authenticated. 

    * `httpBasic()`: This part configures HTTP Basic Authentication for the application. 
      HTTP Basic Authentication is a simple authentication scheme built into the HTTP 
      protocol. It sends user credentials in the request header, making it easy to 
      implement but less secure than other methods unless used in conjunction with HTTPS. 

    * `build()`: Finally, this line builds the SecurityFilterChain instance using the 
      configured HttpSecurity object and returns it. This constructed filter chain is 
      then used by Spring Security to enforce the specified security rules on HTTP requests 
      to the application.

* The `passwordEncoder()` method returns an instance of `BCryptPasswordEncoder`.
  Inside this method, a new instance of `BCryptPasswordEncoder` is created and returned.
  `BCryptPasswordEncoder` is a class provided by Spring Security that implements password
  hashing using the BCrypt strong hashing function.

* The `userDetailsService()` method  is also annotated with `@Bean`. 
  This method configures an in-memory user using the `InMemoryUserDetailsManager`.
  * The `UserDetails` object is created using the `User.builder()` method and 
  specifying the desired `username` and `password`.
  * The `InMemoryUserDetailsManager` is instantiated with the created `UserDetails` objects.


### Access the REST Service

Let's run the example:
```
$ mvn spring-boot:run
```

First, when we access the service **without authentication** we get a **401 Unauthorized** response message.
```
$ curl -i -k https://localhost:8443/articles

HTTP/1.1 401 
WWW-Authenticate: Basic realm="Realm"
Content-Type: application/json
Date: Fri, 15 Oct 2021 19:06:27 GMT

{"timestamp":"2021-10-15T19:06:27.008+00:00","status":401,"error":"Unauthorized","path":"/articles"}
```

Using the `--user` or `-u` parameter of `curl`, we can send a request with Basic Authentication.

```
$ curl -i -k -u homer:homer https://localhost:8443/articles

HTTP/1.1 200 
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
Content-Type: application/json
Date: Sat, 16 Oct 2021 07:18:26 GMT

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":3336}]s
```

In the request message, we can see the additional HTTP header:

```
GET /articles HTTP/1.1
Host: localhost:8443
Authorization: Basic aG9tZXI6aG9tZXI=
User-Agent: curl/7.74.0
Accept: */*
```

When we use a **base64 decoder** we can see that `aG9tZXI6aG9tZXI=` contains the username and the password. 

```
$ echo 'aG9tZXI6aG9tZXI=' | base64 --decode
```


## References

* [Spring Security Implementation With Basic Auth](https://medium.com/@aamir.zaidi5/spring-security-implementation-805520a297d5)

* [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
* [YouTube: HTTP Basic Authentication using Spring Security](https://youtu.be/hF-iMHpl970)

* [HTTP Authentication: Basic and Digest Access Authentication](https://www.ietf.org/rfc/rfc2617.txt)

*Egon Teiniker, 2017-2024, GPL v3.0*
