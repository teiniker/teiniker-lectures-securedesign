# HttpClient 

HttpClient is the **standard HTTP/REST client built into the JDK**, 
located in the `java.net.http` package.

It was introduced in Java 11 and is fully mature and stable in Java 21.

## Core Building Blocks

There are three main types used in the context of the HttpClient:
HttpClient, HttpRequest, and HttpResponse<T>.

### HttpClient

Represents a reusable client:
* Connection pooling
* TLS configuration
* Authentication
* Redirects
* Proxies
* HTTP version

_Example:_

```Java
HttpClient client = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .build();
```

Create once, the `HttpClient` can be reused across requests.


### HttpRequest

An immutable HTTP request:
* Method (GET, POST, PUT, …)
* URI
* Headers
* Body

_Example:_

```Java
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://localhost:8443/articles"))
    .header("Accept", "application/json")
    .GET()
    .build();
```

### HttpResponse<T>

The response, parameterized by body type:

_Example:_

```Java
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

int status = response.statusCode();
String body = response.body();
```

The body handler decides **how the body is read**.


## References 

* [Baeldung: Exploring the New HTTP Client in Java](https://www.baeldung.com/java-9-http-client)
* [JavDocs: Class HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html)