# Example: Spring Boot MVC

```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-mvc-0.0.1-SNAPSHOT.jar
```

```
URL: http://localhost:8080/index.html

$ curl -i http://localhost:8080/index.html
$ curl -i -X POST -d 'word=cat&language=Deutsch&action=Translate' http://localhost:8080/translator
```

## Spring Boot Annotations

* @SpringBootApplication

* @Controller:

* @PostMapping:

* @RequestParam:


## Resources

* [Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

*Egon Teiniker, 2017 - 2022, GPL v3.0*