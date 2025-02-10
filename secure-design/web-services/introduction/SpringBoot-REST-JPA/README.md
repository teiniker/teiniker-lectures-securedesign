# Example: SpringBoot REST + JPA

## Build and Run 

Make sure the MariaDB database is running: 
```Bash
$ sudo systemctl start mariadb.service 
```

If we start the MySQL client, wie can monitor the database content:
```
$ mysql -ustudent -pstudent
MariaDB [(none)]> use testdb;

MariaDB [testdb]> show tables;
MariaDB [testdb]> select * from book;	
```

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
$ curl -i http://localhost:8080/api/books
```

```Bash
$ curl -i http://localhost:8080/api/books/3

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Feb 2025 09:54:02 GMT

{
    "id":3,
    "author":"Martin Fowler",
    "title":"Refactoring",
    "isbn":"978-0134757599"
}
```

### Update a Book

```Bash
$ curl -i -X PUT http://localhost:8080/api/books/3  -H "Content-Type: application/json" -d '{"id":3,"author":"Martin Fowler","title":"Refactoring Existing Code","isbn":"978-0134757599"}'

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 09 Feb 2025 09:51:26 GMT

{
    "id":3,
    "author":"Martin Fowler",
    "title":"Refactoring Existing Code",
    "isbn":"978-0134757599"
}
```


### Delete a Book

```Bash
$ curl -i -X DELETE http://localhost:8080/api/books/2

HTTP/1.1 200 
Content-Length: 0
Date: Sun, 09 Feb 2025 09:53:13 GMT
```

## Implementation Details

### Database Access Layer


### REST Controller



*Egon Teiniker, 2016 - 2024, GPL v3.0*
