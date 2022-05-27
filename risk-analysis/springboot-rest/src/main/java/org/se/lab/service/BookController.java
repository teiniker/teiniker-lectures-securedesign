package org.se.lab.service;

import org.se.lab.data.Book;
import org.se.lab.data.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController
{
    @Autowired
    private BookRepository bookRepository;


    public BookController(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
        bookRepository.saveAll(
                List.of(new Book(1, "Effective Java", "J. Bloch"),
                new Book(2, "Design Patterns", "GoF")));
    }

    @GetMapping
    public Iterable findAll()
    {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List findByTitle(@PathVariable String bookTitle)
    {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id)
    {
        return bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book)
    {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id)
    {
        bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id)
    {
        if (book.getId() != id)
            throw new BookIdMismatchException();

        bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}