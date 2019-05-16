package org.se.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Before;

public abstract class HttpsRestTestBase
{
	protected Logger logger = Logger.getLogger(this.getClass());

	protected Proxy PROXY;
	protected String HOST;
	protected String PORT;
	protected String BASIC_AUTHORIZATION_DATA;

	@Before
	public void setup() throws IOException
	{
		// read connection settings
		Properties properties = new Properties();
		properties.load(this.getClass().getResourceAsStream("/rest.properties"));
		HOST = properties.getProperty("rest.host");
		PORT = properties.getProperty("rest.port");
		logger.debug("Connect to " + HOST + ":" + PORT);

		// Authentication data
		String username = properties.getProperty("rest.username");
		String password = properties.getProperty("rest.password");		
		String userPassword = username + ":" + password;
		BASIC_AUTHORIZATION_DATA = Base64.encodeBase64String(userPassword.getBytes());
		
		// read path to the used keystore file
		System.setProperty( "javax.net.ssl.trustStore", properties.getProperty("ssl.trustStore"));
		System.setProperty( "javax.net.ssl.trustStorePassword", properties.getProperty("ssl.trustStorePassword"));
		
		// configure proxy
		String proxyAddress = properties.getProperty("proxy.address");
		String proxyPort = properties.getProperty("proxy.port");
		if (proxyAddress != null && proxyPort != null)
		{
			logger.debug("Use proxy " + proxyAddress + ":" + proxyPort);
			int port = Integer.parseInt(proxyPort);
			PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, port));
		} 
		else
		{
			PROXY = Proxy.NO_PROXY;
		}
	}

	protected String httpsGetRequest(URL url) throws IOException
	{
		HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());
		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(PROXY);
		connection.setRequestMethod("GET");

		connection.setRequestProperty("Authorization", "Basic " + BASIC_AUTHORIZATION_DATA);
		printHttpsCert(connection);
		
		logger.debug("URL: " + url);
		logger.debug("Request-Method: " + connection.getRequestMethod());

		// read response
		int httpResponseCode = connection.getResponseCode();
		logger.debug("Response-Code: " + httpResponseCode);
		logger.debug("Response-Content-Length:" + connection.getContentLength());

		String content = "";
		if (httpResponseCode >= 400)
		{
			InputStream is = connection.getErrorStream();
			if(is != null)
				content = readResponseContent(connection.getErrorStream());	
		}
		else
		{
			content = readResponseContent(connection.getInputStream());
		}
		return content;
	}

	
	private void printHttpsCert(HttpsURLConnection con) throws IOException
	{
		logger.debug("Response Code : " + con.getResponseCode());
		logger.debug("Cipher Suite : " + con.getCipherSuite());

		Certificate[] certs = con.getServerCertificates();
		for (Certificate cert : certs)
		{
			logger.debug("Cert Type : " + cert.getType());
			logger.debug("Cert Hash Code : " + cert.hashCode());
			logger.debug("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
			logger.debug("Cert Public Key Format : " + cert.getPublicKey().getFormat());
		}
	}

	protected String readResponseContent(InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		StringBuffer content = new StringBuffer();
		while ((line = reader.readLine()) != null)
		{
			content.append(line).append("\n");
		}
		logger.debug("Response-Content:\n" + content.toString());
		return content.toString();
	}
}
