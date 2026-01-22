# Example: SpringBoot REST 

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

## Implementation Details

This implementation demonstrates a simple Spring Boot RESTful web service 
for managing books. It exposes standard CRUD operations (create, read, 
update, delete) over HTTP using REST principles and appropriate HTTP methods. 

The service uses an in-memory data store to simulate a database and exchanges 
data in JSON format through a Book data transfer object. It is intended as a 
lightweight example showing how Spring Boot controllers, request mappings, 
and REST responses work together.

### Application Entry Point

In a Spring Boot REST service, this class is the entry point of the application:

```Java
@SpringBootApplication
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
```

* **@SpringBootApplication**: This is a meta-annotation that combines three 
    important Spring annotations:

    - **@Configuration**:
        - Marks this class as a **Spring configuration class**
        - Allows it to define beans using @Bean methods

    - **@EnableAutoConfiguration**: Tells Spring Boot to **automatically configure** 
        the application based on:
        - Dependencies on the classpath (e.g., Spring MVC, Jackson, Tomcat)
        - Application properties

    - **@ComponentScan**: Instructs Spring to scan for components (`@RestController`, 
        `@Service`, `@Repository`, etc.)
        - By default, it scans the package of this class and all sub-packages
        - This is why controllers are usually placed under the same base package

* **SpringApplication.run(Application.class, args)**: This line starts the Spring 
    Boot application:
    - Creates the **Spring Application Context**
    - Performs **classpath scanning**
    - Applies **auto-configuration**
    - Starts the **embedded web server** (Tomcat/Jetty/Undertow)
    - Registers REST controllers and maps URLs
    - Begins listening for HTTP requests (e.g., on port 8080)


### REST Controller

This class is a Spring Boot REST controller that exposes 
a **CRUD (Create, Read, Update, Delete) REST API** for managing `Book` objects. 

| HTTP Method | Endpoint      | Action        | Status Codes |
| ----------- | ------------- | ------------- | ------------ |
| GET         | `/books`      | Get all books | 200          |
| GET         | `/books/{id}` | Get one book  | 200, 404     |
| POST        | `/books`      | Create book   | 201          |
| PUT         | `/books/{id}` | Update book   | 200, 404     |
| DELETE      | `/books/{id}` | Delete book   | 204, 404     |


It simulates a database using an in-memory map.

```Java
@RestController
public class BookController
{
    @GetMapping("/books")
    ResponseEntity<List<Book>> findAll()
    {
        //...
    }

    @GetMapping("/books/{id}")
    ResponseEntity<Book> findById(@PathVariable long id)
    {
        //...
    }

    @PostMapping("/books")
    ResponseEntity<Book> insert(@RequestBody Book book)
    {
        //...
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> update(@RequestBody Book book, @PathVariable long id)
    {
        //...
    }    

    @DeleteMapping("/books/{id}")
    ResponseEntity<?> delete(@PathVariable long id)
    {
        //...
    }
}
```

* **@RestController**: Marks this class as a REST controller
    (Combines: `@Controller` and `@ResponseBody`)
    - Every method returns data (usually JSON)
    - Spring automatically converts Java objects to JSON using Jackson

* **@GetMapping("/books")**: Handles HTTP GET requests to `/books` and 
    returns all books.
    - ResponseEntity<> allows control over:
        - Response body
        - HTTP status code

* **@GetMapping("/books/{id}")**: Handles HTTP GET requests to `/books` 
    and retrieves a `Book` by ID.
    - `{id}` is a path variable
    - `@PathVariable`: binds URL value to method parameter    

* **@PostMapping("/books")**: Handles HTTP POST request to `/books`
    and creates a new `Book`
    - `@RequestBody`: Converts incoming JSON into a Book object

* **@PutMapping("/books/{id}")**: Handles HTTP PUT requests to `/books`
    and updates an existing `Book`
    - `@RequestBody`: Converts incoming JSON into a Book object
    - `@PathVariable`: binds URL value to method parameter

* **@DeleteMapping("/books/{id}")**: Handles HTTP DELETE requests 
    to `/books` and deletes a `Book`.
    - `@PathVariable`: binds URL value to method parameter


### Data Transfer Object

The `Book` class is a **POJO (Plain Old Java Object)** used as the data 
model for your Spring Boot REST service. It represents the resource that 
is sent to and received from REST endpoints as JSON.

`Serializable` allows `Book` objects to be:
* Serialized (converted to a byte stream)
* Sent over the network
* Stored in sessions or caches

Often required by frameworks (JPA, HTTP sessions, caching)

A **No-Argument Constructor** is required by:
* Jackson (JSON to Object mapping)
    - Creates object using no-arg constructor
    - Sets fields using setters
* Many frameworks (JPA, Hibernate)


## References
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

*Egon Teiniker, 2016 - 2026, GPL v3.0*
