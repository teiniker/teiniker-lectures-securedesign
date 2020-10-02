package org.se.lab;

public class ServiceException
    extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ServiceException(String msg)
    {
        super(msg);
    }
    
    public ServiceException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
