# Example: CSRF - Shopping Card

This example shows how an CSRF attack will word.
It starts with a **Shoping Cart** application which uses a **HTTP session** 
to store products. 

## Shopping Cart 

* We run the SpringBoot application and access its index page:

    ```Bash
    $ cd SpringBoot-MVC-ShoppingCart
    $ mvn spring-boot:run
    
    URL: https://localhost:8443/index.html
    ```

* From there, we navigate to the shopping cart.

* We have to login to use the shopping cart (homer:homer). 
  Together with the login, a new session is created (see the `Set-Cookie`
  header in the response).

* Now we can add products with their quantities to the shopping cart...


## CSRF Attack 

A SCRF attack works by submitting a crafted web form to an existing session.
To do that, we need a second web application called `SpringBoot-MVC-Attack`:

```Bash
$ cd SpringBoot-MVC-Attack
$ mvn spring-boot:run

URL: http://localhost:8080/index.html
```

In a more realistic example, this link would be provided via an email
to the user.

Anyway, the user opens the money page and clicks the `[Add]` button.
In this moment the hidden HTML form data will be submitted to the 
existing `ShoppingCart` session.

Because this is a known session, the browser will be happy to add the 
right cookie to the malicious request.

The `ShoppingCart` session cannot that this request is malicious 
because it comes from the user's browser and carries the right session
cookie.  

Finally, **a new product will be added to the shopping cart**.


## How to Fix the CSRF Problem 

Spring Security comes with a build-in support for CSRF tokens.

```Java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                //.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) ->form
                        .defaultSuccessUrl("/index.html")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return httpSecurity.build();
    }
```

Here we just remove the `csrf(csrf -> csrf.disable())` configuration 
to activate the CSRF protection.

With this configuration, each HTML form will be extended by a hidden 
input field which is named `_csrf`:

```HTML
<input name="_csrf" type="hidden" value="GBeTdmhiGkC3KvCQTea9zXqS1Xsh7nVil0KKwrURLrsgUysYKSChEgwBKXGaSMSlecuJ_Rmn-BlD3kFPpnfo9tEoH4pFZhot" />
```

When the HTML form is submitted back to the server, the hidden filed 
will be added automatically by the browser.
A Spring Security filter will check the value of this token.
If there is no token or an invalid one, the request will be rejected.

*Egon Teiniker, 2017-2025, GPL v3.0*