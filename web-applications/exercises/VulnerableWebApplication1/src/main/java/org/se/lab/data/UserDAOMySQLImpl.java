package org.se.lab.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class UserDAOMySQLImpl 
    extends AbstractDAOImpl
    implements UserDAO
{
    private final static Logger LOG = Logger.getLogger(UserDAOMySQLImpl.class);
    
	/*
	 * DAO operations
	 */

    public void insert(final User user) 
	{
        LOG.debug("insert(" + user + ")"); 
        
		if(user == null)
			throw new IllegalArgumentException("Invalid User!");
		    		
		final String SQL = "INSERT INTO user VALUES (NULL,?,?,?,?)";
		LOG.debug("SQL: " + SQL);
		PreparedStatement pstmt = null;		
		try
		{		
			pstmt = getConnection().prepareStatement(SQL);
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
		    throw new DAOException("Insert failure", e);
		}
		finally
		{
			closePreparedStatement(pstmt);
		}
	}

	
    public void update(final User user)
	{    	
        LOG.debug("update(" + user + ")"); 
        
		if(user == null)
		    throw new IllegalArgumentException("Invalid User!");

		final String SQL = "UPDATE user SET firstname=?, lastname=?, " +
				"username=?, password=? WHERE id=?";
		LOG.debug("SQL: " + SQL);
		PreparedStatement pstmt = null;		
		try
		{
			pstmt = getConnection().prepareStatement(SQL);
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			pstmt.setLong(5, user.getId());
			pstmt.executeUpdate();
		}
        catch(SQLException e)
        {
            throw new DAOException("Update failure", e);
        }		
		finally
		{
			closePreparedStatement(pstmt);
		}
	}


	
    public void delete(final long id) 
	{
        LOG.debug("delete(" + id + ")"); 
        
		final String SQL = "DELETE FROM user WHERE ID = ?";
		LOG.debug("SQL: " + SQL);
		PreparedStatement pstmt = null;
		try
		{
			pstmt = getConnection().prepareStatement(SQL);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		}
        catch(SQLException e)
        {
            throw new DAOException("Delete failure", e);
        }       
        finally
        {
            closePreparedStatement(pstmt);
        }
	}

	
    public User findById(final long id) 
	{
        LOG.debug("findById(" + id + ")"); 
        
		final String SQL = "SELECT * FROM user WHERE id=? ";
		LOG.debug("SQL: " + SQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try
		{
			pstmt = getConnection().prepareStatement(SQL);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			user = new User(rs.getLong("id"), rs.getString("firstName"), 
				rs.getString("lastName"), rs.getString("username"), 
				rs.getString("password"));
		}
        catch(SQLException e)
        {
            throw new DAOException("Find by Id failure", e);
        }       
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
        }
		return user;
	}

	
    public List<User> findByLastname(final String name) 
	{
        LOG.debug("findByLastname(\"" + name + "\")"); 
        
		final String SQL = "SELECT * FROM user WHERE lastname like ? ";
		LOG.debug("SQL: " + SQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();

		try
		{
			pstmt = getConnection().prepareStatement(SQL);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
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
            throw new DAOException("Find by lastname failure", e);
        }       
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
        }
		return users;
	}
	
    
    public User findByUsername(final String name) 
    {
        LOG.debug("findByUsername(\"" + name + "\")"); 
        
        final String SQL = "SELECT * FROM user WHERE username=?";
        LOG.debug("SQL: " + SQL);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try
        {
            pstmt = getConnection().prepareStatement(SQL);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                long id = rs.getLong("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(id, firstName, lastName, username, password);
            }
        }
        catch(SQLException e)
        {
            throw new DAOException("Find by lastname failure", e);
        }       
        finally
        {
            closeResultSet(rs);
            closePreparedStatement(pstmt);
        }
        return user;
    }

    
    public List<User> findAll() 
	{
        LOG.debug("findAll()");
        
		final String SQL = "SELECT * FROM user";
		LOG.debug("SQL: " + SQL);
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
            throw new DAOException("Find all failure", e);
        }       
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
		return users;
	}
}
