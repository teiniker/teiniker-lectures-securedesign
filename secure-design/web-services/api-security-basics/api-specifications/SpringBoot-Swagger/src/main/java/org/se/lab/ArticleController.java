package org.se.lab;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController
{
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository)
    {
        this.repository = repository;
    }

    @Operation(summary = "Get all Articles", description = "Returns a list of articles", operationId = "articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok, successful operation"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping("/articles")
    List<Article> all()
    {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Article one(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PostMapping("/")
    Article newArticle(@RequestBody Article newArticle)
    {
        return repository.save(newArticle);
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id)
    {
        repository.deleteById(id);
    }
}
