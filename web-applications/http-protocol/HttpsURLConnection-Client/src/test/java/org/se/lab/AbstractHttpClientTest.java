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
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.log4j.Logger;
import org.junit.Before;

public abstract class AbstractHttpClientTest
{
	protected Logger logger = Logger.getLogger(this.getClass());

	protected Proxy PROXY;
	protected String HOST;
	protected String PORT;

	@Before
	public void setup() throws IOException
	{
		// read connection settings
		Properties properties = new Properties();
		properties.load(this.getClass().getResourceAsStream("/http.properties"));
		
		HOST = properties.getProperty("http.host");
		PORT = properties.getProperty("http.port");
		logger.debug("Connect to " + HOST + ":" + PORT);

		// read path to the used keystore file
		System.setProperty( "javax.net.ssl.trustStore", properties.getProperty("ssl.trustStore"));
		System.setProperty( "javax.net.ssl.trustStorePassword", properties.getProperty("ssl.trustStorePassword"));
		
		// configure proxy
		String proxyAddress = properties.getProperty("http.proxy.address");
		String proxyPort = properties.getProperty("http.proxy.port");
		if (proxyAddress != null && proxyPort != null)
		{
			logger.debug("Use proxy " + proxyAddress + ":" + proxyPort);
			int port = Integer.parseInt(proxyPort);
			PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyAddress, port));
		} else
		{
			PROXY = Proxy.NO_PROXY;
		}
	}

	protected String httpsGetRequest(URL url)
	{
		HttpsURLConnection connection = null;
		try
		{
			HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());

			connection = (HttpsURLConnection) url.openConnection(PROXY);
			printHttpsCert(connection);

			logger.debug("URL: " + url);
			logger.debug("Request-Method: " + connection.getRequestMethod());

			// read response
			int httpResponseCode = connection.getResponseCode();
			logger.debug("Response-Code: " + httpResponseCode);
			logger.debug("Response-Content-Length:"
					+ connection.getContentLength());

			String content;
			if (httpResponseCode >= 400)
			{
				content = readResponseContent(connection.getErrorStream());
				throw new HttpClientException("Invalid HTTP response!", url,
						connection.getResponseCode(), content);
			}

			content = readResponseContent(connection.getInputStream());
			return content.toString();
		} 
		catch (IOException e)
		{
			throw new HttpClientException("IO problems", e);
		} 
		finally
		{
			if (connection != null)
				connection.disconnect();
		}
	}

	private void printHttpsCert(HttpsURLConnection con)
	{
		if (con != null)
		{
			try
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
			catch (SSLPeerUnverifiedException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
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
