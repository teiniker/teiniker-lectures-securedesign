package org.se.lab.controller;

import org.se.lab.data.Article;
import org.se.lab.data.ArticleNotFoundException;
import org.se.lab.data.ArticleRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ArticleController
{
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/articles")
    CollectionModel<EntityModel<Article>> findAll()
    {
        List<EntityModel<Article>> articles = repository.findAll().stream()
                .map(article -> EntityModel.of(article,
                                                linkTo(methodOn(ArticleController.class).findById(article.getId())).withSelfRel(),
                                                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles")))
                .collect(Collectors.toList());

        return CollectionModel.of(articles, linkTo(methodOn(ArticleController.class).findAll()).withSelfRel());
    }

    @GetMapping("/articles/{id}")
    EntityModel<Article> findById(@PathVariable Long id)
    {
        Article article = repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));
    }

    @PostMapping("/articles")
    ResponseEntity<?> insert(@RequestBody Article newArticle)
    {
        Article article = repository.save(newArticle);
        EntityModel<Article> entityModel =
                EntityModel.of(article,
                                linkTo(methodOn(ArticleController.class).findById(article.getId())).withSelfRel(),
                                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/articles/{id}")
    ResponseEntity<?> update(@RequestBody Article newArticle, @PathVariable Long id)
    {
        Article updatedArticle = repository.findById(id)
                .map(employee -> {
                    employee.setDescription(newArticle.getDescription());
                    employee.setPrice(newArticle.getPrice());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return repository.save(newArticle);
                });
        EntityModel<Article> entityModel =
                EntityModel.of(updatedArticle,
                                linkTo(methodOn(ArticleController.class).findById(updatedArticle.getId())).withSelfRel(),
                                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/articles/{id}")
    ResponseEntity delete(@PathVariable Long id)
    {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
