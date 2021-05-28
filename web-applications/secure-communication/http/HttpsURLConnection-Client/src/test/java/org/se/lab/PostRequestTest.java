package org.se.lab;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class PostRequestTest
	extends AbstractHttpsClientTest
{
	@Test
	public void test() throws MalformedURLException 
	{
		URL url = new URL("https://localhost:8443/Servlet-SSL-Translator/controller");
		String requestContent = "word=cat&language=Deutsch&action=Translate";
		String response = httpPostRequest(url, requestContent);
		
		Assert.assertTrue(response.contains("Katze"));
	}
}
