package org.se.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.junit.Before;

public abstract class HttpsSoapTestBase
{
    protected final Logger LOG = Logger.getLogger(this.getClass());
    
	protected Proxy PROXY;
	protected String USERNAME;
	protected String PASSWORD;
	
	@Before
    public void setup() throws IOException
    {
    	Properties properties = new Properties();
    	properties.load(this.getClass().getResourceAsStream("/soap.properties"));
    
    	// Authentication data
        USERNAME = properties.getProperty("soap.username");
        PASSWORD = properties.getProperty("soap.password");      
    	LOG.info("Authentication : Basic " + USERNAME + ":" + PASSWORD);    
        
        // read path to the used keystore file
        String trustStore = properties.getProperty("ssl.trustStore");
        String trustStorePassword = properties.getProperty("ssl.trustStorePassword");
        if(trustStore != null && trustStorePassword != null)
        {
            System.setProperty( "javax.net.ssl.trustStore", trustStore);
            System.setProperty( "javax.net.ssl.trustStorePassword", trustStorePassword);
            HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());
        }
        
    	String proxyAddress = properties.getProperty("proxy.address");
    	String proxyPort = properties.getProperty("proxy.port");
    	if (proxyAddress != null && proxyPort != null)
    	{
    		System.out.println("Use proxy " + proxyAddress + ":" + proxyPort);
    		int port = Integer.parseInt(proxyPort);
    		PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, port));
    	}
    	else
    	{
    		PROXY = Proxy.NO_PROXY;
    	}
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
