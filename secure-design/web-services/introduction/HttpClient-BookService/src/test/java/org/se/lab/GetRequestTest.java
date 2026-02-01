package org.se.lab;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Assert;
import org.junit.Test;

public class GetRequestTest
{
	@Test
	public void testById() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/2"))
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
                {"id":2,"author":"Robert C. Martin","title":"Clean Code","isbn":"978-0132350884"}""";
		Assert.assertEquals(EXPECTED, content);
	}


	@Test
	public void testAll() throws IOException, InterruptedException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books"))
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
