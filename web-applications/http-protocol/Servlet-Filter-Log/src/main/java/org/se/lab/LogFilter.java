package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/*  
 * The Common Logfile Format
 * 
 * The common logfile format is as follows:
 * 	remotehost 	rfc931 authuser [date] "request" status bytes
 * 	remotehost 	Remote hostname (or IP number if DNS hostname is not available, or if DNSLookup is Off. 
 *  rfc931 		The remote logname of the user. 
 *  authuser	The username as which the user has authenticated himself. 
 *  [date]		Date and time of the request. 
 *  "request"	The request line exactly as it came from the client. 
 *  status		The HTTP status code returned to the client. 
 *  bytes		The content-length of the document transferred. 
 */

@WebFilter("/*")
public class LogFilter implements Filter 
{
	private final Logger LOG = Logger.getLogger(LogFilter.class);
	
	public void init(FilterConfig fConfig) throws ServletException 
	{
		LOG.debug("init()");
	}

	public void destroy()
	{
		LOG.debug("destroy()");
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException 
	{
		LOG.debug("doFilter()");
		
		HttpServletRequest in = (HttpServletRequest)request;
		
		PrintWriter writer = response.getWriter();
		
		Date now = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss Z");
		
		StringBuilder log = new StringBuilder();
		log.append(in.getRemoteHost());
		log.append(" -"); // username
		log.append(" - "); // auth-username
		log.append(formater.format(now));
		log.append(" \"").append(in.getMethod()).append(" ");
		log.append(in.getRequestURI());
		
		String query = in.getQueryString();
		if(query != null)
		{		
			log.append("?").append(query);
		}
		log.append(" ").append(in.getProtocol()).append("\" ");
		       
		// pass the request along the filter chain
		ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse)response);
		chain.doFilter(request, wrapper);

		HttpServletResponse out = (HttpServletResponse)response;
		log.append(out.getStatus()).append(" ");
		log.append(wrapper.toString().length());		

		LOG.info(log.toString());
		
        writer.println(wrapper.toString());
        writer.close();
	}
}
