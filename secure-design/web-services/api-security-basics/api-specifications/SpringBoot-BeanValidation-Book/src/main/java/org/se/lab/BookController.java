package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class BookController
{
    private Map<String, Book> table = new ConcurrentHashMap<>();

    BookController()
    {
        // Simulate INSERT statement
        String id1 = UUID.randomUUID().toString();
        table.put(id1, new Book(id1, "Joshua Bloch", "Effective Java", "978-0134685991"));

        String id2 = UUID.randomUUID().toString();
        table.put(id2, new Book(id2, "Robert C. Martin", "Clean Code", "978-0132350884"));

        String id3 = UUID.randomUUID().toString();
        table.put(id3, new Book(id3, "Martin Fowler", "Refactoring", " 978-0134757599"));
    }

    @GetMapping("/books")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(new ArrayList(table.values()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> findById(@PathVariable String id)
    {
		Book book = table.get(id);
		if(book == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(table.get(id), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> insert(@Valid @RequestBody Book newBook)
    {
        String id = UUID.randomUUID().toString();
        newBook.setId(id);
        table.put(id, newBook);
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

    /*
     * Exception Handler for MethodArgumentNotValidException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
