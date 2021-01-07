# Spring Boot: OrderService 

Spring Boot provides a very good support to building RESTful Web Services.
```Java
@RestController
@RequestMapping("/orders")
public class OrderProcessingService 
{
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order)
    {
        //...
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) 
    {
        //...
        return new ResponseEntity<Order>(orders.get(id), HttpStatus.OK);
    }
}
```
The following annotations can be used:
* The **@RestController** annotation is used to define the RESTful web services.
* The **@RequestMapping** annotation is used to define the Request URI to access the REST Endpoints.

* The **@GetMapping** annotation maps HTTP GET requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.GET)`.
* The **@PostMapping** annotation maps HTTP POST requests onto specific handler methods. 
   It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.POST)`.
* The  @PutMapping annotation maps HTTP PUT requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.PUT)`.
* The **@DeleteMapping** annotation maps HTTP DELETE requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.DELETE)`.

* The **@PathVariable** annotation is used to define the custom or dynamic request URI. 
   The Path variable in request URI is defined as curly braces {}.
* The **@RequestParam** annotation is used to read the request parameters from the Request URL. 
* The **@RequestBody** annotation is used to define the request body content type.


We start with a **simple microservice**:
```
$ mvn spring-boot:run
```

We can send a **HTTP POST request**:
```
$ curl -i http://localhost:8080/orders -H 'Content-Type: application/json' --data-binary @- <<EOF
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

## Build a Docker Image

We create a **fat JAR file** containing the Web server together with the Sping Boot application.
The created JAR file can be startet with a `java -jar` command.
```
$ mvn package

$ java -jar target/SpringBoot-OrderService-1.0.jar
```

The next step ist to write a simple **Dockerfile** which uses an existing image providing the OpenJDK 11.
We copy the fat JAR file into a new directory and specify the execution command.
```
FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/service
COPY target/SpringBoot-OrderService-1.0.jar /opt/service
CMD ["java", "-jar", "/opt/service/SpringBoot-OrderService-1.0.jar"]
```

Using the following Docker command, we **create a new image** called `orderservice`: 
```
# docker build -t orderservice .

# docker image ls
REPOSITORY               TAG                 IMAGE ID            CREATED             SIZE
orderservice             latest              631ef36eb957        29 seconds ago      573MB
adoptopenjdk/openjdk11   ubi                 014786455e14        3 weeks ago         555MB
```

Finally, we start a new container based on the build image:
```
# docker run -p 8080:8080 orderservice
```
Now, we are free to reuse the `curl` statements from above to test our running microservice.


Note that we can easily run a second `orderservice` container with a **different port number**:
```
# docker run -p 8090:8080 orderservice

# docker container ls -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS           PORTS                    NAMES
e7225039ee7c        orderservice        "java -jar /opt/serv…"   14 minutes ago      Up 14 minutes    0.0.0.0:8090->8080/tcp   gallant_dubinsky
c6976c4604e6        orderservice        "java -jar /opt/serv…"   15 minutes ago      Up 15 minutes    0.0.0.0:8080->8080/tcp   awesome_sinoussi
```

  
## References
* [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
