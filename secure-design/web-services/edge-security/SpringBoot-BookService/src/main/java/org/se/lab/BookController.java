package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class BookController
{
    private static long sequence = 1;
    private static long nextValue()
    {
        return sequence++;
    }

    private Map<Long, Book> table;

    BookController()
    {
        // Simulate database table
        table = new ConcurrentHashMap<>();

        long id1 = nextValue();
        table.put(id1, new Book(id1, "Joshua Bloch", "Effective Java", "978-0134685991"));

        long id2 = nextValue();
        table.put(id2, new Book(id2, "Robert C. Martin", "Clean Code", "978-0132350884"));

        long id3 = nextValue();
        table.put(id3, new Book(id3, "Martin Fowler", "Refactoring", " 978-0134757599"));
    }

    @GetMapping("/books")
    ResponseEntity<List<Book>> findAll()
    {
        return new ResponseEntity<>(new ArrayList(table.values()), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    ResponseEntity<Book> indById(@PathVariable long id)
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
        book.setId(nextValue());
        table.put(book.getId(), book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> update(@RequestBody Book book, @PathVariable long id)
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
    ResponseEntity<?> delete(@PathVariable long id)
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
