package org.se.lab.presentation.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.se.lab.business.UserService;


public class LoginCommand
	extends WebCommand
{
	@Override
	public void process() throws ServletException, IOException
	{
		try
		{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			UserService service = factory.createUserService();
			boolean isValid = service.login(username, password);
			if(isValid)
			{
				req.setAttribute("message", "Login successful, welcome " + username + ":-)");
			}
			else
			{
				req.setAttribute("message", "Login failed!");
			}
		}
		catch(Exception e)
		{
		    req.setAttribute("message", "Error: " + e.getMessage());
		    e.printStackTrace();
		}
		forward("/login.jsp");			
	}
}
