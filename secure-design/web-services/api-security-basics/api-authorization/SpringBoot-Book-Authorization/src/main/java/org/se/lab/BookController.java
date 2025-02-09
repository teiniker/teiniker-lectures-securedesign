package org.se.lab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
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
        table.put(id3, new Book(id3, "Martin Fowler", "Refactoring", "978-0134757599"));
    }

    @GetMapping("/books")
    ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(new ArrayList(table.values()));
    }

    @GetMapping("/books/{id}")
    ResponseEntity<?> indById(@PathVariable String id)
    {
        return ResponseEntity.ok(table.get(id));
    }
}
