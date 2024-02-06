# Securing a Microservice with OAuth 2.0

The following examples are reused from **chapter 2** of the **Microservices Security in Actions** book. 
You can find all examples of this book on [GitHub](https://github.com/microservices-security-in-action/samples).
This document describes the main steps needed to make the examples run and discusses the output 
of the experiments.

The following examples are based on the **Spring Boot** framework and can be build using **Maven**. 

Note that **all samples use HTTP** (not HTTPS) endpoints to make it possible to inspect messages being 
passed on the network.
**In production systems, HTTPS should be used for any endpoint.**

## Setting Up an OAuth 2.0 Authorization Server

**OAuth 2.0** is a clean mechanism for solving the problems related to providing our
username and password to an application that we don't trust to access our data.

Here is the OAuth 2.0 flow:
* **End users** are the direct consumers of client applications. They do not access microservices 
  directly.

* The **client application** can be a web application, a mobile application, and so on.

* The client application gets a token from the **authorization server** to access the 
microservice.

* Client applications consume microservices on behalf of the end user.

* According to OAuth 2.0 terminology, the microservice acts as a **resource server**.

* The microservice talks to the authorization server to **validate the access token** it
gets from the client application.

To build and run the OAuth 2.0 authorization server, type:
```
$ cd OAuth2-Server
$ mvn spring-boot:run
```
The OAuth 2.0 authorization server runs on **HTTP port 8085**.
We can change that in the `src/main/resources/application.properties` file:
```
server.port: 8085
server.error.whitelabel.enabled:false
security.oauth2.resource.filter-order: 3
security.oauth2.client.authenticationScheme: header
```

To get an access token from the authorization server, make an HTTP request to the server.
```
$ curl -i -u orderprocessingapp:orderprocessingappsecret -H "Content-Type: application/json" -d '{"grant_type":"client_credentials", "scope":"read write"}' http://localhost:8085/oauth/token

POST /oauth/token HTTP/1.1
Host: localhost:8085
Authorization: Basic b3JkZXJwcm9jZXNzaW5nYXBwOm9yZGVycHJvY2Vzc2luZ2FwcHNlY3JldA==
User-Agent: curl/7.64.0
Accept: */*
Content-Type: application/json
Content-Length: 57

HTTP/1.1 200
Cache-Control: no-store
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 12:55:56 GMT

{
    "access_token":"8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3",
    "token_type":"bearer",
    "expires_in":3573,
    "scope":"read write"
}
```
The **-u flag** provided to curl instructs it to create a **basic authentication header**.
Basic authentication at this point is used only for obtaining the OAuth token
required to access the microservice, from the authorization server.

What follows the **-d flag** is the actual JSON content of the message, which goes in the 
**HTTP body**.
The `grant_type` specifies the protocol to be followed in issuing the token.
The `scope` declares what actions the application intends to perform with a token.

The following list provides details on the JSON response from the authorization server:
* **access_token**: The value of the token issued by the authorization server to the client 
application.
* **token_type**: The token type, most of the OAuth deployments use bearer tokens.
* **expires_in**: The period of validity of the token, in seconds. The token will be considered invalid 
after this period.
* **scope**: The actions that the token is permitted to perform on the resource server 
(microservice).


## Securing a Microservice with OAuth 2.0

Once secured with OAuth 2.0, the microservice expects a valid access token from the
calling client application. Then it will validate this token with the assistance of
the authorization server before it grants access to its resources.

We can build and run the service:
```
$ cd OrderService
$ mvn spring-boot:run
```

If we run the same curl command as we used earlier, we get an error message 
(unauthorized) saying that the request was unsuccessful.
```
$ curl -v http://localhost:8080/orders -H 'Content-Type: application/json' -d '{"items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}'

HTTP/1.1 401
Cache-Control: no-store
Pragma: no-cache
WWW-Authenticate: Bearer realm="sample-oauth", error="unauthorized", error_description="Full authentication is required to access this resource"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 13:34:25 GMT
```
The microservice can no longer be accessed without a valid access token obtained from
the authorization server.

We can see that in the `OrderProcessingService` class:
* **@EnableWebSecurity**: This annotation informs the Spring Boot runtime to apply security to
the resources of this microservice.
* **tokenServices()**: Returns a ResourceServerTokenService object to communicate with the
authorization server to validate credentials received by the microservice.
These credentials are hardcoded in the simple OAuth server we use.

Before a client application can access our microservice, it should obtain an OAuth 2.0 
access token from the authorization server.
```
$ curl -i -u orderprocessingapp:orderprocessingappsecret -H "Content-Type: application/json" -d '{"grant_type":"client_credentials", "scope":"read write"}' http://localhost:8085/oauth/token

HTTP/1.1 200
Cache-Control: no-store
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 13:42:19 GMT

{"access_token":"8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3","token_type":"bearer","expires_in":790,"scope":"read write"}
```
If the request is successful, we get an access token in response.
We need to send the token as an HTTP header named Authorization, and the header value 
needs to be prefixed by the string Bearer:
```
$ curl -v http://localhost:8080/orders -H 'Content-Type: application/json' -H 'Authorization: Bearer 8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3' -d '{"items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}'

POST /orders HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.64.0
Accept: */*
Content-Type: application/json
Authorization: Bearer 8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3
Content-Length: 182


HTTP/1.1 201
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 13:45:01 GMT

{"orderId":"500745a3-ba85-4c4d-abf1-a3fba6804dca","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

After a **timout of the access token**, we get for the same HTTP request:
```
HTTP/1.1 401
Cache-Control: no-store
Pragma: no-cache
WWW-Authenticate: Bearer realm="sample-oauth", error="invalid_token", error_description="8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 14:21:20 GMT

{"error":"invalid_token","error_description":"8d2e469c-e9a6-4ae4-94b2-5f9c3595c7a3"}
```

Note that the access token that the client sent in the HTTP header to the microservice
was validated against the authorization server. This process is called **token inspection**.

The **authorization server** we use contains two applications: one with client ID 
`orderprocessingapp` and one with the client ID `orderprocessingservice`, as we can
see in the `OAuthServerConfig` class:

```
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("orderprocessingapp")
            .secret(passwordEncoder.encode("orderprocessingappsecret"))
            .authorizedGrantTypes("client_credentials", "password")
            .scopes("read", "write")
            .accessTokenValiditySeconds(3600)
            .resourceIds("sample-oauth")
        .and()
        .withClient("orderprocessingservice")
            .secret(passwordEncoder.encode("orderprocessingservicesecret"))
            .authorizedGrantTypes("client_credentials", "password")
            .scopes("read")
            .accessTokenValiditySeconds(3600)
            .resourceIds("sample-oauth");
}
```
The client ID `orderprocessingapp` has privilegs to obtain both scopes `read` and `write`,
whereas the second client ID `orderprocessingservice` has privileges to obtain only
scope `read`.

Now we execute a **HTTP request to obtain an access token** with `orderprocessingservice`
as the client ID:
```
$ curl -i -u orderprocessingservice:orderprocessingservicesecret -H "Content-Type: application/json" -d '{"grant_type":"client_credentials", "scope":"read"}' http://localhost:8085/oauth/token
HTTP/1.1 200
Cache-Control: no-store
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 14:25:10 GMT

