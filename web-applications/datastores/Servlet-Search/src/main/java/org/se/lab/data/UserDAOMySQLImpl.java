package org.se.lab.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOMySQLImpl 
    extends AbstractDAOImpl
    implements UserDAO
{
	/*
	 * DAO operations
	 */

    public User findById(String id) 
	{
		final String SQL = "SELECT * FROM user WHERE id=" + id;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try
		{
			pstmt = getConnection().prepareStatement(SQL);
			rs = pstmt.executeQuery();
			rs.next();
			user = new User(rs.getLong("id"), rs.getString("firstName"), 
				rs.getString("lastName"), rs.getString("username"), 
				rs.getString("password"));
		}
        catch(SQLException e)
        {
            throw new DAOException("update failure");
        }       
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
        }
		return user;
	}

	
    public List<User> findByUsername(String name) 
	{
		final String SQL = "SELECT * FROM user WHERE lastname = '" + name + "'";
		
		Statement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();

		try
		{
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(SQL);
			System.out.println("sql> " + SQL);
			
			while (rs.next())
			{
				long id = rs.getLong("id");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String username = rs.getString("username");
				String password = rs.getString("password");
				User user = new User(id, firstName, lastName, username, password);
				users.add(user);
			}
		}
        catch(SQLException e)
        {
            throw new DAOException("find by lastname: " + e.getMessage(), e);
        }       
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
		return users;
	}
	
	
    public List<User> findAll() 
	{
		final String SQL = "SELECT * FROM user";
		Statement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		
		try
		{
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next())
			{
				long id = rs.getLong("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String username = rs.getString("username");
				String password = rs.getString("password");
				User user = new User(id, firstName, lastName, username, password);
				users.add(user);
			}
		}		
        catch(SQLException e)
        {
            throw new DAOException("find all failure", e);
        }       
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
		return users;
	}
}
