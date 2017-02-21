package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebFilter("/login.html")
public class ResponseFilter implements Filter
{
	private final Logger LOG = Logger.getLogger(ResponseFilter.class);
	
	/*
	 * Lifecycle methods
	 */
	
	public ResponseFilter()
	{
		LOG.debug("ResponseFilter()");
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
		LOG.debug("ResponseFilter.init()");
	}

	public void destroy()
	{
		LOG.debug("ResponseFilter.destroy()");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		LOG.debug("doFilter()");
		
		if(response instanceof HttpServletResponse)
		{
			ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse)response);
			chain.doFilter(request, wrapper);
			
			String content = wrapper.toString();
			
			LOG.info("---------------------------------------------------");
			LOG.info("Response Filter");
			LOG.info("---------------------------------------------------");
			LOG.info(content);
			
	        PrintWriter out = response.getWriter();
	        out.println(content);
	        out.close();
		}
		else
		{	
			chain.doFilter(request, response);
		}
	}
}
