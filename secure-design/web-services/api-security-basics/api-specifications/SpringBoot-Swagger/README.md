# Documenting a REST API

Documentation is an essential part of building REST APIs.
**SpringDoc** is a tool that simplifies the generation and maintenance of API docs based on the 
**OpenAPI 3 specification**

## Service Setup

To have `springdoc-openapi` automatically generate the OpenAPI 3 specification docs for our API, 
we simply add the springdoc-openapi-ui dependency to our `pom.xml`:
Besides generating the OpenAPI 3 specification itself, we can integrate `springdoc-openapi` with 
**Swagger UI** so that we can interact with our API specification and exercise the endpoints.

```
<dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
   <version>2.0.2</version>
</dependency>
```

We use Maven to compile and run the service.
```
$ mvn spring-boot:run
```

## Access the Swagger UI

So now our API documentation will be available at:
```
https://localhost:8443/swagger-ui.html
```

## Configure SpringDoc 

Springdoc-openapi also supports swagger-ui properties.
These can be used as **Spring Boot properties**, with the prefix `springdoc.swagger-ui`.

To customize the path of our API documentation, we modify our **application.properties** to include:
```
springdoc.swagger-ui.path=/swagger-ui-custom.html
```
To sort the API paths in order of their HTTP methods, we can add:

```
springdoc.swagger-ui.operationsSorter=method
```

## Generate Documentation via Annotations

We can add some description to our API using some of **OpenAPI-specific annotations**:

* **@Operation**: The annotation may be used to define a resource method as an OpenAPI Operation, and/or to define additional properties for the Operation.

* **@ApiResponse**: The annotation may be used at method level or as field of Operation to define one or more responses of the Operation.

* **@ApiResponses**: Container for repeatable ApiResponse annotation.


## OpenAPI Specification
The **OpenAPI** descriptions will be available at the path `/v3/api-docs` by default:
```
https://localhost:8443/v3/api-docs/
https://localhost:8443/v3/api-docs.yaml
```

To use a custom path, we can indicate in the `application.properties` file:
```
springdoc.api-docs.path=/api-docs
```

## References
* [YouTube (Java Brains): How to add Swagger to Spring Boot](https://youtu.be/gduKpLW_vdY)

* [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
* [springdoc-openapi](https://springdoc.org/)

* [swagger-annotations API](https://javadoc.io/doc/io.swagger.core.v3/swagger-annotations/latest/index.html)

*Egon Teiniker, 2017-2025, GPL v3.0*