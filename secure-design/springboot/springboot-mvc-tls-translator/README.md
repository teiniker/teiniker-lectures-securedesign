# Example: Spring Boot MVC TLS Translator 

This example shows the TLS configuration in a Spring Boot project.

```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-mvc-translator-0.0.1-SNAPSHOT.jar
```

```
URL: https://localhost:8443/index.html

curl -i -k -X POST -d 'word=cat&language=Deutsch&action=Translate' https://localhost:8443/translator
```

## Resources

[Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

*Egon Teiniker, 2020 - 2021, GPL v3.0*