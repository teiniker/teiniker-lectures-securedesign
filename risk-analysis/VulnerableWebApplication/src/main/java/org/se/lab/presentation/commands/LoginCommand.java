package org.se.lab.presentation.commands;

import org.apache.log4j.Logger;
import org.se.lab.business.UserService;

import javax.servlet.ServletException;
import java.io.IOException;


public class LoginCommand
	extends WebCommand
{
    private final static Logger LOG = Logger.getLogger(LoginCommand.class);
    
	@Override
	public void process() throws ServletException, IOException
	{
	    try
		{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			LOG.debug("process(\"" + username + "\")");

			UserService service = factory.createUserService();
			boolean isValid = service.login(username, password);
			if(isValid)
			{
				LOG.debug("Login successful: " + username);
				forward("/table.jsp");
			}
			else
			{
			    LOG.debug("Login failed: " + username + " : " + password);
				req.setAttribute("message", "Login failed for user: " + username);
				forward("/login.jsp");
			}
		}
		catch(Exception e)
		{
		    req.setAttribute("message", "Error: " + e.getMessage());
		    LOG.error("Can't execute Login!", e);
            forward("/login.jsp");
        }
	}
}
