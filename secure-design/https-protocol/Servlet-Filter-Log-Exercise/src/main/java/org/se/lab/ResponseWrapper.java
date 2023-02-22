package org.se.lab;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/*
 * In order to pass a standard-in stream to the servlet, the filter creates 
 * a response wrapper that overrides the "getWriter" method to return this
 * standard-in stream.
 * The wrapper is passed to the "doFilter()" method of the filter chain.
 */

public class ResponseWrapper
	extends HttpServletResponseWrapper
{
	private CharArrayWriter output;
	
	/*
	 * Constructor
	 */
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
