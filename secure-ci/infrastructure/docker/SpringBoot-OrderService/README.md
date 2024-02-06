# Building a Docker Image with Spring Boot 

We build and start the **Spring Boot web service**:
```
$ mvn spring-boot:run
```

We can send a **HTTP POST request**:
```
$ curl -i http://localhost:8080/orders -H 'Content-Type: application/json' --data-binary @- <<EOF
{
"items":[
    {
        "itemCode":"IT001",
        "quantity":3
    },
    {
        "itemCode":"IT004",
        "quantity":1
    }
    ],
    "shippingAddress":"No 4, CA, USA"
}
EOF

HTTP/1.1 201
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:53:35 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```
And we can send a **HTTP GET request** using `curl`:
```
$ curl -i http://localhost:8080/orders/28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 19 Nov 2020 16:55:48 GMT

{"orderId":"28e9ba9c-f284-4f3b-ac14-8dce44f2a7cd","items":[{"itemCode":"IT001","quantity":3},{"itemCode":"IT004","quantity":1}],"shippingAddress":"No 4, CA, USA"}
```

## Build a Docker Image

We create a **fat JAR file** containing the Web server together with the Sping Boot application.
The created JAR file can be startet with a `java -jar` command.
```
$ mvn package

$ java -jar target/SpringBoot-OrderService-1.0.jar
```

The next step ist to write a simple **Dockerfile** which uses an existing image providing the OpenJDK 11.
We copy the fat JAR file into a new directory and specify the execution command.
```
FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /opt/service
COPY target/SpringBoot-OrderService-1.0.jar /opt/service
CMD ["java", "-jar", "/opt/service/SpringBoot-OrderService-1.0.jar"]
```

Using the following Docker command, we **create a new image** called `orderservice`: 
```
$ sudo docker build -t orderservice .

$ sudo docker image ls
REPOSITORY               TAG                 IMAGE ID            CREATED             SIZE
orderservice             latest              631ef36eb957        29 seconds ago      362MB
adoptopenjdk/openjdk11   alpine              014786455e14        3 weeks ago         345MB
```

Finally, we start a new container based on the build image:
```
$ sudo docker run -p 8080:8080 orderservice
```
Now, we are free to reuse the `curl` statements from above to test our running microservice.


Note that we can easily run a second `orderservice` container with a **different port number**:
```
$ sudo docker run -p 8090:8080 orderservice

$ sudo docker container ls -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS           PORTS                    NAMES
e7225039ee7c        orderservice        "java -jar /opt/serv…"   14 minutes ago      Up 14 minutes    0.0.0.0:8090->8080/tcp   gallant_dubinsky
c6976c4604e6        orderservice        "java -jar /opt/serv…"   15 minutes ago      Up 15 minutes    0.0.0.0:8080->8080/tcp   awesome_sinoussi
```

## Build a Docker Image with Maven

Spring Boot comes pre-shipped with its own plugin for building Docker images and we don’t need to make any changes
in your pom.xml, simply use `spring-boot-starter-parent`.
We do not need to write a Dockerfile, the plugin takes care of Spring-recommended security, memory, and performance 
optimizations. 
But be warned, if there is a **Dockerfile** located within your source code repository, it **will be ignored**.

Note that to use the `docker` command, we have to execute Maven as root:
```
# vi .bashrc

export M2_HOME=/opt/apache-maven-3.8.2
export PATH=$M2_HOME/bin:$PATH
```

```
# mvn spring-boot:build-image -Dspring-boot.build-image.imageName=myrepo/orderservice

# docker run -p 8080:8080 myrepo/orderservice
```  
  
## References
* [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Build a Docker image using Maven and Spring Boot](https://medium.com/swlh/build-a-docker-image-using-maven-and-spring-boot-58147045a400)
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
