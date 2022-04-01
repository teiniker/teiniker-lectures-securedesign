package org.se.lab;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ResponseWrapper
	extends HttpServletResponseWrapper
{
	private CharArrayWriter output;
	
	public ResponseWrapper(HttpServletResponse response)
	{
		super(response);
		output = new CharArrayWriter();
	}

	@Override
	public PrintWriter getWriter()
	{
		return new PrintWriter(output);
	}

	@Override
	public String toString()
	{
		return output.toString();
	}
}
