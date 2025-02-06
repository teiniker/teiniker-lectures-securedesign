package org.se.lab;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class ArticleController
{
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/articles")
    List<Article> all()
    {
        return repository.findAll();
    }

    @GetMapping("/articles/{id}")
    Article one(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PostMapping("/articles")
    Article newArticle(@RequestBody Article newArticle)
    {
        return repository.save(newArticle);
    }

    @PutMapping("/articles/{id}")
    Article replaceEmployee(@RequestBody Article newArticle, @PathVariable Long id)
    {
        return repository.findById(id)
                .map(employee -> {
                    employee.setDescription(newArticle.getDescription());
                    employee.setPrice(newArticle.getPrice());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return repository.save(newArticle);
                });
    }

    @DeleteMapping("/articles/{id}")
    void deleteEmployee(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
