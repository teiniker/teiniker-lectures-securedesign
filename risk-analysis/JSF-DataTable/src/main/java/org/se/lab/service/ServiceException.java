package org.se.lab.service;

public class ServiceException
	extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ServiceException(String message)
	{
		super(message);
	}

	public ServiceException(String message, Throwable e)
	{
		super(message, e);
	}
}
