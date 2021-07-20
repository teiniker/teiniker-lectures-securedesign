package org.se.lab.business;

import java.util.List;

import org.se.lab.data.DAOException;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;


public class UserServiceImpl 
	extends AbstractService 
	implements UserService
{
	/*
	 * Dependency: ---[1]-> UserDAO 
	 */
	private UserDAO userDAO;
	protected final UserDAO getUserDAO()
	{
		return userDAO;
	}
	public final void setUserDAO(final UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	
	/*
	 * Business methods
	 */
	
    public void addUser(final String firstName, final String lastName, 
			final String username, final String password) 
	{
		try
		{
			User newUser = new User(firstName,lastName, username, password);
			
			begin();
			getUserDAO().insert(newUser);				
			commit();			
		}
		catch(DAOException e)
		{
			rollback();
			throw new ServiceException("Can't add user!");
		}
		finally
		{
			closeConnection();
		}	
	}
	

    public void removeUser(final String idString) 
	{
		try
		{
			long id = Long.valueOf(idString);
			
			begin();
			getUserDAO().delete(id);
			commit();
		}
        catch(DAOException e)
        {
            rollback();
			throw new ServiceException("Can't remove user!");
        }
        finally
        {
            closeConnection();
        }
	}
	
	
    public List<User> findUsers(final String username)
	{
		List<User> users = null;
		try
		{
			begin();
			users = getUserDAO().findByLastname(username);
			commit();
		}
        catch(DAOException e)
        {
            rollback();
			throw new ServiceException("Can't find users!");
        }
        finally
        {
            closeConnection();
        }
		return users;
	}


    public List<User> findAllUsers()
	{
		List<User> users = null;
		try
		{
			begin();
			users = getUserDAO().findAll();
			commit();
		}
        catch(DAOException e)
        {
            rollback();
			throw new ServiceException("Can't find all users!", e);
        }
        finally
        {
            closeConnection();
        }
		return users;
	}
    
    
    public boolean login(String username, String password)
    {
		boolean isValid = false;
		try
		{
			begin();
			isValid = getUserDAO().isValidUser(username, password);
			commit();
		}
        catch(DAOException e)
        {
            rollback();
			throw new ServiceException("Can't validate user!");
        }
        finally
        {
            closeConnection();
        }
		return isValid;    	
    }
}
