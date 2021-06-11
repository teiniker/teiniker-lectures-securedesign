package org.se.lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService
{
	/*
	 * A SQL injection vulnerability arises when the original SQL query 
	 * can be altered to form an altogether different query. 
	 * Execution of this altered query may result in information leaks or 
	 * data modification. 
	 */

	/*
     * The primary means of preventing SQL injection are sanitizing and 
     * validating untrusted input and parameterizing queries.
     * 
     * Fortunately, the JDBC library provides an API for building SQL 
     * commands that sanitize untrusted data. 
     * The java.sql.PreparedStatement class properly escapes input strings, 
     * preventing SQL injection when used properly.
     */
    public boolean login(Connection connection, String username, String password)
    {
        final String SQL = "SELECT id FROM User WHERE username=? AND password=?";
        
        try(PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
        catch(SQLException e)
        {
        	return false;
        }
    }
}
