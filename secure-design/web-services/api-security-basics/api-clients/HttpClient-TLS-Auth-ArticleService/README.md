# Example: HTTP Client for the BookService API 

## Setup

We have to start the `ArticleService` first:
```bash
$ cd api-authentication/basic/SpringBoot-ArticleService-BasicAuth
$ mvn spring-boot:run
```

As a quick check, run the following `curl` statement:
```bash
$ curl -i -k -u homer:homer https://localhost:8443/articles

HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 01 Feb 2026 10:29:11 GMT

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