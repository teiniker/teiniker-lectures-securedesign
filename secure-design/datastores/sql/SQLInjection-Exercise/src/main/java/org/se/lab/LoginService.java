package org.se.lab;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class LoginService
{
	private final static Logger LOG = Logger.getLogger(LoginService.class);
	
    public boolean login(Connection connection, String username, String password)
    {
        final String SQL 
        		= "SELECT id FROM User WHERE username ='" 
                + username
                + "' AND password = '" 
                + password + "'";
        LOG.info("SQL> " + SQL);
        try(Statement stmt = connection.createStatement())
        {
            ResultSet rs = stmt.executeQuery(SQL);
            return rs.next();
        } 
        catch (SQLException e)
        {
            return false;
        } 
    }
}
