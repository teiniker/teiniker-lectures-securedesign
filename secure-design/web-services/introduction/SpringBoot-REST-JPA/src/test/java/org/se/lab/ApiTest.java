package org.se.lab;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    // A test book instance to be used for retrieval
    private Book testBook;

    @BeforeEach
    public void setup()
    {
        // Ensure a clean slate for every test run
        bookRepository.deleteAll();

        // {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
        // Create and save a test Book instance
        testBook = new Book();
        testBook.setId(1);
        testBook.setAuthor("Joshua Bloch");
        testBook.setTitle("Effective Java");
        testBook.setIsbn("978-0134685991");
        bookRepository.save(testBook);
    }

    @AfterEach
    public void cleanup()
    {
        bookRepository.deleteAll();
    }

    @Test
    public void testFindBook()
    {
        // Build the URL to the find endpoint
        String url = "/api/books/" + testBook.getId();

        // Perform a GET request to retrieve the book
        ResponseEntity<Book> response = restTemplate.getForEntity(url, Book.class);

        // Verify the HTTP status code is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body contains the expected book details
        Book foundBook = response.getBody();
        assertNotNull(foundBook);
        assertEquals("Effective Java", foundBook.getTitle());
        assertEquals("Joshua Bloch", foundBook.getAuthor());
        assertEquals("978-0134685991", foundBook.getIsbn());
    }
}