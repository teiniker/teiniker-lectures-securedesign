package org.se.lab;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

@WebFilter("/login.html")
public class RequestFilter implements Filter 
{
	private final Logger LOG = Logger.getLogger(RequestFilter.class);
	
	public RequestFilter()
	{
		LOG.debug("RequestFilter()");
	}

	public void init(FilterConfig fConfig) throws ServletException 
	{
		LOG.debug("RequestFilter.init()");
	}

	public void destroy()
	{
		LOG.debug("RequestFilter.destroy()");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException 
	{
		// place your code here
		LOG.info("---------------------------------------------------");
		LOG.info("Request Filter");
		LOG.info("---------------------------------------------------");

		LOG.info("Server:");
		LOG.info("    server name = " + request.getServerName());
		LOG.info("    server port = " + request.getServerPort());
		LOG.info("---------------------------------------------------");

		LOG.info("Client:");
		LOG.info("    remote address = " + request.getRemoteAddr());
		LOG.info("    remote host    = " + request.getRemoteHost());
		LOG.info("    remote port    = " + request.getRemotePort());
		LOG.info("    protocol       = " + request.getProtocol());
		LOG.info("    https          = " + request.isSecure());
        
		LOG.info("---------------------------------------------------");
        
        
		LOG.info("Parameters:");
        Enumeration<String> parameterNames = request.getParameterNames();	
        while(parameterNames.hasMoreElements())
        {
        	String name = parameterNames.nextElement();
        	String value = request.getParameter(name);
        	LOG.info("    " + name + "  = " + value);
        }
     
        LOG.info("---------------------------------------------------\n");
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}
