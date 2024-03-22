package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ServiceConnectorTest
{

    private ArticleServiceConnector service;

    @Before
    public void init()
    {
        service = new ArticleServiceConnectorJSON();
    }


    @Test
    public void testFindById()
    {
        Article article = service.findById(2);
        Assert.assertNotNull(article);
        Assert.assertEquals("Effective Java", article.getDescription());
        Assert.assertEquals(3336, article.getPrice());
    }

    @Test
    public void testFindAll()
    {
        List<Article> articles = service.findAll();
        Assert.assertNotNull(articles);
        Assert.assertEquals(2, articles.size());

        Article article = articles.get(0);
        Assert.assertEquals(Long.valueOf(1), article.getId());
        Assert.assertEquals("Design Patterns", article.getDescription());
        Assert.assertEquals(4295, article.getPrice());

        article = articles.get(1);
        Assert.assertEquals(Long.valueOf(2), article.getId());
        Assert.assertEquals("Effective Java", article.getDescription());
        Assert.assertEquals(3336, article.getPrice());
    }

    @Test
    public void testInsert()
    {
        Article article = new Article(0L, "Clean Architecture", 2672);
        service.insert(article);

        List<Article> articles = service.findAll();
        Assert.assertNotNull(articles);
        System.out.println(articles);
    }

    @Test
    public void testUpdate()
    {
        Article article = new Article(1L, "Design Patterns", 1999);
        service.update(article);

        Article updatedArticle = service.findById(1);
        Assert.assertNotNull(updatedArticle);
        Assert.assertEquals(1999, updatedArticle.getPrice());
    }

    @Test
    public void testDelete()
    {
        service.delete(2);

        List<Article> articles = service.findAll();
        Assert.assertNotNull(articles);
        System.out.println(articles);
    }
}
