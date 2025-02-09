# Example: SpringBoot REST + JPA

## Build and Run 

We can start the service as a separate process:
```Bash
$ mvn spring-boot:run
```

## Accessing the REST API via curl

### Insert Books
```Bash
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}'
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
$ curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json" -d '{"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":"978-0134757599"}'
```

### Find Books

```Bash
$ curl http://localhost:8080/api/books
```

```Bash
$ curl http://localhost:8080/api/books/2
```

### Update a Book


### Delete a Book



