# Example: SpringBoot Transport Layer Security 

## Setup TLS

In order to configure a TLS connector, we have to create a server-side certificate and configure the 
application.properties file.

```
$ cd src/main/resources
$ keytool -genkeypair -keystore server.jks -storepass student -keypass student -keyalg RSA -alias server -dname "cn=se,o=lab,c=org"
```

Change `application.properties`:
```
server.port=8443

server.ssl.key-store=classpath:server.jks
server.ssl.key-store-type=pkcs12
server.ssl.key-store-password=student
server.ssl.key-password=student
server.ssl.key-alias=server
```

Now, we can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Using the Service

### Find Articles

```
$ curl -i -k https://localhost:8443/articles

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 13:38:59 GMT

[
    {"id":1,"description":"Design Patterns","price":4295},
    {"id":2,"description":"Effective Java","price":3336}
]
```

```
$ curl -i -k https://localhost:8443/articles/2

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 13:39:04 GMT

{"id":2,"description":"Effective Java","price":3336}
```

```
$ curl -i -k https://localhost:8443/articles/99

HTTP/1.1 404
Content-Type: text/plain;charset=UTF-8
Content-Length: 26
Date: Thu, 07 Oct 2021 14:14:49 GMT

Could not find employee 99
```

### Insert an Article
```
$ curl -i -k -X POST https://localhost:8443/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 14:17:36 GMT

{"id":3,"description":"Microservices Patterns: With examples in Java","price":2550}
```

### Update an Article
```
$ curl -i -k -X PUT https://localhost:8443/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 07 Oct 2021 14:20:22 GMT

{"id":2,"description":"Effective Java","price":9999}
```

### Delete an Article
```
$ curl -i -k -X DELETE https://localhost:8443/articles/3

HTTP/1.1 200
Content-Length: 0
Date: Thu, 07 Oct 2021 14:23:30 GMT
```

```
$ curl -i -k https://localhost:8443/articles/3

HTTP/1.1 404
Content-Type: text/plain;charset=UTF-8
Content-Length: 25
Date: Thu, 07 Oct 2021 14:24:05 GMT

Could not find employee 3
```

## References

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Enable HTTPS/SSL in Spring Boot](https://youtu.be/HLSmjZ5vN0w)

* [Apache Maven Resources Plugin](https://maven.apache.org/plugins/maven-resources-plugin/)

*Egon Teiniker, 2020 - 2021, GPL v3.0*
