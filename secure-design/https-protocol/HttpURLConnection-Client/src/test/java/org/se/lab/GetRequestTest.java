package org.se.lab;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class GetRequestTest
	extends AbstractHttpClientTest
{
	@Test
	public void test() throws MalformedURLException 
	{
		URL url = new URL("http://localhost:8080" +
				"/Servlet-Translator/controller" +
				"?word=cat&language=Deutsch&action=Translate");
		String response = httpGetRequest(url);
		
		Assert.assertTrue(response.contains("Katze"));
	}
}
