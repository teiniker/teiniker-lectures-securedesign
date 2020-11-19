# Securing an API Gateway

The following examples are reused from **chapter 3** of the **Microservices Security in Actions** book. 
You can find all examples of this book on [GitHub](https://github.com/microservices-security-in-action/samples).
This document describes the main steps needed to make the examples run and discusses the output 
of the experiments.

The following examples are based on the **Spring Boot** framework and can be build using **Maven**.

Note that **all samples use HTTP** (not HTTPS) endpoints to make it possible to inspect messages being 
passed on the network.
**In production systems, HTTPS should be used for any andpoint.**

## The Need for an API Gateway
The API gateway is an important piece of infrastructure in our microservice architecture,
since it plays a critical role that helps us clearly separate the functional requirements 
from nonfunctional ones.

Microservices are behind a set of APIs that is exposed to the outside world via an API 
gateway.
The API gateway is the entry point to the microservice deployment, which screens all 
incoming messages for security and other QoS features.

One key aspect of microservices best practice is the 
**single responsibility principle (SRP)**.
Each microservice should be performing only one particular function.
An API gateway helps in **decoupling security from a microservice**.

## Securing at the Edge

**Basic authentication** allows a user with a valid username and password to access 
a microservice via an API.
This model fails to meet access delegation requirements for a variety of reasons:
* _No restrictions on what the application can do_. After an application gets access to 
the username and password of a user, it can do everything that users can do with the
microservice.
* _The username and password are static, long-living credentials_. If a user provides a 
username and password to an application, the application needs to retain this information
for that particular user session to access the microservice.
The time during which this information needs to be retained could be as long as the 
application decides.

**Mutual Transport Layer Security (mTLS)** is a mechanism by which a client application 
verifies a server and the server verifies the client application by exchanging 
certificates and proving that each one owns the corresponding private keys.
When we use mTLS, the corresponding private key never leaves its owner - or is never
passed over the wire.
But, just as basic authentication, mTLS fails to meet access delegation requirements.
mTLS is mostly used to secure communication between a client application and a microservice, 
or communication among microservices.

**OAuth 2.0**, which is designed for access delegation, fits best in securing microservices 
at the edge.


## Setting Up an API Gateway 
Zuul is an open source proxy server build by Netflix, acting as the entry point for
all of the company's backend streaming applications.

We start with a **simple microservice**:
```
$ cd sample01
$ mvn spring-boot:run
```

We can send a **HTTP POST request**:
```
$ curl -v http://localhost:8080/orders -H 'Content-Type: application/json' --data-binary @- <<EOF
{
"items":[
    {
        "itemCode":"IT001",
        "quantity":3
    },
    {
        "itemCode":"IT004",
        "quantity":1
    }
    ],
    "shippingAddress":"No 4, CA, USA"
}
EOF

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

To compile and run the **Zuul proxy** usinh Maven:
```
$ cd sample02
$ mvn spring-boot:run
```

Now we can access our microservice through the Zuul proxy:
```
$ curl -i http://localhost:9090/retail/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd

HTTP/1.1 200
Date: Thu, 19 Nov 2020 17:06:04 GMT
Keep-Alive: timeout=60
Content-Type: application/json
Transfer-Encoding: chunked

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
Note several important points in this request:
* The **port** has switched to **9090**, which is the port of the Zuul proxy.
* The request URL now starts with /retail, which is the base path in Zuul that 
we have configred to route requests to the microservice.

To see how the routing is configured, review the `src/main/resources/application.properties` 
file:
```
zuul.routes.retail.url=http://localhost:8080
zuul.routes.token.url=http://localhost:8085
zuul.sensitiveHeaders=
ribbon.eureka.enabled=false
server.port=9090
```

## Enforcing OAuth 2.0 at the Zuul Gateway
The next step is enforcing security on the Zuul gateway so that **only authenticated 
clients** are granted access to the microservice.

First, we need an **OAuth 2.0 authorization server**:
```
$ cd sample03
$ mvn spring-boot:run
```

When the authorization server starts successfully, we can request tokens from it via 
the Zuul gateway.

We did not enforce OAuth 2.0 security screening at the Zuul gateway, which we started 
from `sample02`. So we **need to stop** it and build and run a **new Zuul gateway**:
```
$ cd sample04
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

As we can see from this example:
* The client application sends an OAuth 2.0 access token as a header 
to the Zuul gateway on the path on which the microservice is exposed (`/retail/orders`).
* The gateway extracts the token from the header and introspects it through the 
authorization server.
* The authorization server responds with a valid or invalid status message. 
* If the status is valid, the gateway allows the request to be passed to the microservice.

To do this in Zuul, we use a **request filter**, which intercepts requests and performs 
various operations on them (`OAuthFilter`): 
```Java
String oauthServerURL = env.getProperty("authserver.introspection.endpoint");

try {
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
    if (responseCode != 200) {
        log.error("Response code from authz server is " + responseCode);
        handleError(requestContext);
    }
```
When all format checks are done, the gateway talks to the authorization server to
check whether the token is valid.
If the authorization server responds with an **HTTP response status code of 200**, 
it is a **valid token**.
If the server doesn't respond with 200, the authentication has failed.

## Securing Communication Between Zuul and the Microservice
We have to consider what happens if someone accesses the microservice directly,
**bypassing the API gateway** layer.

First and formost, we need to make sure that our microservice isn't directly 
exposed to external clients. We need to make sure that it sits behind an organisation's
**firewall**.
No external client get access to our microservice unless they come in via the API gateway.

We need to build a mechanism in which the microservice rejects any request comming from
clients other than teh API gateway.
The standard way is to enable **mTLS** between the API gateway and the microservice.

Note that **mTLS verification happens at the transport layer** of the microservice and doesn't 
propagate up to the application layer.
Developers don't have to write any application logic to handle the client verification,
which is done by the underlying transport-layer implementation.

## References
Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
