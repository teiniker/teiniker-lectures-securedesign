package org.se.lab.business;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.se.lab.data.UserDAOMySQLImpl;


public class Factory
{
	private final static String DATA_SOURCE_NAME = "java:jboss/datasources/MySqlDS";

	
    public UserService createUserService()
    {
        Connection c = createConnection();
                
        UserDAOMySQLImpl userDAO = new UserDAOMySQLImpl();
        userDAO.setConnection(c);

        UserServiceImpl service = new UserServiceImpl();
        service.setConnection(c);
        service.setUserDAO(userDAO);

        return service;    
    }
    
        
    /*
     * Utility methods
     */
    
    protected Connection createConnection()
    {
        try
        {
			Context initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(DATA_SOURCE_NAME); 
            return ds.getConnection();
        } 
        catch (NamingException e)
        {
            throw new IllegalStateException();
        }
        catch (SQLException e)
        {
            throw new IllegalStateException();
        }
    }
}
