# Spring Boot: Bean Validation


## Service Setup

We use Maven to compile and run the service.
```
$ mvn spring-boot:run
```

## Access the REST Service

Find all Articles:
```
$ curl -ki https://localhost:8443/articles

```

Find a particular Article:
```    
$ curl -ki https://localhost:8443/articles/2

```
 
Insert a valid Article:
```
$ curl -ki -X POST https://localhost:8443/articles -H 'Content-type:application/json' -d '{"id":7, "description": "Microservices Patterns: With examples in Java", "price": 2550}'
```

Insert an invalid Article:
```
$ curl -ki -X POST https://localhost:8443/articles -H 'Content-type:application/json' -d '{"id":7, "description": "Patterns", "price": 2550}'

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 28 Oct 2021 20:11:55 GMT
Connection: close

{"description":"Description must have at least 10 characters"}
```

## Implementation

To use the Bean Validation feature in SpringBoot, we need to add the following 
dependency to the `pom.xml` file:

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
```

Now we can **annotate beans with constraints** which are used as **Data Transfer Objects (DTO)**:

```Java
    @NotNull
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String description;
```

These constraints will be validated if we use them as parameters in the controller.
```Java
   @PostMapping("/articles")
    ResponseEntity<Article> newArticle(@Valid @RequestBody Article newArticle)
    {
        table.add(newArticle);
        return new ResponseEntity<Article>(newArticle, HttpStatus.CREATED);
    }
```
The **@Valid** annotation triggers the validation of the DTO before the method will be executed.

To handle a possible **MethodArgumentNotValidException** we also implement an exception handler in the controller
class.
```Java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
```

## Common Bean Validation Annotations

Annotations defined in the JSR 303 (Bean Validation) are:
* **@NotNull**: Indicates that the annotated field should not be null.
* **@Size(min=n, message="")**: Indicates that the annotated string should have a minimum length. 
   The `@Size` annotation can also be used to set the minimum and maximum size of arrays, collections such as lists, sets, and maps.
* **@NotBlank**: Validates that the property is not null or whitespace. But, it can be applied only to text values.
* **@AssertFalse**: states that the value of the field or property must be false.
* **@AssertTrue**: states that the value of the field or property must be true.
* **@Maxensures**: a number whose value must be lower or equal to the specified maximum.
* **@Min(value=n, message="")**: ensures a number whose value must be higher or equal to the specified minimum.
* **@Future**: ensurest that the date or time is in the future.
* **@Past**: ensures that the date is in the past.
* **@DecimalMax**: a decimal number whose value must be lower or equal to the specified maximum.
* **@DecimalMin**: a decimal number whose value must be higher or equal to the specified minimum.
* **@Email**: Validates that the annotated property is a valid email address.
* **@Pattern(regexp="", message="")**: The annotated CharSequence must match the specified regular expression. The regular expression follows the Java regular expression conventions.



## Access the OpenAPI Specification

Download the formal specification of this REST API:
```
https://localhost:8443/swagger-ui/index.html

$ curl -k https://localhost:8443/v3/api-docs.yaml
```

Note that the **bean validation annotations** will be **included into the generated 
OpenAPI specification**:
```
components:
  schemas:
    Article:
      required:
      - description
      type: object
      properties:
        id:
          minimum: 0
          type: integer
          format: int64
        description:
          maxLength: 255
          minLength: 10
          type: string
        price:
          minimum: 0
          type: integer
          format: int64
```

## References
* [Bean Validation in Spring Boot](https://springframework.guru/bean-validation-in-spring-boot/)
* [Bean Validation 2.0 (JSR 380)](https://beanvalidation.org/2.0-jsr380/)

*Egon Teiniker, 2016-2025, GPL v3.0*
