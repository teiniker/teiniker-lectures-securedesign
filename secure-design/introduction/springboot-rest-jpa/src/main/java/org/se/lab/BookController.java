package org.se.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/books")
public class BookController
{
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> indById(@PathVariable long id)
    {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public Book insert(@RequestBody Book book)
    {
        return bookRepository.save(book);
    }
}
