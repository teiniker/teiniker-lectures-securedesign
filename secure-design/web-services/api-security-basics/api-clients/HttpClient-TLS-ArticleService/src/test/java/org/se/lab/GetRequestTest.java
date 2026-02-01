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
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

public class GetRequestTest
{
    static final String TRUSTSTORE_PATH = "../../api-authentication/basic/SpringBoot-ArticleService-BasicAuth/src/main/resources/server.jks";
    static final String TRUSTSTORE_PASSWORD = "student";

    @Before
    public void setup() throws IOException
    {
        // Disable host verification - compare with: curl -k
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
    }

    // Replacement for system property setting on the command line
    // -Djavax.net.ssl.trustStore=...
    // -Djavax.net.ssl.trustStorePassword=...
    SSLContext sslContextFromTruststore() throws Exception
    {
        KeyStore ts = KeyStore.getInstance("PKCS12");
        try (InputStream in = Files.newInputStream(Path.of(TRUSTSTORE_PATH)))
        {
            ts.load(in, TRUSTSTORE_PASSWORD.toCharArray());
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);
        SSLContext ctx = SSLContext.getInstance("TLS"); // negotiates best supported TLS version
        ctx.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
        return ctx;
    }

    // curl -i -k https://localhost:8443/articles
	@Test
	public void testById() throws Exception
    {
        SSLContext ssl = sslContextFromTruststore();
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
        String content = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + content);

        Assert.assertEquals(200, status);
		final String EXPECTED = """
                {"id":1,"description":"Design Patterns","price":4295}""";
		Assert.assertEquals(EXPECTED, content);
	}

    // curl -i -k https://localhost:8443/articles
	@Test
	public void testAll() throws Exception
    {
        SSLContext ssl = sslContextFromTruststore();
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
        String content = response.body();
        System.out.println("Status: " + status);
        System.out.println("Body: " + content);

		Assert.assertEquals(200, status);
	}
}
