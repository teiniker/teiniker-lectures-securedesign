# Example: Spring Boot REST

## Setup and Run
```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-rest-0.0.1-SNAPSHOT.jar
```

## Access the REST API

* **GET Requests (Retrieve Data)**
```
$ curl -i http://localhost:8180/api/books

$ curl -i http://localhost:8180/api/books/1
```

* **POST Request (Insert Data)**
```
curl -i http://localhost:8180/api/books
curl -i -H "Content-Type: application/json"  -d '{"id":7,"title":"Programming in C","author":"K&R"}' -X POST http://localhost:8180/api/books
```

## Spring Data JPA

**Spring Data** is Spring-based programming model for data access. 
It reduces the amount of code needed for working with databases and datastores. 
It consists of several modules.
The **Spring Data JPA** simplifies the development of Spring applications that use JPA technology.

The **DataSource configuration** is controlled by external configuration properties
in `application.properties`:
```
spring.datasource.url=jdbc:mariadb://localhost:3306/testdb
spring.datasource.username=student
spring.datasource.password=student
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

The **Java Persistence API** is a standard technology that lets you “map” objects to relational databases.

### Entity Classes
Traditionally, JPA **Entity** classes are specified in a `persistence.xml` file. 
With Spring Boot, this file is not necessary and **Entity Scanning** is used instead. 
By default, all packages below your main configuration class (the one annotated with `@SpringBootApplication`) 
are searched.

Any classes annotated with `@Entity`, `@Embeddable`, or `@MappedSuperclass` are considered.

_Example_: Entity class  
```Java
@Entity
public class Book
{
    Book() {}
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    private String title;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    //...
}
```

An Entity class requires only two pieces of metadata:
* **@Entity**: Denotes that the class should be mapped to a database.
* **@Id**: Marks which property will be used as the primary key.
* **@GeneratedValue**: For provider-generated keys.
* **@Column**: Describes how a particular property is mapped to a specific column in a table. 
  The name attribute specifies the column name. 
  The **unique** and **nullable** attributes define constraints we can place on the column.
* **@Table**: Tells the EntityManager which relational table the Entity class maps to. 
  This annotation is optional, the table name defaults to the unqualified class name.

### Data JPA Repositories

Spring Data repositories usually extend from the **Repository** or **CrudRepository** interfaces. 
Spring Data JPA repositories are interfaces that you can define to access data. 
JPA queries are created automatically from your method names.

_Example_: Repository interface
```Java
@Repository
public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book> findByTitle(String title);
}
```

The **CrudRepository** interface defines basic CRUD operations, including count, delete, 
deleteById, save, saveAll, findById, and findAll:

```Java
public interface CrudRepository<T, ID> extends Repository<T, ID> 
{
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);
    long count();
    void deleteById(ID id);
    void delete(T entity);
    void deleteAllById(Iterable<? extends ID> ids);
    void deleteAll(Iterable<? extends T> entities);
    void deleteAll();
}
```

## Spring MVC Framework

The Spring MVC framework is a rich **model-view-controller** web framework. 
Spring MVC lets you create special **@Controller** or **@RestController** beans to handle incoming HTTP requests. 

_Example_: REST controller
```Java
@RestController
@RequestMapping("/api/books")
public class BookController
{
    @Autowired
    private BookRepository bookRepository;

     @GetMapping
    public Iterable findAll() { /* ... */ }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) { /* ... */ }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) { /* ... */ }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { /* ... */ }
 
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id)
    { /* ... */ }
}
```

Methods in your controller are mapped to HTTP by using the following annotations:
* **@RequestMapping**: The class-level annotation maps a specific request path or pattern onto a controller. 
  You can then apply additional method-level annotations to make mappings more specific to handler methods.

* **@GetMapping**: Acts as a shortcut for @RequestMapping(method = RequestMethod.GET)
* **@PostMapping**: Acts as a shortcut for @RequestMapping(method = RequestMethod.POST)
* **@DeleteMapping**: Acts as a shortcut for @RequestMapping(method = RequestMethod.DELETE)
* **@PutMapping**: Acts as a shortcut for @RequestMapping(method = RequestMethod.PUT)

* **@PathVariable**: Can be used to handle template variables in the request URI mapping, and set them as method parameters.
* **@RequestBody**: Maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the 
  inbound HttpRequest body onto a Java object.
* **@ResponseStatus**: Set the status code of an HTTP response.

* **@Autowired**: We can use autowiring (dependenvy injectio) on properties, setters, and constructors.


## Resources
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

* [Spring-Boot: Data](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data)
* [Spring-Boot: Web](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web)

* [Guide to Spring @Autowired](https://www.baeldung.com/spring-autowire)
* [Spring @RequestMapping New Shortcut Annotations](https://www.baeldung.com/spring-new-requestmapping-shortcuts)
* [Spring @PathVariable Annotation](https://www.baeldung.com/spring-pathvariable)
* [Spring’s RequestBody and ResponseBody Annotations](https://www.baeldung.com/spring-request-response-body)
* [Using Spring @ResponseStatus to Set HTTP Status Code](https://www.baeldung.com/spring-response-status)

*Egon Teiniker, 2017 - 2022, GPL v3.0*