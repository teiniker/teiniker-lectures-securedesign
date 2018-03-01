package org.se.lab;

import java.io.*;
import java.net.Socket;
import java.util.Date;


public class HttpRequestHandler
{
	/*
	 * Constructor
	 */
	public HttpRequestHandler(String webDirectory)
	{
		if(webDirectory == null)
			throw new IllegalArgumentException();
		this.webDirectory = webDirectory;
	}
	
	
	/*
	 * Property: webDirectory:String
	 */
	private final String webDirectory;
	
	
	public void handleRequest(Socket connection)
	{
		Logger.log("[" + generateDate() + "]" + connection + " - thread id: " + Thread.currentThread().getId());
        try
        {
            OutputStream out = connection.getOutputStream();
            InputStream in = connection.getInputStream();
            handleRequest(in, out);
            connection.close();
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Can't handle HTTP request!", e);
        }
        finally
        {
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (IOException e)
                {
                    throw new IllegalStateException("Can't close connection!", e);
                }
            }
        }
	}
	
	
	private void handleRequest(InputStream in, OutputStream out)
       throws IOException
	{
		Writer w = new OutputStreamWriter(out);
        w.write(generateResponse("index.html"));
        w.flush();
	}
	
	private String generateResponse(String filename)
       throws IOException
	{
		String page = readHtmlFile(filename);
		StringBuilder buffer = new StringBuilder();
		buffer.append(generateHeader(page.length()));
		buffer.append(page);		
		return buffer.toString();
	}
	
	private String generateHeader(int length)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("HTTP/1.1 200 OK\r\n");
		buffer.append("Server: Apache-Coyote/1.1\r\n");
		buffer.append("Content-Type: text/html;charset=ISO-8859-1\r\n");
		buffer.append("Content-Length: ").append(length).append("\r\n");
		buffer.append("Date: ").append(generateDate()).append("\r\n");
		buffer.append("\r\n");
		return buffer.toString();
	}
	
	private String generateDate()
	{
		Date now = new Date();
		return now.toString();
	}
	
	private String readHtmlFile(String filename) 
	    throws IOException
    {
        File file = new File(webDirectory, filename);
        StringBuilder buffer = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String s;
        while((s = in.readLine()) != null)
        {
            buffer.append(s).append("\n");
        }
        in.close();
        return buffer.toString();
	}
}
