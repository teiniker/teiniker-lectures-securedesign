package org.se.lab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.net.ssl.SSLContext;

public class HttpRequestsTest extends HttpRequestsBase
{
    static final String TRUSTSTORE_PATH = "../../api-authentication/basic/SpringBoot-ArticleService-BasicAuth/src/main/resources/server.jks";
    static final String TRUSTSTORE_PASSWORD = "student";
    private HttpClient client;

    @Before
    public void setup() throws Exception
    {
        // Disable host verification - compare with: curl -k
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");

        SSLContext ssl = sslContextFromTruststore(TRUSTSTORE_PATH, TRUSTSTORE_PASSWORD);
        client = HttpClient.newBuilder()
                .sslContext(ssl)
                .build();

    }

    // curl -i -k https://localhost:8443/articles/1
	@Test
	public void testById() throws IOException, InterruptedException
    {
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
		final String EXPECTED = """
                {"id":1,"description":"Design Patterns","price":4295}""";
		Assert.assertEquals(EXPECTED, body);
	}

    // curl -i -k https://localhost:8443/articles/666
    @Test
    public void testById_NotFound() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/666"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(404, status);
    }

    // curl -i -k https://localhost:8443/articles
	@Test
	public void testAll() throws IOException, InterruptedException
    {
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
	}

    // curl -i -k -X POST https://localhost:8443/articles -H 'Content-type:application/json' -d '{"description": "Microservices Patterns: With examples in Java", "price": 2550}'
    @Test
    public void testInsert() throws IOException, InterruptedException
    {
        String jsonRequest = """
                {"description": "Microservices Patterns: With examples in Java", "price": 2550}""";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
     
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        Assert.assertEquals(200, status);
    }

    // curl -i -k -X PUT https://localhost:8443/articles/2 -H 'Content-type:application/json' -d '{"description": "Effective Java", "price": 9999}'
    @Test
    public void testUpdate() throws IOException, InterruptedException
    {
        String jsonRequest = """
                {"description": "Effective Java", "price": 9999}""";    
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/2"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")               
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);    
        
        Assert.assertEquals(200, status);
        Assert.assertTrue(body.contains("9999"));
    }

    // curl -i -k -X DELETE https://localhost:8443/articles/3
    @Test @Ignore
    public void testDelete() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/3"))
                .header("Accept", "application/json")
                .DELETE()
                .build();   

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());     
        int status = response.statusCode();
        String body = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);
        
        Assert.assertEquals(200, status);
    }
}
