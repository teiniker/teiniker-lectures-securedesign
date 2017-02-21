package org.se.lab;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HttpServer
{
	private final static String WEB_DIR = "./src/main/webapp";
	private final static int SERVER_PORT = 8080;  
		
	public static void main(String... ags)
	{
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(SERVER_PORT);
			log("HTTP server is running...");
			while (true)
			{
				Socket connection = server.accept(); // wait for a connection
				log("connection: " + connection.toString());
				try
				{					
					handleRequest(connection.getInputStream(), connection.getOutputStream());
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				} 
				finally
				{
					if(connection != null)
						connection.close();
				}
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(server != null)
				try
				{
					server.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
	}

	
	private static void handleRequest(InputStream in, OutputStream out) 
		throws IOException
	{
		// read request's first line
		InputStream input = new BufferedInputStream(in);
		StringBuilder buffer = new StringBuilder();
		while(true)
		{
			int c = input.read();
			if(c == '\r' || c == '\n' || c== -1)
				break;
			buffer.append((char)c);
		}		
		log("request: " + buffer.toString());
		
		String[] requestElements = buffer.toString().split(" ");
		if(requestElements[0].equals("GET") && requestElements[2].startsWith("HTTP/1"))
		{
			String html = httpGet(requestElements[1]);
			Writer w = new OutputStreamWriter(out);
			w.write(httpHeader(html.length()));
			w.write(html);
			w.flush();
		}
		// TODO: HEADER, POST, etc.
	}
	
	
	public static String httpGet(String filename) 
		throws IOException
	{
		File file = new File(WEB_DIR, filename);
		if(file.exists() && file.isFile())
		{
			log("read: " + file);
			return htmlFile(file);
		}
		else
		{
			log("file not found: " + file);
			return htmlFile( new File(WEB_DIR, "error404.html"));  				
		}
	}

	private static String httpHeader(int length)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("HTTP/1.1 200 OK\r\n");
		buffer.append("Server: Apache-Coyote/1.1\r\n");
		buffer.append("Content-Type: text/html;charset=ISO-8859-1\r\n");
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

	
	private static String htmlFile(File file) 
	{	
		try
		{
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
		catch (IOException e)
		{
			throw new IllegalStateException("Text file not loaded", e);
		}
	}

	
	private static void log(String msg)
	{		
		System.out.println("[" + timeStamp() + "] " + msg);
	}
}
