package org.se.lab.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;

public class UserServiceXMLITCase
	extends AbstractTestBase
{
	private static final String WEB_APP_NAME = "/REST-EJB-UserService-MessageEncryption-JWE/v1";
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
	public void testInsert() throws IOException, JAXBException, DecoderException, KeyLengthException, JOSEException
	{
		// Request
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
		String requestContent = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"0\">"
				+   "<username>maggie</username>"
				+   "<password>AZ2wv9X4WVHLRuRFLpZChYwAQVU=</password>"
				+ "</user>";
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/jose+json");
		
		JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
        Payload payload = new Payload(requestContent);
        JWEObject jweObject = new JWEObject(header, payload);
        byte[] key = Hex.decodeHex(keyProperty.toCharArray());
        jweObject.encrypt(new DirectEncrypter(key));
        String jweString = jweObject.serialize();
        System.out.println(jweString);
		
		OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
		w.write(jweString);
		w.flush();
		w.close();
		
		// Response
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(201, httpResponseCode);
		Assert.assertEquals(0, connection.getContentLengthLong());
	}
	
	
	@Test
	public void testFindById() throws IOException, JAXBException
	{
		URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/3");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		int httpResponseCode = connection.getResponseCode();
		Assert.assertEquals(200, httpResponseCode);
		String content = readResponseContent(connection.getInputStream());	
		final String EXPECTED = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<user id=\"3\">"
				+   "<username>bart</username>"
				+   "<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>"
				+ "</user>\n";		
		System.out.println("Response-Content:\n" + content);
		Assert.assertEquals(EXPECTED, content);
	}
}
