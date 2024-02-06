# OpenAPI Generator

## Setup

Download the openapi-generator-cli.jar file from the Maven Repository and store it in a local directory.
```
$ wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/6.2.1/openapi-generator-cli-6.2.1.jar -O openapi-generator-cli.jar

$ java -jar openapi-generator-cli.jar help
usage: openapi-generator-cli <command> [<args>]

The most commonly used openapi-generator-cli commands are:
    author        Utilities for authoring generators or customizing templates.
    batch         Generate code in batch via external configs.
    config-help   Config help for chosen lang
    generate      Generate code with the specified generator.
    help          Display help information about openapi-generator
    list          Lists the available generators
    meta          MetaGenerator. Generator for creating a new template set and configuration for Codegen.  The output will be based on the language you specify, and includes default templates to include.
    validate      Validate specification
    version       Show version information used in tooling
```

## Generate the Service Implementation

```
$ java -jar openapi-generator-cli.jar generate -g spring --library spring-boot -i ArticleService.yaml -o target -p groupId=org.se.lab -p artifactId=ArticleService -p artifactVersion=1.0.0-SNAPSHOT

$ tree 
├── ArticleService.yaml
└── target
    ├── pom.xml
    ├── README.md
    └── src
        ├── main
        │   ├── java
        │   │   └── org
        │   │       └── openapitools
        │   │           ├── api
        │   │           │   ├── ApiUtil.java
        │   │           │   ├── ArticlesApiController.java
        │   │           │   └── ArticlesApi.java
        │   │           ├── configuration
        │   │           │   ├── HomeController.java
        │   │           │   └── SpringDocConfiguration.java
        │   │           ├── model
        │   │           │   └── Article.java
        │   │           ├── OpenApiGeneratorApplication.java
        │   │           └── RFC3339DateFormat.java
        │   └── resources
        │       ├── application.properties
        │       └── openapi.yaml
        └── test
            └── java
                └── org
                    └── openapitools
                        └── OpenApiGeneratorApplicationTests.java
```

Compile the generated code
```
$ cd target
$ mvn spring-boot:run
```

The API documentation will be available at:
```
http://localhost:8080/swagger-ui.html
```

Because this only the skeleton of a service, there is no real functionality.
We have to extend the generated code to add business logic to it.

## Security Aspects of OpenAPI

### Authentication
OpenAPI supports multiple types of authentications and authorzations schemes specified with
the `security scheme` component.

```
components:
  securitySchemes:
    basicAuth:     
      type: http
      scheme: basic
```

After defining a `securitySchemes` we can add a `security` element to our methods:

```
/articles:
  get:
    security:
       - basicAuth: []
```

The OpenAPI generator will only generate server-side annotations but not code to use an
authentication mechanism of Spring Security.


### Data Validation

When we define the schema for a model class like Article, we can specify the data types 
of its attributes.

Primitive data types in the OAS are based on the types supported by the 
**JSON Schema Specification**.
Primitives have an optional modifier property: `format`: `int32`, `int64`, `float`, `double`, `password`. 
OAS uses several known formats to define in fine detail the data type being used.

```
    Article:
      type: object
      properties:
        id:
          type: integer
          format: int64
          minimum: 0
        description:
          type: string
          
        mail:
          type: string
          format: email
        price:
          type: integer
          format: int64
          minimum: 0

```

Also, for type:string there are interesting formats like: `email`, `uuid` 
and properties like: `minLength`, `maxLength`, `pattern`

These constraints will be added as **Bean Validation** annotations in the generated code.
Thus, the constraints are validated at runtime.



## References
* OpenAPI Generator
  * [OpenAPI Generator](https://openapi-generator.tech/)
  * [Config Options for Spring](https://openapi-generator.tech/docs/generators/spring)
  * [Maven Repository](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-cli/5.2.1)
  * [YouTube: Introducing OpenAPI Generator](https://youtu.be/t4jaTC7QjMg)

* Security Aspects
  * [OpenAPI Specification for API Security](https://youtu.be/kc56ks9b7AQ)
  * [OpenAPI Specification](https://spec.openapis.org/oas/v3.1.0)
    (4.4 Data Types; 4.8.27 Security Scheme Object)

*Egon Teiniker, 2016-2022, GPL v3.0*
