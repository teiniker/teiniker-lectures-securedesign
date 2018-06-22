package org.se.lab.presentation.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.owasp.encoder.Encode;
import org.se.lab.business.UserService;
import org.se.lab.util.Validator;


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

			Validator.checkUsername(username);
			Validator.checkPassword(password);

			UserService service = factory.createUserService();
			boolean isValid = service.login(username, password);
			if(isValid)
			{
			    String value = Encode.forHtmlContent(username);
				req.setAttribute("message", "Login successful, welcome " + value + ":-)");
				LOG.debug("Login successful: " + value);
			}
			else
			{
			    LOG.warn("Login failed: " + username + " : " + password);
				req.setAttribute("message", "Login failed!");
			}
		}
		catch(Exception e)
		{
		    String value = Encode.forHtmlContent(e.getMessage());
		    req.setAttribute("message", "Error: " + value);
		    LOG.error("Can't execute Login!", e);
		}
		forward("/login.jsp");			
	}
}
