# Securing an API Gateway with OAuth2.0

The following examples are reused from **chapter 3** of the **Microservices Security in Actions** book. 
You can find all examples of this book on [GitHub](https://github.com/microservices-security-in-action/samples).
This document describes the main steps needed to make the examples run and discusses the output 
of the experiments.

The examples are based on the **Spring Boot** framework and can be build using **Maven**.

Note that **all samples use HTTP** (not HTTPS) endpoints to make it possible to inspect messages being 
passed on the network. **In production systems, HTTPS should be used for any endpoint.**


## Setting Up a Microservice 

We start with a **simple microservice**:
```
$ cd OrderService
$ mvn spring-boot:run
```

We can send a **HTTP POST request**:
```
$ curl -v http://localhost:8080/orders -H 'Content-Type: application/json' -d '{"items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}'

HTTP/1.1 201
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:53:35 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
And we can send a **HTTP GET request** using `curl`:
```
$ curl -i http://localhost:8080/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:55:48 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

## Enforcing OAuth 2.0 at the Edge

The next step is enforcing security on the Zuul gateway so that **only authenticated 
clients** are granted access to the microservice.

First, we need an **OAuth 2.0 authorization server**:
```
$ cd OAuth2Server
$ mvn spring-boot:run
```

When the authorization server starts successfully, we can request tokens from it via 
the Zuul gateway, so we have to run a **new Zuul gateway**:
```
$ cd APIGateway
$ mvn spring-boot:run
```

Here are the settings in the `application.properties` of the new Zuul proxy:
```
zuul.routes.retail.url=http://localhost:8080
zuul.routes.token.url=http://localhost:8085
zuul.sensitiveHeaders=
ribbon.eureka.enabled=false
server.port=9090
authserver.introspection.endpoint=http://localhost:8085/oauth/check_token
```
Note that Zuul is pointing to the **token validation endpoint** of the authorization server.
Zuul talks to this endpoint to validate tokens.

Once the gateway has started successfully on port 9090, we can **request an access
token** from the authorization server, through the Zuul gateway.
```
$ curl -i -u application1:application1secret -H "Content-Type: application/json" -d '{"grant_type":"client_credentials", "scope":"read write"}' http://localhost:9090/token/oauth/token

HTTP/1.1 200
Cache-Control: no-store
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Date: Thu, 19 Nov 2020 17:28:36 GMT
Keep-Alive: timeout=60
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

{"access_token":"dc7b8405-7ceb-474d-b2e6-162d79766b8b","token_type":"bearer","expires_in":3599,"scope":"read write"}
```

Once the client application gets an access token from the token endpoint of the
authorization server, the client application accesses the microservice via the Zuul
gateway with this token.

The purpose is to make the gateway enforce all security-related policies while the
microservice focuses only on the business logic it executes.

If we access the microservice through the Zuul gateway as before (**without access token**), 
we get an error message:
```
$ curl -v http://localhost:9090/retail/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd

HTTP/1.1 401
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 17:34:20 GMT

{"error": true, "reason":"Authentication Failed"}
```
This error message confirms that the Zuul gateway, with OAuth 2.0 security 
screening enforced, doesn't allow any request to pass through it without a 
valid token.
Zuul no longer grants open access to resources on the resource server (microservice).
It mandates authentication.

We are therefore required to use a **valid access token**, we just obtained, 
to access the microservice:
```
$ curl -v http://localhost:9090/retail/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd -H 'Content-Type: application/json' -H 'Authorization: Bearer dc7b8405-7ceb-474d-b2e6-162d79766b8b'

GET /retail/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd HTTP/1.1
Host: localhost:9090
User-Agent: curl/7.64.0
Accept: */*
Content-Type: application/json
Authorization: Bearer dc7b8405-7ceb-474d-b2e6-162d79766b8b

HTTP/1.1 200
Date: Thu, 19 Nov 2020 17:36:27 GMT
Keep-Alive: timeout=60
Content-Type: application/json
Transfer-Encoding: chunked

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

## Conclusion
As we can see from this example:
* The client application sends an OAuth 2.0 access token as a header 
to the Zuul gateway on the path on which the microservice is exposed (`/retail/orders`).
* The gateway extracts the token from the header and introspects it through the 
authorization server.
* The authorization server responds with a valid or invalid status message. 
* If the status is valid, the gateway allows the request to be passed to the microservice.

To do this in **Zuul**, we use a **request filter**, which intercepts requests and performs 
various operations on them (`OAuthFilter`): 
```Java
String oauthServerURL = env.getProperty("authserver.introspection.endpoint");

try
{
    URL url = new URL(oauthServerURL);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setRequestProperty("Authorization", "Basic YXBwbGljYXRpb24xOmFwcGxpY2F0aW9uMXNlY3JldA==");
    String urlParameters = "token=" + token;

    //Send post request to authorization server to validate token
    connection.setDoOutput(true);
    outputStream = new DataOutputStream(connection.getOutputStream());
    outputStream.writeBytes(urlParameters);

    int responseCode = connection.getResponseCode();

    //If the authorization server doesn't respond with a 200.
    if (responseCode != 200) 
    {
        log.error("Response code from authz server is " + responseCode);
        handleError(requestContext);
    }
```
When all format checks are done, the gateway talks to the authorization server to
check whether the token is valid.
If the authorization server responds with an **HTTP response status code of 200**, 
it is a **valid token**.
If the server doesn't respond with 200, the authentication has failed.


## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
    * Chapter 3: Securing north/south traffic with an API gateway

*Egon Teiniker, 2020 - 2021, GPL v3.0*
