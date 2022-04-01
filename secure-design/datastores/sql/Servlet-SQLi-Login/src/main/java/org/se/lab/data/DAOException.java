package org.se.lab.data;

public class DAOException
    extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /*
     * Constructors
     */
    public DAOException()
    {
        super("DAOException");
    }

    public DAOException(String message)
    {
        super(message);
    }
    
    public DAOException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
