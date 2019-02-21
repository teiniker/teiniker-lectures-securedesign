package org.se.lab.presentation.commands;

import org.apache.log4j.Logger;
import org.se.lab.business.UserService;

import javax.servlet.ServletException;
import java.io.IOException;


public class DeleteCommand
	extends WebCommand
{
	private final Logger LOG = Logger.getLogger(DeleteCommand.class);
	
	@Override
	public void process() throws ServletException, IOException
	{
		LOG.debug("process DELETE command");
		
		try
		{
			String id = req.getParameter("id");
		
			UserService service = factory.createUserService();
			service.removeUser(id);
			req.setAttribute("message", "User with id = " + id + " successfully deleted.");
		}
		catch(Exception e)
		{
		    req.setAttribute("message","Error: " + e.getMessage());
			LOG.error("Can't delete user!", e);
		}
		forward("/table.jsp");
	}
}
