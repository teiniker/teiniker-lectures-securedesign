# JSON Mapping Attacks


## JSON Injection Attack

```Java
    ObjectMapper mapper = new ObjectMapper();
    String json =
            """
                {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "hack":666}
            """;
    Book book = mapper.readValue(json, Book.class);
```

Adding unknown attributes such as `"hack":666` triggers an `UnrecognizedPropertyException`, 
so this **attack doesn’t work**.


## JSON Missing Attributes

```Java
    ObjectMapper mapper = new ObjectMapper();
    String json =
            """
                {"author":"Joshua Bloch","isbn":"978-0134685991"}
            """;

    Book book = mapper.readValue(json, Book.class);

    Assert.assertEquals(0, book.getId());   // No value mapped
    Assert.assertEquals("Joshua Bloch", book.getAuthor());
    Assert.assertNull(book.getTitle());             // No value mapped
    Assert.assertEquals("978-0134685991", book.getIsbn());
```

When attributes are missing in a JSON object, mapping still proceeds and the 
absent fields receive default values (0, null, ...).


## JSON Override Attributes

```Java
    ObjectMapper mapper = new ObjectMapper();
    String json =
        """
            {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "id":666}
        """;
    
    Book book = mapper.readValue(json, Book.class);
    Assert.assertEquals(666, book.getId());	// Attack worked !!
```

When a duplicate attribute appears, the last value wins, so the **attack succeeds**.


## JSON Validation

```Java
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    String json =
            """
                {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991", "id":666}
            """;

    // Validate
    mapper.readTree(json);
```

With the correct configuration, the JSON parser can detect duplicated attributes 
and throw a `JsonProcessingException`.



## References
* [Baeldung: Intro to the Jackson ObjectMapper](https://www.baeldung.com/jackson-object-mapper-tutorial)
* [Jackson Project Home @github](https://github.com/FasterXML/jackson)

*Egon Teiniker, 2017-2024, GPL v3.0*	