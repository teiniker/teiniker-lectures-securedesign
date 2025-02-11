# SpringBoot Basic Authentication

## Access the REST Service

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

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":3336}]
```

In the request message, we can see the additional HTTP header:

```
$ curl -v -k -u homer:homer https://localhost:8443/articles

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


## Setup HTTP Basic Authentication

We use the **Spring Security** module to realize Basic Authentication.
Thus, we include a dependency to `pom.xml`:

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
	<dependency>
	    <groupId>org.springframework.security</groupId>
	    <artifactId>spring-security-config</artifactId>
	</dependency>
```

Now we can add a `SecurityConfig` class. All security related settings can be found there:

```Java 
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
```

Let's break down the code snippet step by step:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

The code creates a bean that returns a `BCryptPasswordEncoder`. This bean can then 
be injected into any component of your Spring application that requires password 
encoding (for example, when registering a new user or authenticating an existing one). 
By doing so, it centralizes the configuration of password encoding, ensuring 
consistency and security across your application.

* **PasswordEncoder**:  
  In Spring Security, the `PasswordEncoder` interface defines the contract for 
  encoding passwords. It provides methods to:
  * **Encode a password:** Convert a plaintext password into a hashed format.
  * **Verify a password:** Compare a plaintext password against a stored hashed 
  password to check if they match.

* **BCryptPasswordEncoder:**  
  This is a concrete implementation of the `PasswordEncoder` interface that uses 
  the BCrypt hashing function.
  BCrypt is widely regarded as a secure way to hash passwords because it incorporates 
  a salt (a random value added to the password before hashing) and is adaptive. 
  The adaptive nature means you can increase the work factor (i.e., computational cost) 
  over time as hardware improves, making brute-force attacks more difficult.


```Java 
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
    {
        UserDetails admin = User.withUsername("burns")
                .password(passwordEncoder.encode("burns"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("homer")
                .password(passwordEncoder.encode("homer"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
```

The method returns an object that implements the `UserDetailsService` interface, 
which is a core component in Spring Security responsible for retrieving 
user-related data.

The method creates two in-memory users by utilizing a builder pattern provided 
by the `User` class:
* **Username**: The username is set to "burns".
* **Password**: The password is set to "burns", but before storing it, the password 
  is encoded using the provided `PasswordEncoder`.
  * **Encoding**: This ensures that the password is stored in a secure, hashed format 
    rather than plain text.
  * **Roles**: The user is assigned the role "ADMIN". Roles help define the user's 
    authorities and permissions within the application.

* **InMemoryUserDetailsManager**:
  This is a Spring Security implementation of `UserDetailsService` that stores 
  user details in memory.
  The returned `InMemoryUserDetailsManager` holds the two defined users (admin and 
  user), making them available for authentication.

  When a user attempts to authenticate, Spring Security will use this 
  `UserDetailsService` to load the user details (username, password, roles) and 
  verify the credentials.

```Java 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
```

The method is named `securityFilterChain()` and returns a `SecurityFilterChain`. 
This object encapsulates the **filter chain used by Spring Security to process and 
secure HTTP requests**.

The method takes an `HttpSecurity` object as a parameter. Spring automatically provides 
this object, which serves as a **builder for configuring web-based security**.

* **CSRF (Cross-Site Request Forgery)**:
  CSRF is a security measure that helps prevent unwanted actions on behalf of an 
  authenticated user.

  In this configuration, **CSRF protection is disabled**. This might be appropriate 
  for stateless applications (like REST APIs) or where CSRF protection is handled 
  differently.

* **Authenticate HTTP Requests**:
  This line configures how HTTP requests are authenticated.
  * `.anyRequest().authenticated()`:
  This specifies that **every incoming HTTP request must be authenticated**. 

* **HTTP Basic Authentication**:
  This line sets up HTTP Basic authentication, a simple authentication scheme built 
  into the HTTP protocol.
  * `Customizer.withDefaults()`:
    This applies the default configuration for HTTP Basic authentication. 
    With HTTP Basic, the client must send an Authorization header with each request, 
    which includes a Base64-encoded username and password.

* **Building the Filter Chain**:
  The `build()` method finalizes the configuration and returns the 
  `SecurityFilterChain` object.

  The constructed `SecurityFilterChain` is then registered as a bean in the 
  Spring context. Spring Security uses this bean to apply the defined security 
  rules to all incoming HTTP requests.

## References

* [Spring Security Implementation With Basic Auth](https://medium.com/@aamir.zaidi5/spring-security-implementation-805520a297d5)

* [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
* [YouTube: HTTP Basic Authentication using Spring Security](https://youtu.be/hF-iMHpl970)

* [HTTP Authentication: Basic and Digest Access Authentication](https://www.ietf.org/rfc/rfc2617.txt)

*Egon Teiniker, 2017-2025, GPL v3.0*
