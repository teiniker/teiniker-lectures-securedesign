package org.se.lab;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class HttpClientTest
{
	private final static int PORT = 8080;

	@Test
	public void testPOSTRequest() throws UnknownHostException, IOException
	{
		Socket socket = new Socket("localhost", PORT);
//		socket.setSoTimeout(5000);

		// send request
		PrintWriter out = new PrintWriter(socket.getOutputStream());		
		out.println("GET /index.html HTTP/1.1");
		out.println("Host: localhost:" + PORT);
		out.println();
		out.flush();
		
		// read response
		InputStream in = socket.getInputStream();
		StringBuilder buffer = new StringBuilder();
		int c;
		while((c=in.read()) != -1)
		{
			buffer.append((char)c);
		}
		
		in.close();
		out.close();
		socket.close();

		System.out.println(buffer.toString());
	}
}
