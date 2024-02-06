package org.se.lab.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Before;

import javax.net.ssl.HttpsURLConnection;

public abstract class AbstractTestBase
{
    protected final Logger LOG = Logger.getLogger(this.getClass());
    
	protected Proxy PROXY;
	protected String HOST;
	protected String PORT;
	protected String BASIC_AUTHORIZATION_DATA;


	@Before
    public void setup() throws IOException
    {
    	Properties properties = new Properties();
    	properties.load(this.getClass().getResourceAsStream("/rest.properties"));
    	HOST = properties.getProperty("rest.host");
    	PORT = properties.getProperty("rest.port");
    	LOG.debug("Connect to " + HOST + ":" + PORT);

		// TLS data
		System.setProperty( "javax.net.ssl.trustStore", properties.getProperty("ssl.trustStore"));
		System.setProperty( "javax.net.ssl.trustStorePassword", properties.getProperty("ssl.trustStorePassword"));

    	// Authentication data
        String username = properties.getProperty("rest.username");
        String password = properties.getProperty("rest.password");      
        String userPassword = username + ":" + password;
        BASIC_AUTHORIZATION_DATA = Base64.encodeBase64String(userPassword.getBytes());
    	
        
    	String proxyAddress = properties.getProperty("proxy.address");
    	String proxyPort = properties.getProperty("proxy.port");
    	if (proxyAddress != null && proxyPort != null)
    	{
    	    LOG.debug("Use proxy " + proxyAddress + ":" + proxyPort);
    		int port = Integer.parseInt(proxyPort);
    		PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, port));
    	}
    	else
    	{
    		PROXY = Proxy.NO_PROXY;
    	}

		HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());
    }

	
	/*
	 * Utility methods
	 */
	
	protected String readResponseContent(InputStream in) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		StringBuffer content = new StringBuffer();
		while ((line = reader.readLine()) != null)
		{
			content.append(line).append("\n");
		}
		return content.toString();
	}
}
