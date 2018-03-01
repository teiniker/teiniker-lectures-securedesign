package org.se.lab;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerSingleThreaded
{
	private final static String WEB_DIR = "./src/main/webapp";
	private final static int SERVER_PORT = 8080;

	public static void main(String... ags)
	{
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(SERVER_PORT);
			Logger.log("Server started...");
			Logger.log(server.toString());
			
			while (true)
			{
				Socket connection = server.accept(); // wait for a connection

				HttpRequestHandler handler = new HttpRequestHandler(WEB_DIR);
				handler.handleRequest(connection);
				connection.close();
			}
		} 
		catch(IOException e)
		{
			throw new IllegalStateException("Can't handle connection!", e);
		}
		finally
		{
			if(server != null)
			{
				try
				{
					server.close();
				}
				catch (IOException e)
				{
					throw new IllegalStateException("Can't close server socket!", e);
				}
			}
		}
	}
}