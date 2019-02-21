package org.se.lab.business;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.se.lab.data.DAOException;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;
import org.se.lab.data.UserDAOMySQLImpl;


public class UserServiceImpl 
	extends AbstractService 
	implements UserService
{
    private final static Logger LOG = Logger.getLogger(UserDAOMySQLImpl.class);
    
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
        LOG.debug("addUser(\"" + firstName + "\",\"" + lastName + "\",\"" + username + "\"");
		try
		{
            String encodedPassword = Base64.encodeBase64String(password.getBytes("UTF-8"));
			User newUser = new User(firstName,lastName, username, encodedPassword);
			
			begin();
			getUserDAO().insert(newUser);				
			commit();			
		}
		catch(DAOException | UnsupportedEncodingException e)
		{
			rollback();
			LOG.error("Can't add user!", e);
			throw new ServiceException("Can't add user!");
		}
		finally
		{
			closeConnection();
		}	
	}
	

    public void removeUser(final String idString) 
	{
        LOG.debug("removeUser(\"" + idString + "\"");
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
            LOG.error("Can't remove user!", e);
			throw new ServiceException("Can't remove user!");
        }
        finally
        {
            closeConnection();
        }
	}
	
	
    public List<User> findUsers(final String username)
	{
        LOG.debug("findUsers(\"" + username + "\"");
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
            LOG.error("Can't find users!", e);
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
        LOG.debug("findAllUsers()");
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
            LOG.error("Can't find all users!", e);
			throw new ServiceException("Can't find all users!");
        }
        finally
        {
            closeConnection();
        }
		return users;
	}
    
    
    public boolean login(String username, String password)
    {
        LOG.debug("login(\"" + username + "\")");
        boolean isValid = false;
		try
		{
			begin();
			String encodedPassword = Base64.encodeBase64String(password.getBytes("UTF-8"));
			isValid=userDAO.isValidUser(username, encodedPassword);
			commit();
		}
        catch(DAOException | UnsupportedEncodingException e)
        {
            rollback();
            LOG.error("Can't validate user!", e);
			throw new ServiceException("Can't validate user!");
        }
        finally
        {
            closeConnection();
        }
		return isValid;    	
    }
}
