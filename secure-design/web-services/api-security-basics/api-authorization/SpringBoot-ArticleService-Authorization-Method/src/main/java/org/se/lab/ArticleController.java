package org.se.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController
{
    @Autowired
    private ArticleRepository repository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(repository.findAll());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    ResponseEntity<Article> indById(@PathVariable Long id)
    {
        Optional<Article> book = repository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Article insert(@RequestBody Article article)
    {
        return repository.save(article);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody Article newArticle)
    {
        Optional<Article> optionalArticle = repository.findById(id);
        if(optionalArticle.isPresent())
        {
            Article article = optionalArticle.get();
            article.setDescription(newArticle.getDescription());
            article.setPrice(newArticle.getPrice());
            repository.save(article);
            return ResponseEntity.ok(article);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if(repository.existsById(id))
        {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
