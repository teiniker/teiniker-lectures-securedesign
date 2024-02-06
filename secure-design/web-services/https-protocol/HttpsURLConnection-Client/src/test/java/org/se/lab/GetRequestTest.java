package org.se.lab;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class GetRequestTest
	extends AbstractHttpsClientTest
{
	@Test
	public void test() throws MalformedURLException 
	{
		URL url = new URL("https://localhost:8443" +
				"/Servlet-SSL-Translator/controller" +
				"?word=cat&language=Deutsch&action=Translate");
		String response = httpsGetRequest(url);
		
		Assert.assertTrue(response.contains("Katze"));
	}
}
