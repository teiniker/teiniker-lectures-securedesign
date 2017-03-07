package org.se.lab.business;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService
{
	/*
	 * Dependency: ---[1]-> connection:Connection
	 */
	protected Connection connection;
	protected Connection getConnection()
	{
		return connection;
	}
	public void setConnection(Connection connection)
	{
		if(connection == null)
			throw new NullPointerException("connection");
		this.connection = connection;
	}
	
    protected void closeConnection()
    {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e)
        {
        }
    }

	
	
	/*
	 * Transaction methods
	 */
	
	protected void begin() 
	    throws ServiceException
	{
		try
        {
            connection.setAutoCommit(false);
        } 
		catch (SQLException e)
        {
		    throw new ServiceException("transaction begin failure");
        } 
	}

	protected void commit() 
	    throws ServiceException	    
	{
		try
        {
            connection.commit();
            connection.setAutoCommit(true);
        } 
		catch (SQLException e)
        {
		    throw new ServiceException("transaction commit failure");
        }
	}

	protected void rollback() 
	    throws ServiceException
	{
		try
        {
            connection.rollback();
        } 
		catch (SQLException e)
        {
            throw new ServiceException("transaction rollback failure");
        }
	}
}