package org.se.lab;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.net.ssl.SSLContext;

public class HttpRequestsTest 
{
    static final String TRUSTSTORE_PATH = "../../api-authentication/basic/SpringBoot-ArticleService-BasicAuth/src/main/resources/server.jks";
    static final String TRUSTSTORE_PASSWORD = "student";
    private ArticleServiceConnector service;

    @Before
    public void setup() throws IOException
    {
        service = new ArticleServiceConnectorImpl(TRUSTSTORE_PATH, TRUSTSTORE_PASSWORD);
    }


    // curl -i -k https://localhost:8443/articles
	@Test
	public void testById() 
    {
        Article article = service.findById(1);
        
        Assert.assertEquals(Long.valueOf(1), article.id());
        Assert.assertEquals("Design Patterns", article.description());
        Assert.assertEquals(4295, article.price());
	}


    // curl -i -k https://localhost:8443/articles
	@Test
	public void testAll() 
    {
        List<Article> articles = service.findAll();
        
        Assert.assertEquals(2, articles.size());

        // {"id":1,"description":"Design Patterns","price":4295}
        Assert.assertEquals(Long.valueOf(1), articles.get(0).id());
        Assert.assertEquals("Design Patterns", articles.get(0).description());
        Assert.assertEquals(4295, articles.get(0).price());

        // {"id":2,"description":"Effective Java","price":3336}
        Assert.assertEquals(Long.valueOf(2), articles.get(1).id());
        Assert.assertEquals("Effective Java", articles.get(1).description());
        Assert.assertEquals(3336, articles.get(1).price());
    }

    @Test
    public void testInsert() {
        Article article = new Article(0L, "Clean Architecture", 2672);
        Article insertedArticle = service.insert(article);

        Assert.assertTrue(insertedArticle.id() > 0);
        Assert.assertEquals("Clean Architecture", insertedArticle.description());
        Assert.assertEquals(2672, insertedArticle.price());
    }

    @Test
    public void testUpdate()
    {
        Article article = new Article(1L, "Design Patterns", 999);
        service.update(article);

        Article updatedArticle = service.findById(1);
        Assert.assertNotNull(updatedArticle);
        Assert.assertEquals(999, updatedArticle.price());
    }

    @Test @Ignore
    public void testDelete()
    {
        service.delete(2);
    }
}
