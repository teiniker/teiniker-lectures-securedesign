# Hypermedia as the Engine of Application State (HATEOAS)

HATEOAS is a very important concept of REST. 
It is one of the concepts that **differentiate REST from RPC**.

With HATEOAS, RESTful web services provide information dynamically through hypermedia.
Hypermedia is a part of the content that you receive from a REST call response.
This hypermedia content contains links to different types of media such as text, images, and videos.

**Hypermedia links can be contained either in HTTP headers or the response body**.

## Spring Boot HATEOAS

Introducing **Spring HATEOAS**, a Spring project aimed at helping us write hypermedia-driven outputs.

## Service Setup

We use Maven to compile and run the service.
```
$ mvn spring-boot:run
```

## Access the REST Service
A critical ingredient to any RESTful service is **adding links to relevant operations**.

Adding all these links makes it possible to **evolve REST services over time**. 

Existing links can be maintained while new links can be added in the future. 
Newer clients may take advantage of the new links, while legacy clients can sustain 
themselves on the old links. 

This is especially helpful if services get relocated and moved around. 
As long as the link structure is maintained, clients can STILL find and interact with things.

### Find a Particular Article

```    
$ curl -i http://localhost:8080/articles/2
HTTP/1.1 200 
Content-Type: application/hal+json
Transfer-Encoding: chunked
Date: Thu, 04 Nov 2021 18:48:57 GMT

{
   "_links" : {
      "articles" : {
         "href" : "http://localhost:8080/articles"
      },
      "self" : {
         "href" : "http://localhost:8080/articles/2"
      }
   },
   "description" : "Effective Java",
   "id" : 2,
   "price" : 3336
}
```
This output shows not only the data elements `id`, `description` and `price`, but also a `_links` entry containing 
two URIs. This entire document is formatted using **HAL**.

HAL is a **lightweight mediatype** that allows encoding not just data but also hypermedia controls, 
alerting consumers to other parts of the API they can navigate toward. 

In this case, there is a `self` link (kind of like a `this` statement in code) along with a link back to the 
aggregate root.


### Find all Articles

```
$ curl -i http://localhost:8080/articles
HTTP/1.1 200 
Content-Type: application/hal+json
Transfer-Encoding: chunked
Date: Thu, 04 Nov 2021 19:24:04 GMT

{
   "_embedded" : {
      "articleList" : [
         {
            "_links" : {
               "articles" : {
                  "href" : "http://localhost:8080/articles"
               },
               "self" : {
                  "href" : "http://localhost:8080/articles/1"
               }
            },
            "description" : "Design Patterns",
            "id" : 1,
            "price" : 4295
         },
         {
            "_links" : {
               "articles" : {
                  "href" : "http://localhost:8080/articles"
               },
               "self" : {
                  "href" : "http://localhost:8080/articles/2"
               }
            },
            "description" : "Effective Java",
            "id" : 2,
            "price" : 3336
         }
      ]
   },
   "_links" : {
      "self" : {
         "href" : "http://localhost:8080/articles"
      }
   }
}
```
For this **aggregate root**, which serves up a collection of employee resources, there is a top-level
`self` link. The collection is listed underneath the `_embedded` section.
This is how **HAL** represents collections.

And each individual member of the collection has their information as well as related links.


### Insert an Article
```
$ curl -i -X POST http://localhost:8080/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'
HTTP/1.1 201 
Location: http://localhost:8080/articles/3
Content-Type: application/hal+json
Transfer-Encoding: chunked
Date: Thu, 04 Nov 2021 19:21:42 GMT

{
   "_links" : {
      "articles" : {
         "href" : "http://localhost:8080/articles"
      },
      "self" : {
         "href" : "http://localhost:8080/articles/3"
      }
   },
   "description" : "Microservices Patterns: With examples in Java",
   "id" : 3,
   "price" : 2550
}
```

This response not only has the resulting object rendered in HAL, but also the **Location** header populated 
with `http://localhost:8080/articles/3`. 
A hypermedia powered client could opt to **surf** to this new resource and proceed to interact with it.

### Update an Article
```    
$ curl -i -X PUT http://localhost:8080/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'
HTTP/1.1 201 
Location: http://localhost:8080/articles/2
Content-Type: application/hal+json
Transfer-Encoding: chunked
Date: Thu, 04 Nov 2021 19:36:31 GMT

{
   "_links" : {
      "articles" : {
         "href" : "http://localhost:8080/articles"
      },
      "self" : {
         "href" : "http://localhost:8080/articles/2"
      }
   },
   "description" : "Effective Java",
   "id" : 2,
   "price" : 9999
}
```
That article resource has now been updated and the location URI sent back.

