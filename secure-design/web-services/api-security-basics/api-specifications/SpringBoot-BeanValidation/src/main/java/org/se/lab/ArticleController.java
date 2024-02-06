package org.se.lab;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ArticleController
{
    private List<Article> table = new CopyOnWriteArrayList<>();

    ArticleController()
    {
        table.add(new Article(Long.valueOf(1), "Design Patterns", 4295));
        table.add(new Article(Long.valueOf(2), "Effective Java", 3336));
    }

    @GetMapping("/articles")
    List<Article> all()
    {
        return table;
    }

    @GetMapping("/articles/{id}")
    Article one(@PathVariable Long id)
    {
        for(Article article : table)
        {
            if(article.getId() == id)
                return article;
        }
        return null;
    }

    @PostMapping("/articles")
    ResponseEntity<Article> newArticle(@Valid @RequestBody Article newArticle)
    {
        table.add(newArticle);
        return new ResponseEntity<Article>(newArticle, HttpStatus.CREATED);
    }

    @PutMapping("/articles/{id}")
    Article replaceEmployee(@RequestBody Article newArticle, @PathVariable Long id)
    {
        return null;
    }

    @DeleteMapping("/articles/{id}")
    void deleteEmployee(@PathVariable Long id)
    {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
