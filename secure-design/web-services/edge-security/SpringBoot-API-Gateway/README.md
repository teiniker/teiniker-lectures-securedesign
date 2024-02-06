# Spring Cloud Gateway

Spring Cloud Gateway aims to provide a simple way to route to APIs and provide cross cutting concerns 
to them such as: security, monitoring/metrics, and resiliency.

* **Route**: The basic building block of the gateway. It is defined by an ID, a destination URI, 
  a collection of predicates, and a collection of filters. 
  A route is matched if the aggregate predicate is true.

* **Predicate**: This is a Java 8 Function Predicate. The input type is a Spring Framework ServerWebExchange. 
  This lets you match on anything from the HTTP request, such as headers or parameters.

* **Filter**: These are instances of Spring Framework GatewayFilter that have been constructed with 
  a specific factory. Here, you can modify requests and responses before or after sending the downstream request.


## Setup

Set up the microservice: 
```
$ cd SpringBoot-BookService
$ mvn spring-boot:run

$ curl -i http://localhost:9090/books
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 07 Feb 2023 18:54:12 GMT

[
  {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
  {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
  {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

Set up the API gateway:

```
$ cd SpringBoot-API-Gateway
$ mvn spring-boot:run
```

## Path Route Predicate
```
spring:
  cloud:
    gateway:
      routes:
        - id: path_route
          uri: http://localhost:9090
          predicates:
            - Path=/v1/**
          filters:
            - RewritePath=/v1/(?<segment>.*), /$\{segment}

logging:
  level:
    org:
      springframework:
        cloud:
          gateway: TRACE
```

```
$ curl -i http://localhost:8080/v1/books
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 19:12:18 GMT

[
  {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
  {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
  {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

```
$ curl -i http://localhost:8080/v1/books/1
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 19:12:14 GMT

{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
```

The path `/v1/books` will be rewritten to: `/books`.


## Method Route Predicate 
The **Method Route Predicate** Factory takes a methods argument which is one or more parameters: 
the HTTP methods to match. The following example configures a method route predicate:
```
spring:
  cloud:
    gateway:
      routes:
      - id: method_route
        uri: http://localhost:9090/articles
        predicates:
        - Method=GET,POST
```
This route matches if the request method was a `GET` or a `POST`.

### Find Articles

```
$ curl -i http://localhost:8080/articles

HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 17:44:17 GMT

[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":9999}]
```

```
$ curl -i http://localhost:8080/articles/2

HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 17:45:24 GMT

{"id":2,"description":"Effective Java","price":9999}
```

### Insert an Article
```
$ curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'

HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/json
Date: Tue, 07 Feb 2023 17:49:33 GMT

{"id":4,"description":"Microservices Patterns: With examples in Java","price":2550}
```

### Update an Article
```
$ curl -i -X PUT http://localhost:8080/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'

HTTP/1.1 404 Not Found
Content-Type: application/json
Content-Length: 139
```

### Delete an Article
```
$ curl -i -X DELETE http://localhost:8080/articles/3

HTTP/1.1 404 Not Found
Content-Type: application/json
Content-Length: 139
```



## References
* [YouTube: Use Spring Cloud Gateway to drive traffic to your APIs](https://youtu.be/wYk0JrNdb8g)
* [Spring Cloud Tutorial - Spring Cloud Gateway Hello World Example](https://www.javainuse.com/spring/cloud-gateway)
* [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html)

*Egon Teiniker, 2016-2023, GPL v3.0*
