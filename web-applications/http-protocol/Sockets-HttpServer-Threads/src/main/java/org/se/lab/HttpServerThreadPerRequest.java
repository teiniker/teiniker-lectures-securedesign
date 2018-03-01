package org.se.lab;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerThreadPerRequest
{	
	public static void main(String... ags)
	{
		ServerSocket server = null;
		try
		{
			server = new ServerSocket(8080);
			Logger.log("Server started...");
			Logger.log(server.toString());

			while (true)
			{
				final Socket connection = server.accept(); // wait for a connection

				Runnable task = new Runnable()
				{
					public void run()
					{						
						HttpRequestHandler handler = new HttpRequestHandler("src/main/webapp");
						handler.handleRequest(connection);						
					}
				};
				Thread t = new Thread(task);
				t.start();
			}
		} 
		catch(IOException e)
		{
			e.printStackTrace();
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
					e.printStackTrace();
				}
			}	
		}
	}
}