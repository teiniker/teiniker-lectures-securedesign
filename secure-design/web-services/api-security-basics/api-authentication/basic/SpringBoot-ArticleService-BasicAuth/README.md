# Example: SpringBoot Basic Authentication

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
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("homer")
                    .password(passwordEncoder().encode("homer"))
                    .roles("ROLE_USER")
                .and()
                .withUser("marge")
                    .password(passwordEncoder().encode("marge"))
                    .roles("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
```

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

* [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
* [YouTube: HTTP Basic Authentication using Spring Security](https://youtu.be/hF-iMHpl970)

* [HTTP Authentication: Basic and Digest Access Authentication](https://www.ietf.org/rfc/rfc2617.txt)

*Egon Teiniker, 2020 - 2022, GPL v3.0*
