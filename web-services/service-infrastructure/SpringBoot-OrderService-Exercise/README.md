# Spring Boot: OrderService 

Spring Boot provides a very good support to building RESTful Web Services.
```Java
@RestController
@RequestMapping("/orders")
public class OrderProcessingService 
{
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order)
    {
        //...
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) 
    {
        //...
        return new ResponseEntity<Order>(orders.get(id), HttpStatus.OK);
    }
}
```
The following annotations can be used:
* The **@RestController** annotation is used to define the RESTful web services.
* The **@RequestMapping** annotation is used to define the Request URI to access the REST Endpoints.

* The **@GetMapping** annotation maps HTTP GET requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.GET)`.
* The **@PostMapping** annotation maps HTTP POST requests onto specific handler methods. 
   It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.POST)`.
* The  @PutMapping annotation maps HTTP PUT requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.PUT)`.
* The **@DeleteMapping** annotation maps HTTP DELETE requests onto specific handler methods. 
    It is a composed annotation that acts as a shortcut for `@RequestMapping(method = RequestMethod.DELETE)`.

* The **@PathVariable** annotation is used to define the custom or dynamic request URI. 
   The Path variable in request URI is defined as curly braces {}.
* The **@RequestParam** annotation is used to read the request parameters from the Request URL. 
* The **@RequestBody** annotation is used to define the request body content type.


We start with a **simple microservice**:
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
  
## References
* [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
