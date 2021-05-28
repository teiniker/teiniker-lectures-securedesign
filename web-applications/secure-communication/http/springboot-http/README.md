# Spring Boot: HTTP Connector


## Access the Web service

```
URL: http://localhost:8080/translate/katze

$ curl -i -H "accept-language:en" -X GET http://localhost:8080/translate/katze
```

## Access the Web application

```
URL: http://localhost:8080/app?word=pferd

$ curl -i --cookie "id=1234567" -X GET http://localhost:8080/app?word=pferd
```

## Resources

[Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

*Egon Teiniker, 2020, GPL v3.0*