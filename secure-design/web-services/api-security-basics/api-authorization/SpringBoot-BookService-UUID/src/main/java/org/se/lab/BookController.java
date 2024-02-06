package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class BookController
{
    private Map<String, Book> table;

    BookController()
    {
        // Simulate database table
        table = new ConcurrentHashMap<>();

        String id1 = "da42b451-11e1-4ac9-be5e-dd3b767be1ba";
        table.put(id1, new Book(id1, "Joshua Bloch", "Effective Java", "978-0134685991"));

        String id2 = "10e290da-4c8d-4646-be93-7a03f47e3082";
        table.put(id2, new Book(id2, "Robert C. Martin", "Clean Code", "978-0132350884"));

        String id3 = "9770cdb8-1eb0-4198-b95e-242595374a20";
        table.put(id3, new Book(id3, "Martin Fowler", "Refactoring", " 978-0134757599"));
    }

    @GetMapping("/books")
    ResponseEntity<List<Book>> findAll()
    {
        return new ResponseEntity<>(new ArrayList(table.values()), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    ResponseEntity<Book> indById(@PathVariable String id)
    {
        Book item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(table.get(id), HttpStatus.OK);
    }

    @PostMapping("/books")
    ResponseEntity<Book> insert(@RequestBody Book book)
    {
        book.setId(UUID.randomUUID().toString());
        table.put(book.getId(), book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> update(@RequestBody Book book, @PathVariable String id)
    {
        Book item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            item.setAuthor(book.getAuthor());
            item.setTitle(book.getTitle());
            item.setIsbn(book.getIsbn());
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<?> delete(@PathVariable String id)
    {
        Book item = table.get(id);
        if(item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            table.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
