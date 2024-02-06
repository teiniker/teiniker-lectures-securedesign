package org.se.lab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class BookController
{
    private Map<Long, Book> table = new ConcurrentHashMap<>();

    BookController()
    {
        // Simulate INSERT statement
        table.put(Long.valueOf(1), new Book(1, "Joshua Bloch", "Effective Java", "978-0134685991"));
        table.put(Long.valueOf(2), new Book(2, "Robert C. Martin", "Clean Code", "978-0132350884"));
        table.put(Long.valueOf(3), new Book(3, "Martin Fowler", "Refactoring", " 978-0134757599"));
    }

    @GetMapping("/books")
    ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(new ArrayList(table.values()));
    }

    @GetMapping("/books/{id}")
    ResponseEntity<?> indById(@PathVariable Long id)
    {
        return ResponseEntity.ok(table.get(id));
    }
}
