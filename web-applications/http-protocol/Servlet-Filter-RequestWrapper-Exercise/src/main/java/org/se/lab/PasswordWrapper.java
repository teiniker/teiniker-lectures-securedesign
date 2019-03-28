package org.se.lab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordWrapper
	extends HttpServletRequestWrapper
{
	public PasswordWrapper(HttpServletRequest request)
	{
		super(request);
	}

	/*
	 * Property: hashValue
	 */
	private String hashValue;
	public String getHashValue()
	{
		return hashValue;
	}
	public void setHashValue(String hashValue)
	{
		if(hashValue == null)
			throw new IllegalArgumentException("Parameter hash value is null");
		this.hashValue = hashValue;
	}


	@Override
	public String getParameter(String name)
	{
		if("password".equals(name))
		{
			return getHashValue();
		}
		else
		{
			return getRequest().getParameter(name);
		}
	}
}
