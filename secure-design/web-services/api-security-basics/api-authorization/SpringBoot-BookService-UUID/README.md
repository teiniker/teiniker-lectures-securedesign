# Object-Level Authorization Using UUIDs 

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
Date: Mon, 06 Feb 2023 18:00:08 GMT

[
    {"id":"da42b451-11e1-4ac9-be5e-dd3b767be1ba","author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
    {"id":"10e290da-4c8d-4646-be93-7a03f47e3082","author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
    {"id":"9770cdb8-1eb0-4198-b95e-242595374a20","author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

```
$ curl -i http://localhost:8080/books/da42b451-11e1-4ac9-be5e-dd3b767be1ba

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 18:02:47 GMT

{"id":"da42b451-11e1-4ac9-be5e-dd3b767be1ba","author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
```

```
$ curl -i http://localhost:8080/books/666

HTTP/1.1 404 
Content-Length: 0
Date: Mon, 06 Feb 2023 18:05:19 GMT
```

### Insert a Book
```
$ curl -i -X POST http://localhost:8080/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'

HTTP/1.1 201 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 06 Feb 2023 18:07:52 GMT

{"id":"da42b451-11e1-4ac9-be5e-dd3b767be1ba","author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
```

### Update a Book
```
$ curl -i -X PUT http://localhost:8080/books/da42b451-11e1-4ac9-be5e-dd3b767be1ba -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 14:20:22 GMT

{"id":"da42b451-11e1-4ac9-be5e-dd3b767be1ba","author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}
```

### Delete a Book
```
$ curl -i -X DELETE http://localhost:8080/books/da42b451-11e1-4ac9-be5e-dd3b767be1ba

HTTP/1.1 204 
Date: Mon, 06 Feb 2023 18:20:15 GMT
```

```
$ curl -i http://localhost:8080/books/3

HTTP/1.1 404 
Content-Length: 0
Date: Mon, 06 Feb 2023 18:21:13 GMT
```

## References
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

*Egon Teiniker, 2016 - 2023, GPL v3.0*
