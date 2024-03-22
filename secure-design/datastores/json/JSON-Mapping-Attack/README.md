 # JSON to Java Mapping	

The **Jackson library** is a powerful, high-performance JSON processing library in Java 
that provides functionality for **serializing Java objects into JSON** format and 
**deserializing JSON strings into Java objects**. 

It supports a wide range of features, including data binding (simple POJOs to and from JSON), 
streaming API (for reading and writing JSON), and tree model (for constructing a tree 
representation of a JSON string). 

Jackson is highly customizable, supports annotations for influencing 
serialization/deserialization processes, and can handle complex data types, 
including collections, generics, and custom serializers/deserializers. 

Its versatility and efficiency make it a popular choice for JSON processing in 
Java applications, ranging from simple data transfer objects to complex data 
transformation and mapping scenarios.

_Example:_ Serialize Java Object to JSON String 
```Java
@Test
public void testJava2JSON() throws JsonProcessingException
{
    Book book = new Book(1, "Joshua Bloch", "Effective Java", "978-0134685991");

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(book);

    String expected =
    """
        {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
    """;
    Assert.assertEquals(expected.trim(), json);
}
```

_Example:_ Deserialize JSON String into Java Object
```Java
@Test
public void testJSON2Java() throws JsonProcessingException
{
    ObjectMapper mapper = new ObjectMapper();
    String json =
    """
        {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
    """;
    Book book = mapper.readValue(json, Book.class);

    Assert.assertEquals(1, book.getId());
    Assert.assertEquals("Joshua Bloch", book.getAuthor());
    Assert.assertEquals("Effective Java", book.getTitle());
    Assert.assertEquals("978-0134685991", book.getIsbn());
}
```

## References
* [Baeldung: Intro to the Jackson ObjectMapper](https://www.baeldung.com/jackson-object-mapper-tutorial)
* [Jackson Project Home @github](https://github.com/FasterXML/jackson)

*Egon Teiniker, 2017-2024, GPL v3.0*	