package org.se.lab;

import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

public class ArticleServiceConnectorImpl
        extends ArticleServiceConnectorBase
        implements ArticleServiceConnector
{
    private final Logger LOG = Logger.getLogger(ArticleServiceConnectorImpl.class);
    private final HttpClient client;

    public ArticleServiceConnectorImpl(String trustStorePath, String trustStorePassword)
    {
        LOG.debug("ArticleServiceConnectorImpl initialized");
    
        // Disable host verification - compare with: curl -k
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        try
        {
            SSLContext ssl = sslContextFromTruststore(trustStorePath, trustStorePassword);
            client = HttpClient.newBuilder()
                    .sslContext(ssl)
                    .build();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Can't create HTTP Client!", e);
        }
    }

    @Override
    public Article findById(int id)
    {
        LOG.debug("Finding article with id: " + id);

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJson2Article(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't find article by id!", e);
        }
    }

    @Override
    public List<Article> findAll()
    {
        LOG.debug("Finding all articles");

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/articles"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJsonArray2ArticleList(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't find articles!", e);
        }
    }

    @Override
    public Article insert(Article article)
    {
        LOG.debug("Inserting article: " + article);
    
        try
        {
            String jsonBody = convertArticle2Json(article);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/articles"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            String body = response.body();
            System.out.println("Status: " + status);
            System.out.println("Body: " + body);

            if(status != 200 && status !=201)
                throw new IllegalStateException("Unexpected status code: " + status);
            else
                return convertJson2Article(body);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't insert an article!", e);
        }
    }

    @Override
    public void update(Article article)
    {
        LOG.debug("Updating article: " + article);

        try
        {
            String jsonRequest = convertArticle2Json(article);    
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/articles/" + article.id()))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")               
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
            int status = response.statusCode();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't update an article!", e);
        }
    }

    @Override
    public void delete(int id)
    {
        LOG.debug("Deleting article with id: " + id);

        try
        {   
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/articles/" + id))
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();   

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
            int status = response.statusCode();
            if (status != 200)
                throw new IllegalStateException("Unexpected status code: " + status);        
        }
        catch (IOException | InterruptedException e)
        {
            throw new IllegalStateException("Can't delete an article!", e);
        }
    }
}
