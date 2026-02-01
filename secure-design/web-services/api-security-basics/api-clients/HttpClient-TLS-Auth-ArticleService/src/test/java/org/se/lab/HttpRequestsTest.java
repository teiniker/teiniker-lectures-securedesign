package org.se.lab;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Assert;
import org.junit.Before;
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


    // curl -i -k -u homer:homer https://localhost:8443/articles/1
    @Test
    public void testUnauthorized() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/1"))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String content = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + content);

        Assert.assertEquals(401, status);   //Unauthorized
    }

    // curl -i -k -u homer:homer https://localhost:8443/articles/1
	@Test
	public void testById() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles/1"))
                .header("Authorization", basicAuth("homer", "homer"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String content = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + content);

        Assert.assertEquals(200, status);
		final String EXPECTED = """
                {"id":1,"description":"Design Patterns","price":4295}""";
		Assert.assertEquals(EXPECTED, content);
	}

    // curl -i -k -u homer:homer https://localhost:8443/articles
	@Test
	public void testAll() throws Exception
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/articles"))
                .header("Authorization", basicAuth("homer", "homer"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String content = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + content);

		Assert.assertEquals(200, status);
	}
}
