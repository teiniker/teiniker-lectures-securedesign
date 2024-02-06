# Example: Article Service

## Service Setup

We use Maven to compile and run the service.
```
$ mvn spring-boot:run
```

## Using the Service

### Find Articles

```
$ curl -i http://localhost:9090/articles

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 13:38:59 GMT

[
    {"id":1,"description":"Design Patterns","price":4295},
    {"id":2,"description":"Effective Java","price":3336}
]
```

```
$ curl -i http://localhost:9090/articles/2

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 13:39:04 GMT

{"id":2,"description":"Effective Java","price":3336}
```

```
$ curl -i http://localhost:9090/articles/99

HTTP/1.1 404
Content-Type: text/plain;charset=UTF-8
Content-Length: 26
Date: Thu, 07 Oct 2021 14:14:49 GMT

Could not find employee 99
```

### Insert an Article
```
$ curl -i -X POST http://localhost:9090/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 14:17:36 GMT

{"id":3,"description":"Microservices Patterns: With examples in Java","price":2550}
```

### Update an Article
```
$ curl -i -X PUT http://localhost:9090/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 14:20:22 GMT

{"id":2,"description":"Effective Java","price":9999}
```

### Delete an Article
```
$ curl -i -X DELETE http://localhost:9090/articles/3

HTTP/1.1 200
Content-Length: 0
Date: Thu, 07 Oct 2021 14:23:30 GMT
```

*Egon Teiniker, 2016 - 2022, GPL v3.0*