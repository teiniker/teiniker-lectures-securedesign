Bean Validation - Books
-------------------------------------------------------------------------------

Given a working REST service:
$ mvn spring-boot:run

$ curl -i -k https://localhost:8443/books

Extend the service with the following features:

A) Insert a new Book
    - Valid data
    $ curl -ik -X POST https://localhost:8443/books -H 'Content-type:application/json' -d '{"id":"","author":"Sam Newman","title":"Building Microservices","isbn":"978-1492034025"}'

    - Invalid author
    $ curl -ik -X POST https://localhost:8443/books -H 'Content-type:application/json' -d '{"id":"","author":"Sam","title":"Building Microservices","isbn":"978-1492034025"}'

    - Invalid title
    $ curl -ik -X POST https://localhost:8443/books -H 'Content-type:application/json' -d '{"id":"","author":"Sam Newman","title":"Building","isbn":"978-1492034025"}'

    - Invalid isbn
    $ curl -ik -X POST https://localhost:8443/books -H 'Content-type:application/json' -d '{"id":"","author":"Sam Newman","title":"Building Microservices","isbn":"97-1492034025"}'

B) Bean Validation
    @NotNull
    @Size(min=5, max=255, message="Invalid author!")
    private String author;
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    @NotNull
    @Size(min=10, max=255, message="Invalid title!")
    private String title;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Pattern(regexp="^[0-9]{3}-[0-9]{10}$", message="Invalid ISBN format!")
    private String isbn;
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

C) Swagger
$ curl -k https://localhost:8443/v3/api-docs.yaml
...
components:
  schemas:
    Book:
      required:
      - author
      - title
      type: object
      properties:
        id:
          type: string
        author:
          maxLength: 255
          minLength: 5
          type: string
        title:
          maxLength: 255
          minLength: 10
          type: string
        isbn:
          pattern: "^[0-9]{3}-[0-9]{10}$"
          type: string

Tip: Also add the needed dependencies in pom.xml 
