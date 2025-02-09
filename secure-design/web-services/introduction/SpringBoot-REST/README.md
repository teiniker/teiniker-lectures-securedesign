# SpringBoot REST Service 

## Setup 

We can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Accessing the API via curl

### Find Books

```
$ curl -i http://localhost:8080/books

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 21:03:31 GMT

[
    {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
    {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
    {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

```
$ curl -i http://localhost:8080/books/2

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 21:06:05 GMT

{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
```

```
$ curl -i http://localhost:8080/books/666

HTTP/1.1 404 
Content-Length: 0
Date: Mon, 06 Feb 2023 21:06:42 GMT
```

### Insert a Book
```
$ curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'

HTTP/1.1 201 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 21:07:20 GMT

{"id":4,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
```

### Update a Book
```
$ curl -i -X PUT http://localhost:8080/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 21:08:31 GMT

{"id":1,"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}
```

### Delete a Book
```
$ curl -i -X DELETE http://localhost:8080/books/2

HTTP/1.1 204 
Date: Mon, 06 Feb 2023 21:09:24 GMT
```

```
$ curl -i http://localhost:8080/books/666

HTTP/1.1 404 
Content-Length: 0
Date: Mon, 06 Feb 2023 21:10:07 GMT
```

## References
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

*Egon Teiniker, 2016 - 2024, GPL v3.0*
