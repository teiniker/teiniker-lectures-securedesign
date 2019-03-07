package org.se.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;


public class HttpRequestHandler
{
	private final String WEB_DIR;

	public HttpRequestHandler(String webDirectory)
	{
		if(webDirectory == null)
			throw new IllegalArgumentException();
		this.WEB_DIR = webDirectory;
	}
	

	public void handleRequest(Socket connection)
	{
		Logger.log(connection + " - thread id: " + Thread.currentThread().getId());
        try
        {
            OutputStream out = connection.getOutputStream();
            InputStream in = connection.getInputStream();
            handleRequest(in, out);
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
		StringBuilder buffer = new StringBuilder();
		while (true) {
			int c = in.read();
			if (c == '\r' || c == '\n' || c == -1)
				break;
			buffer.append((char) c);
		}
		Logger.log("request: " + buffer.toString());

		String[] requestElements = buffer.toString().split(" ");
		if (requestElements[0].equals("GET") && requestElements[2].startsWith("HTTP/1"))
		{
			String html = httpGet(requestElements[1]);
			Writer w = new OutputStreamWriter(out);
			w.write(httpHeader(html.length()));
			w.write(html);
			w.flush();
		}
	}

    public String httpGet(String filename)
    {
        File file = new File(WEB_DIR, filename);
        if(file.exists() && file.isFile())
        {
            Logger.log("read: " + file);
            return htmlFile(file);
        }
        else
        {
            Logger.log("file not found: " + file);
            return htmlFile( new File(WEB_DIR, "error404.html"));
        }
    }


    private String htmlFile(File file)
    {
        StringBuilder buffer = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new FileReader(file)))
        {
            String s;
            while ((s = in.readLine()) != null)
            {
                buffer.append(s).append("\n");
            }
        }
        catch(IOException e)
        {
            throw new IllegalStateException("Can't read file: " + file, e);
        }
        return buffer.toString();
    }


    private String httpHeader(int length)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("HTTP/1.1 200 OK\r\n");
        buffer.append("Server: Apache-Coyote/1.1\r\n");
        buffer.append("Content-Type: text/html; charset=utf-8\r\n");
        buffer.append("Content-Length: ").append(length).append("\r\n");
        buffer.append("Date: ").append(timeStamp()).append("\r\n");
        buffer.append("\r\n");
        return buffer.toString();
    }

    private static String timeStamp()
    {
        Date now = new Date();
        return now.toString();
    }
}
