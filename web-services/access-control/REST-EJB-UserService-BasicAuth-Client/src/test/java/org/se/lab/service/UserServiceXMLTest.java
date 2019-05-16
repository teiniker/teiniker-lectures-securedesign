package org.se.lab.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceXMLTest 
	extends AbstractTestBase
{
	private static final String WEB_APP_NAME = "/REST-EJB-UserService-BasicAuth/v1";
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	
	@Before
	public void init()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/insertUserTable.sql");
	}
	
	@After
	public void destroy()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");		
	}
	
	
	@Test
	public void testInsert() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		String requestContent = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"0\">"
				+ 	"<username>maggie</username>"
				+ 	"<password>AZ2wv9X4WVHLRuRFLpZChYwAQVU=</password>"
				+ "</user>";
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.setRequestProperty("Accept", "application/xml");		
		OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
		w.write(requestContent);
		w.flush();
		w.close();
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(201, httpResponseCode);
		Assert.assertEquals(0, connection.getContentLengthLong());
	}

	
	@Test
	public void testUpdate() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/1");
		String requestContent = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"1\">"
				+ 	"<username>homer</username>"
				+ 	"<password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>"
				+ "</user>";
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);		
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.setRequestProperty("Accept", "application/xml");
		
		OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
		w.write(requestContent);
		w.flush();
		w.close();
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(204, httpResponseCode);
		Assert.assertEquals(0, connection.getContentLengthLong());
	}
	
	
	@Test
	public void testDelete() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/2");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(204, httpResponseCode);
	}
	
	
	@Test
	public void testFindById() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"3\">"
				+ 	"<username>bart</username>"
				+ 	"<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>"
				+ "</user>\n";		
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
	
	
	@Test
	public void testFindAll() throws IOException, JAXBException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		connection.setRequestProperty("Accept", "application/xml");
	
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<collection>"
				+ 	"<user id=\"1\">"
				+ 		"<username>homer</username>"
				+ 		"<password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>"
				+ 	"</user>"
				+ 	"<user id=\"2\">"
				+ 		"<username>marge</username>"
				+ 		"<password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password>"
				+ 	"</user>"
				+ 	"<user id=\"3\">"
				+ 		"<username>bart</username>"
				+ 		"<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>"
				+ 	"</user>"
				+ 	"<user id=\"4\">"
				+ 		"<username>lisa</username>"
				+ 		"<password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password>"
				+ 	"</user>"
				+ "</collection>\n";
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
