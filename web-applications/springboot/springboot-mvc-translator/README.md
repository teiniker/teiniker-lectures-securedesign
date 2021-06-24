# Spring Boot MVC: Translator 

```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-mvc-translator-0.0.1-SNAPSHOT.jar
```

```
URL: http://localhost:8080/index.html

curl -i -X POST -d 'word=cat&language=Deutsch&action=Translate' http://localhost:8080/translator
```

## Resources

[Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

*Egon Teiniker, 2020 - 2021, GPL v3.0*