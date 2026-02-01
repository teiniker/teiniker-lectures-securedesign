# Example: HTTP Client for the ArticleService API 

## Setup

We have to start the `ArticleService` first:
```bash
$ cd api-tls/SpringBoot-ArticleService-TLS
$ mvn spring-boot:run
```

As a quick check, run the following `curl` statement:
```bash
$ $ curl -i -k https://localhost:8443/articles

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Feb 2026 09:44:41 GMT

[
  {"id":1,"description":"Design Patterns","price":4295},
  {"id":2,"description":"Effective Java","price":3336}
]
```

## GET Requests

```Java
    SSLContext ssl = sslContextFromTruststore();
    HttpClient client = HttpClient.newBuilder()
            .sslContext(ssl)
            .build();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://localhost:8443/articles/1"))
            .header("Accept", "application/json")
            .GET()
            .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    int status = response.statusCode();
    String content = response.body();
```


*Egon Teiniker, 2016-2026, GPL v3.0*