### Delete an Article
```    
$ curl -i -X DELETE http://localhost:8080/articles/1
HTTP/1.1 204 
Date: Thu, 04 Nov 2021 19:43:08 GMT
```


## Implementation

To enable **Spring HATEOAS** to this project we add the following dependency to the `pom.xml`
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```
This library will give us the constructs to define a RESTful service and then render it in an 
acceptable format for client consumption.

A critical ingredient to any RESTful service is **adding links to relevant operations**.

```Java
@GetMapping("/articles/{id}")
EntityModel<Article> findById(@PathVariable Long id)
{
    Article article = repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
 
    return EntityModel.of(article,
            linkTo(methodOn(ArticleController.class).findById(id)).withSelfRel(),
            linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));
}
```
Here we have added the following elements:
* `EntityModel<>`\
  The return type of the method has changed from `Article` to `EntityModel<Article>`.
  `EntityModel<T>` is a generic container from Spring HATEOAS that includes not only the data but a collection of links.

* `linkTo(methodOn(ArticleController.class).findById(id)).withSelfRel()`\
  Asks that Spring HATEOAS build a link to the `ArticleController.findById()` method, and flag it as a **self link**.

* `linkTo(methodOn(ArticleController.class).findAll()).withRel("articles")`\
  Asks Spring HATEOAS to build a link to the aggregate root, `findAll()`, and call it "articles".

One of Spring HATEOAS’s core types is Link. It includes a `URI` and a `rel` (relation).
Roy Fielding encourages building APIs with the same techniques that made the web successful, and links are one of them.

To **make the aggregate root also more RESTful**, you want to include top level links while also including any
RESTful components within.

```Java
@GetMapping("/articles")
CollectionModel<EntityModel<Article>> findAll()
{
    List<EntityModel<Article>> articles = repository.findAll().stream()
            .map(article -> EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).findById(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles")))
            .collect(Collectors.toList());

    return CollectionModel
        .of(articles, linkTo(methodOn(ArticleController.class)
        .findAll()).withSelfRel());
}
```
Here we have added the following elements:
* `CollectionModel<>`\ 
   This is another Spring HATEOAS container. It is aimed at encapsulating collections of resources—instead of a 
   single resource entity, like `EntityModel<>`. `CollectionModel<>` lets us include links too.

We update the **POST method** like this:
```Java
@PostMapping("/articles")
ResponseEntity<?> insert(@RequestBody Article newArticle)
{
    Article article = repository.save(newArticle);
    EntityModel<Article> entityModel =
            EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).findById(article.getId())).withSelfRel(),  
                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
}
```

* Spring MVC’s `ResponseEntity` is used to create an **HTTP 201 Created** status message. 

* This type of response typically includes a **Location** response header, and we use the URI derived from the 
  model’s self-related link.

* Additionally, return the model-based version of the saved object.


The **PUT method** method needs similar changes:
```Java
@PutMapping("/articles/{id}")
ResponseEntity<?> update(@RequestBody Article newArticle, @PathVariable Long id)
{
    //...
    EntityModel<Article> entityModel =
            EntityModel.of(updatedArticle,
                linkTo(methodOn(ArticleController.class).findById(updatedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
}
```
Since we want a more detailed HTTP response code than **200 OK**, we will use the Spring `ResponseEntity` wrapper. 
It has a handy static method `created()` where we can plug in the resource’s URI. 
It’s debatable if **HTTP 201 Created** carries the right semantics since we aren’t necessarily "creating" 
a new resource. But it comes pre-loaded with a **Location** response header, so run with it.

Finally, we update the **DELETE method**:
```Java
@DeleteMapping("/articles/{id}")
ResponseEntity delete(@PathVariable Long id)
{
    repository.deleteById(id);
    return ResponseEntity
        .noContent().build();
}
```
This returns an HTTP **204 No Content** response.



## References

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

* [HAL - Hypertext Application Language](https://stateless.group/hal_specification.html)

* Sourabh Sharma. **Modern API Development with Spring and Spring Boot:
  Design highly scalable and maintainable APIs with REST, gRPC, GraphQL, and the reactive paradigm**.  
  Packt Publishing, 2021

*Egon Teiniker, 2016-2022, GPL v3.0*