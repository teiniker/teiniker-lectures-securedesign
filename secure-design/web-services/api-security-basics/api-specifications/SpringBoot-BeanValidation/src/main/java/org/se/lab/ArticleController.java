package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ArticleController
{
    private Map<Long, Article> table = new ConcurrentHashMap<>();

    ArticleController()
    {
        table.put(1L, new Article(1L, "Design Patterns", 4295));
        table.put(2L, new Article(2L, "Effective Java", 3336));
    }

    @GetMapping("/articles")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(new ArrayList(table.values()));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
		Article article = table.get(id);
		if(article == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(table.get(id), HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> insert(@Valid @RequestBody Article newArticle)
    {
        Long id = newArticle.getId();
        table.put(id, newArticle);
        return new ResponseEntity<Article>(newArticle, HttpStatus.CREATED);
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
