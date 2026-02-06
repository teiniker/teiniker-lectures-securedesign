# Example: SpringBoot REST 

## Setup 

We can start the service as a separate process:
```bash
$ mvn spring-boot:run
```

For using Docker, we have to start the Docker engine:
```bash
$ sudo systemctl start docker

$ sudo systemctl status docker
```


## Build a Docker Image

We create a **fat JAR file** containing the web server together with the Sping Boot application.
The created JAR file can be startet with a `java -jar` command.
```
$ mvn package

$ java -jar target/SpringBoot-REST.jar
```

The next step ist to write a simple **Dockerfile** which uses an existing 
image providing the OpenJDK 21.

```Dockerfile
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set working directory
WORKDIR /opt/service

# Copy the JAR file with a more generic name
COPY --chown=appuser:appgroup target/SpringBoot-REST.jar app.jar

# Switch to non-root user
USER appuser

# Expose the port your application runs on (adjust as needed)
EXPOSE 8080

# Use ENTRYPOINT with exec form for better signal handling
ENTRYPOINT ["java", "-jar", "app.jar"]
```



Using the following Docker command, we **create a new image** called `orderservice`: 
```
$ docker build -t bookservice .

$ docker image ls
REPOSITORY                                 TAG               IMAGE ID       CREATED          SIZE
bookservice                                latest            aa7123e55712   12 seconds ago   227MB```
```

Finally, we start a new container based on the build image:
```bash
$ docker run --rm -p 8080:8080 bookservice
```
Now, we are free to reuse the `curl` statements from above to test our running microservice.


Note that we can easily run a second `bookservice` container with a **different port number**:

```
$ docker run --rm -p 8090:8080 bookservice
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


## References
* [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

* [Build a Docker image using Maven and Spring Boot](https://medium.com/swlh/build-a-docker-image-using-maven-and-spring-boot-58147045a400)

* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020

*Egon Teiniker, 2017-2026, GPL v3.0*
