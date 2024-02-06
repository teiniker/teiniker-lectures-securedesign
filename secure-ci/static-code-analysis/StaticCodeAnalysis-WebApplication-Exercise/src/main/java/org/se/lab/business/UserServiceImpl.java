package org.se.lab.business;

import org.apache.commons.codec.binary.Base64;
import org.se.lab.data.DAOException;
import org.se.lab.data.UserDAO;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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


    public boolean login(String username, String password)
    {
		boolean isValid = false;
		try
		{
			begin();
			isValid = getUserDAO().isValidUser(username, hashValue(password));
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

	private String hashValue(String message)
	{
		MessageDigest algorithm;
		try
		{
			algorithm = MessageDigest.getInstance("SHA-1");
			algorithm.update(message.getBytes("UTF-8"));
			byte[] bytes = algorithm.digest();

			return Base64.encodeBase64String(bytes);
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
		{
			throw new IllegalStateException("Unable to calculate a hash value!",e);
		}
	}
}
