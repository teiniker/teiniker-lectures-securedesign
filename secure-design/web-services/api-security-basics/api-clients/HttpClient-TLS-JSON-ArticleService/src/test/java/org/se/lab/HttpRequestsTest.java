package org.se.lab;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLContext;

public class HttpRequestsTest extends HttpRequestsBase
{
    static final String TRUSTSTORE_PATH = "../../api-authentication/basic/SpringBoot-ArticleService-BasicAuth/src/main/resources/server.jks";
    static final String TRUSTSTORE_PASSWORD = "student";

    @Before
    public void setup() throws IOException
    {
        // Disable host verification - compare with: curl -k
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
    }


    // curl -i -k https://localhost:8443/articles
	@Test
	public void testById() throws Exception
    {
        SSLContext ssl = sslContextFromTruststore(TRUSTSTORE_PATH, TRUSTSTORE_PASSWORD);
        HttpClient client = HttpClient.newBuilder()
                .sslContext(ssl)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/1"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(200, status);
        Article article = convertJson2Article(body);

        Assert.assertEquals(Long.valueOf(1), article.id());
        Assert.assertEquals("Design Patterns", article.description());
        Assert.assertEquals(4295, article.price());
	}

    // curl -i -k https://localhost:8443/articles
	@Test
	public void testAll() throws Exception
    {
        SSLContext ssl = sslContextFromTruststore(TRUSTSTORE_PATH, TRUSTSTORE_PASSWORD);
        HttpClient client = HttpClient.newBuilder()
                .sslContext(ssl)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

		Assert.assertEquals(200, status);
        List<Article> articles = convertJsonArray2ArticleList(body);
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
}
