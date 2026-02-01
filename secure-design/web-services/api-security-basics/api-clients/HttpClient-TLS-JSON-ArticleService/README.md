# Example: HTTP Client for the BookService API 

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

## Convert JSON to Java Objects

```Java
    protected Article convertJson2Article(String json)
    {
        Article article = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            article = mapper.readValue(json, Article.class);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return article;
    }
```

```Java
    protected List<Article> convertJsonArray2ArticleList(String json)
    {
        List<Article> list = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(json, new TypeReference<List<Article>>(){});
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return list;
    }
```

## Convert Java Objects to JSON

```Java
    protected String convertArticle2Json(Article article)
    {
        String json = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(article);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException(e);
        }
        return json;
    }
```

*Egon Teiniker, 2016-2026, GPL v3.0*