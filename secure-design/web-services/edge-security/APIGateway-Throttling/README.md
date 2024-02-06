# Throttling at the API Gateway with Zuul

The following examples are reused from **chapter 5** of the **Microservices Security in Actions** book. 
You can find all examples of this book on [GitHub](https://github.com/microservices-security-in-action/samples).
This document describes the main steps needed to make the examples run and discusses the output 
of the experiments.

The following examples are based on the **Spring Boot** framework and can be build using **Maven**.

Note that **all samples use HTTP** (not HTTPS) endpoints to make it possible to inspect messages being 
passed on the network.
**In production systems, HTTPS should be used for any andpoint.**


## Deployment

The following **Spring Boot applications** must be started in **separate shells**:

```
$ cd APIGateway-Throttling/OrderService
$ mvn spring-boot:run

$ cd APIGateway-Throttling/OAuth2Server
$ mvn spring-boot:run

$ cd APIGateway-Throttling/APIGateway
$ mvn spring-boot:run

```

## Accessing the Microservice via the API Gateway

First, we **request an access token** from the OAuth 2.0 server:
```
$ curl -u application1:application1secret -H "Content-Type: application/json" -d '{"grant_type":"client_credentials", "scope":"read write"}' http://localhost:9090/token/oauth/token

{"access_token":"f8685e59-f370-450e-965b-39d2a20563c5","token_type":"bearer","expires_in":3389,"scope":"read write"}
```

Once we have the token, we can **access the Order Processing micrservice** 
via the **Zuul API gateway**. We run the following `curl` command to do an **HTTP POST** 
to the Order processing microservice:
```
$ curl -v http://localhost:9090/retail/orders -H 'Content-Type: application/json' -H 'Authorization: Bearer f8685e59-f370-450e-965b-39d2a20563c5' --data-binary @- <<EOF
{ "customer_id":"101021",
"payment_method":{
    "card_type":"VISA",
    "expiration":"01/22",
    "name":"John Doe",
    "billing_address":"San Jose, CA"
    },
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

{{"orderId":"c69d2104-ac8b-46ed-8a49-3fd2d8d68437","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
Make sure to use the value of the same access token we received from the previous request.

Next, let us use the `orderId` to query our order information via an **HTTP GET** request:
```
$ curl -i http://localhost:9090/retail/orders/c69d2104-ac8b-46ed-8a49-3fd2d8d68437 -H 'Content-Type: application/json' -H 'Authorization: Bearer f8685e59-f370-450e-965b-39d2a20563c5'

HTTP/1.1 200
Date: Thu, 26 Nov 2020 18:56:38 GMT
Keep-Alive: timeout=60
Content-Type: application/json
Transfer-Encoding: chunked

{"orderId":"c69d2104-ac8b-46ed-8a49-3fd2d8d68437","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
Make sure to use the same access token und the same `orderId` as before.

## Verifying Zuul's Throttling Filter

Execute the same **HTTP GET** request **many times** within a minute, we see a response
with the **status code 429** saying the **request is throttled out**:
```
$ curl -i http://localhost:9090/retail/orders/c69d2104-ac8b-46ed-8a49-3fd2d8d68437 -H 'Content-Type: application/json' -H 'Authorization: Bearer f8685e59-f370-450e-965b-39d2a20563c5'

HTTP/1.1 429
Transfer-Encoding: chunked
Date: Thu, 26 Nov 2020 18:57:44 GMT

{"error": true, "reason":"Request Throttled."}
```
The duration of the time window is configured in a Java class. We introducted another **Zuul filter** on
our gateway that **executes after the security filter** to enforce throttling limits.

The Java class that implements throttling is **ThrottlingFilter.java** which an be found inside
the `src/main/.../filters` directory.

The **filterType()** method returns **"pre"** which indicates that this filter should 
be executed before the request being processed.

The **filterOrder()** method returns **2** which defines that this filter should be executed after 
the OAuthFilter (whose filter-order is set to 1).

The **shouldFilter()** method returns **true** which indicates that this filter is active.

At the top of the ThrottlingFilter class, we initialize a counter of the `CounterCache`
type, which is an in-memory map. Each entry in this map holds a counter against its 
key. And each entry in the map resides for approximately 60 seconds, after which
it is removed.
```Java
    // Create a counter cache where each entry expires in 1 minute and the cache is 
    // cleared every 10 seconds.
    // Maximum number of entries in the cache would be 10000.
    private CounterCache counter = new CounterCache(60, 10, 10000);
```

The key in this map is quite important and is what we count our requests against.
In this particular example, **we use the access token as the key** to count against.

Since the access token itself is kind of a secret, we might be able to use its **hashed
value** rather than using it as it is.
```Java
    @Override
    public Object run() {

        log.info("Executing Throttling Filter");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //Avoid throttling the token endpoint
        if (request.getRequestURI().startsWith("/token")) {
            return null;
        }

        //Get the value of the Authorization header.
        String authHeader = request.getHeader("Authorization");

        //If the Authorization  header doesn't exist or is not in a valid format.
        if (StringUtils.isEmpty(authHeader)) {
            log.warn("No auth header found, not throttling");
            return null;
        }

        //Get the value of the token by splitting the Authorization header
        String key = authHeader.split("Bearer ")[1];
        log.info("Checking key.." + key);
        Object count = counter.get(key);

        //If key doesn't exist in cache.
        if (count == null) {
            log.info("Counter doesn't exist, putting count as 1");
            //Put count to cache as 1
            synchronized (key) {
                counter.put(key, 1);
            }
        }
        //If count is greater than or equal to 5
        else if ((int) count >= 5) {
            log.info("Counter is greater than 5. Returning error");
            //Quota exceeded. Return error
            handleError(requestContext);
        }
        else {
            log.info("Current count is " + (int)count + " incrementing by 1");
            //Increment counter by 1
            synchronized (key) {
                counter.put(key, (int)count + 1);
            }
        }
        return null;
    }
```
If we make two requests using the same access token within a 60 seconds time 
window, the counter of that token would be 2.

We can similary use any other key to count against, depending on our use case.
For example, if we count the **number of requests of an application**, we would use 
the `client_id` as the key to count against.

We can also use any attribute such as the user's username, IP address, and so on 
as a key.


## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
    * Chapter 5.1: Throttling at the API gateway with Zuul 


