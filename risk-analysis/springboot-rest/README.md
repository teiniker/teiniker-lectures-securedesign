# Example: Spring Boot REST

## Setup and Run
```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-rest-0.0.1-SNAPSHOT.jar
```

## Access the REST API

* **GET Requests (Retrieve Data)**
```
$ curl -i http://localhost:8180/api/books

$ curl -i http://localhost:8180/api/books/1
```

* **POST Request (Insert Data)**
```
curl -i http://localhost:8180/api/books
curl -i -H "Content-Type: application/json"  -d '{"id":7,"title":"Programming in C","author":"K&R"}' -X POST http://localhost:8180/api/books
```

## Spring Boot Annotations

* @SpringBootApplication
* @EnableJpaRepositories
* @EntityScan

* @RestController
* @RequestMapping
* @Autowired
* @GetMapping
* @PostMapping
* @DeleteMapping
* @PutMapping
* @ResponseStatus

* @ControllerAdvice
* @ExceptionHandler

* @Entity
* @Id
* @GeneratedValue
* @Column



## Resources

* [Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

*Egon Teiniker, 2017 - 2022, GPL v3.0*