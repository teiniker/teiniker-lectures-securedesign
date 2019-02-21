package org.se.lab.presentation.commands;

import org.apache.log4j.Logger;
import org.se.lab.business.UserService;

import javax.servlet.ServletException;
import java.io.IOException;


public class AddCommand
	extends WebCommand
{
	private final Logger LOG = Logger.getLogger(AddCommand.class);
	
	@Override
	public void process() throws ServletException, IOException
	{
		LOG.debug("process ADD command");

		try
		{
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String username = req.getParameter("username");
			String password = req.getParameter("password");

			UserService service = factory.createUserService();
			service.addUser(firstName, lastName, username, password);
			req.setAttribute("message", "User '" + username + "' successfully added.");
		}
		catch(Exception e)
		{
		    req.setAttribute("message", "Error: " + e.getMessage());
			LOG.error("Can't add user!", e);
		}
		forward("/table.jsp");
	}
}
