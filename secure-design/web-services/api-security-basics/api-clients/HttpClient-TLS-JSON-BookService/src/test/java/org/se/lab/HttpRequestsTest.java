package org.se.lab;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLContext;

public class HttpRequestsTest 
    extends HttpRequestsBase
{
    static final String TRUSTSTORE_PATH = "../../api-tls/SpringBoot-BookService-TLS/src/main/resources/server.jks";
    static final String TRUSTSTORE_PASSWORD = "student";

    private HttpClient client;

    @Before
    public void setup() throws Exception
    {
        // Disable host verification - compare with: curl -k
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        SSLContext ssl = sslContextFromTruststore(TRUSTSTORE_PATH, TRUSTSTORE_PASSWORD);
        client = HttpClient.newBuilder()
                .sslContext(ssl)
                .build();
    }


    // curl -ik https://localhost:8443/books/2
	@Test
	public void testById() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books/2"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        // {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
        Assert.assertEquals(200, status);
        Book book = convertJson2Book(body);

        Assert.assertEquals(2L, book.id());
        Assert.assertEquals("Robert C. Martin", book.author());
        Assert.assertEquals("Clean Code", book.title());
        Assert.assertEquals("978-0132350884", book.isbn());
    }

    // curl -ik https://localhost:8443/books
	@Test
	public void testAll() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

		Assert.assertEquals(200, status);
        List<Book> books = convertJsonArray2BookList(body);
        Assert.assertEquals(3, books.size());

        // {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
        Assert.assertEquals(1L, books.get(0).id());
        Assert.assertEquals("Joshua Bloch", books.get(0).author());
        Assert.assertEquals("Effective Java", books.get(0).title());
        Assert.assertEquals("978-0134685991", books.get(0).isbn());

        // {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}
        Assert.assertEquals(2L, books.get(1).id());
        Assert.assertEquals("Robert C. Martin", books.get(1).author());
        Assert.assertEquals("Clean Code", books.get(1).title());
        Assert.assertEquals("978-0132350884", books.get(1).isbn());
    
        // {"id":3,"author":"Martin Fowler","title":"Refactoring","isbn":" 978-0134757599"}
        Assert.assertEquals(3L, books.get(2).id());
        Assert.assertEquals("Martin Fowler", books.get(2).author());
        Assert.assertEquals("Refactoring", books.get(2).title());
        Assert.assertEquals(" 978-0134757599", books.get(2).isbn());
    }

    // curl -ik https://localhost:8443/books/666
    @Test
    public void testByIdNotFound() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books/666"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();

        Assert.assertEquals(404, status);
    }

    // curl -ik -X POST https://localhost:8443/books -H 'Content-type:application/json' -d '{"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}'
    @Test
    public void testInsert() throws Exception
    {
        Book newBook = new Book(0L, "Robert C. Martin", "Clean Code", "978-0132350884");
        String jsonRequest = convertBook2Json(newBook);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(201, status); // Created
        Book createdBook = convertJson2Book(body);
        Assert.assertTrue(createdBook.id() > 0);
        Assert.assertEquals(newBook.author(), createdBook.author());
        Assert.assertEquals(newBook.title(), createdBook.title());
        Assert.assertEquals(newBook.isbn(), createdBook.isbn());
    }

    // curl -ik -X PUT https://localhost:8443/books/1 -H 'Content-type:application/json' -d '{"author":"Joshua Bloch","title":"Effective Java, 2nd Edition","isbn":"978-0134685991"}'
    @Test
    public void testUpdate() throws Exception
    {
        Book updatedBook = new Book(1L, "Joshua Bloch", "Effective Java, 2nd Edition", "978-0134685991");
        String jsonRequest = convertBook2Json(updatedBook); 
    
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        int status = response.statusCode();
        String body = response.body();

        Assert.assertEquals(200, status); // OK
        Book returnedBook = convertJson2Book(body);
        Assert.assertEquals(updatedBook.id(), returnedBook.id());
        Assert.assertEquals(updatedBook.author(), returnedBook.author());
        Assert.assertEquals(updatedBook.title(), returnedBook.title());
        Assert.assertEquals(updatedBook.isbn(), returnedBook.isbn());
    }   

    // curl -ik -X DELETE https://localhost:8443/books/2
    @Test
    public void testDelete() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/books/2"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(204, status); // No Content
    }
}   
