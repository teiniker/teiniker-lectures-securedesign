package org.se.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import org.junit.Before;

public abstract class AbstractTestBase
{
	protected Proxy PROXY;
	protected String HOST;
	protected String PORT;


	@Before
    public void setup() throws IOException
    {
    	Properties properties = new Properties();
    	properties.load(this.getClass().getResourceAsStream("/rest.properties"));
    	HOST = properties.getProperty("rest.host");
    	PORT = properties.getProperty("rest.port");
    	System.out.println("Connect to " + HOST + ":" + PORT);
    
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
