# Web Application Access Control 

## Build and Run

We build and run the application like any Spring-Boot application:
```
$ mvn spring-boot:run 
```

## Accessing the Web Application

The running web application can be accessed by a browser or a command line tool:
```
URL: https://localhost:8443/index.html
```

Access to these web pages is permission-based:
* **index.html**: no login needed
* **Info page**: login as USER (homer/homer)
* **Configuration page**: login as ADMIN (burns/burns)

To **change the user**, we have to **logout** first.


## Implementation

This web application consists only of static HTML pages. These pages are arranged according to 
roles (`user` and `admin`).
```
$ tree src/main/resources/
src/main/resources/
├── application.properties
├── server.jks
├── static
│       ├── admin
│       │    └── configuration.html
│       ├── index.html
│       └── user
│            └── info.html
└── templates
    └── error.html
```

We can only access the static HTML files if we log in as a user with 
the right role.

Note that with a login a new `JSESSIONID` cookie will be created and with 
a logout, this cookie will be invaldated.

## Spring Security Configurations

### Transport Layer Security (TLS) 

```Java


```

### Authentication and Authorization 

First, we define users with usernames and passwords and assign roles to them.

```Java
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder)
    {
        UserDetails admin = User.withUsername("burns")
                .password(encoder.encode("burns"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("homer")
                .password(encoder.encode("homer"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
```

In the `SecurityFilterChain`, we enable **form-based login** and the corresponding **logout**
functionality (**authentication**).

`RequestMatchers` define which resources each role may access (**authorization**).

```Java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) ->form
                        .defaultSuccessUrl("/index.html")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/index.html")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return httpSecurity.build();
    }
```

Note that we have **disabled the CSRF tokens** for now.

## Resources

*Egon Teiniker, 2017-2026, GPL v3.0*