package org.se.lab;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

@WebFilter("/controller")
public class EncryptionFilter implements Filter
{
	private final Logger LOG = Logger.getLogger(EncryptionFilter.class);

	public EncryptionFilter()
	{
		LOG.debug("EncryptionFilter()");
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
		LOG.debug("EncryptionFilter.init()");
	}

	public void destroy()
	{
		LOG.debug("EncryptionFilter.destroy()");
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		// place your code here
		LOG.info("EncryptionFilter.doFilter()");

		String password = request.getParameter("password");
		PasswordWrapper wrapper = new PasswordWrapper((HttpServletRequest)request);
		if (password != null)
		{			
			wrapper.setHashValue(hashValue(password));
		}

		// pass the request along the filter chain
		chain.doFilter(wrapper, response);
	}

	
	private String hashValue(String message)
	{
		MessageDigest algorithm;
		try
		{
			algorithm = MessageDigest.getInstance("SHA-256");
			algorithm.update(message.getBytes("UTF-8"));
			byte[] bytes = algorithm.digest();
			
			return Base64.encodeBase64String(bytes);
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
		{
			throw new IllegalStateException("Unable to calculate a hash value!",e);
		}
	}
}
