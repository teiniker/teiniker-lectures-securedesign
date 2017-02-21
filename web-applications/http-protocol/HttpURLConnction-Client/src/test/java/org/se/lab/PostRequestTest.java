package org.se.lab;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class PostRequestTest
	extends AbstractHttpClientTest
{
	@Test
	public void test() throws MalformedURLException 
	{
		URL url = new URL("http://localhost:8080/Servlet-SimpleLogin/controller");
		
		String requestContent = "username=student&password=student&usergroup=User&action=Login";
		
		String response = httpPostRequest(url, requestContent);
		
		Assert.assertTrue(response.contains("Welcome"));
	}
}
