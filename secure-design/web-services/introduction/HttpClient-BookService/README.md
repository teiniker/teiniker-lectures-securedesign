# Example: HTTP Client for the BookService API 

## Setup

We have to start the BookService first:
```bash
$ cd SpringBoot-REST
$ mvn spring-boot:run
```

As a quick check, run the following `curl` statement:
```bash
$ curl -i http://localhost:8080/books

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Feb 2026 09:36:54 GMT

[
  {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"},
  {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"},
  {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
]
```

## GET Requests

```Java
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/books"))
            .header("Accept", "application/json")
            .GET()
            .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    int status = response.statusCode();
    String content = response.body();
```


*Egon Teiniker, 2016-2026, GPL v3.0*