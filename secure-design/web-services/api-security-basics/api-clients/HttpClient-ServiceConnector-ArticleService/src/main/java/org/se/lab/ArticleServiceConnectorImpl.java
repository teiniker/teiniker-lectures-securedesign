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
            throw new IllegalStateException("Can't send a GET request!", e);
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
            throw new IllegalStateException("Can't send a GET request!", e);
        }
    }

    @Override
    public void insert(Article article)
    {
        LOG.debug("Inserting article: " + article);
    }

    @Override
    public void update(Article article)
    {
        LOG.debug("Updating article: " + article);
    }

    @Override
    public void delete(int id)
    {
        LOG.debug("Deleting article with id: " + id);
    }
}