{"access_token":"8a54259a-df5e-486f-a901-ecd475e6e873","token_type":"bearer","expires_in":3599,"scope":"read"}
```

In the **microservice implementation**, the class `ResourceServerConfig` forces the scopes
on the resources it wants to protect.
```
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN_WRITE = "/orders/**";
    private static final String SECURED_PATTERN_READ = "/orders/{id}";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers(SECURED_PATTERN_WRITE).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN_WRITE).access(SECURED_WRITE_SCOPE)
                .antMatchers(HttpMethod.GET, SECURED_PATTERN_READ).access(SECURED_READ_SCOPE);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("sample-oauth");
    }
}
```
This code instructs the microservice runtime to check for the relevant scope
for the particular HTTP method and request path.

Using the new access token, we can send a **GET request** to the microservice:
```
$ curl -v http://localhost:8080/orders/500745a3-ba85-4c4d-abf1-a3fba6804dca -H 'Content-Type: application/json' -H 'Authorization: Bearer 8a54259a-df5e-486f-a901-ecd475e6e873'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 14:27:31 GMT

{"orderId":"500745a3-ba85-4c4d-abf1-a3fba6804dca","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

But when we try to use a **POST request**, the response says that the token's scope for
this particular operation is insufficient and that the required scope is `write`:
```
$ curl -v http://localhost:8080/orders -H 'Content-Type: application/json' -H 'Authorization: Bearer 8a54259a-df5e-486f-a901-ecd475e6e873' -d '{"items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}'

HTTP/1.1 403
Cache-Control: no-store
Pragma: no-cache
WWW-Authenticate: Bearer error="insufficient_scope", error_description="Insufficient scope for this resource", scope="write"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 14:28:24 GMT

{"error":"insufficient_scope","error_description":"Insufficient scope for this resource","scope":"write"}
```

## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
  * Chapter 2: First Steps in Securing Microservices
  
*Egon Teiniker, 2020-2021, GPL v3.0*
