package org.se.lab;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class GetRequestTest
	extends AbstractHttpsClientTest
{
	@Test
	public void testById() throws MalformedURLException
	{
		URL url = new URL("https://localhost:8443/articles/1");
		String response = httpsGetRequest(url);

		final String EXPECTED = """
  			{\"id\":1,\"description\":\"Design Patterns\",\"price\":4295}
  			""";
		Assert.assertEquals(EXPECTED, response);
	}

	@Test
	public void testAll() throws MalformedURLException
	{
		URL url = new URL("https://localhost:8443/articles");
		String response = httpsGetRequest(url);

		final String EXPECTED = """
					[{"id":1,"description":"Design Patterns","price":4295},{"id":2,"description":"Effective Java","price":3336}]
					""";
		Assert.assertEquals(EXPECTED, response);
	}

}
