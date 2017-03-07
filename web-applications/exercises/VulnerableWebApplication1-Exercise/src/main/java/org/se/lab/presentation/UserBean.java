package org.se.lab.presentation;

import java.util.Date;
import java.util.List;

import org.se.lab.business.Factory;
import org.se.lab.business.UserService;
import org.se.lab.data.User;


public class UserBean
{
	private final Factory factory = new Factory();
		
	/*
	 * View-Helper Methods
	 */

	public String getUserTable()
	{
		StringBuilder html = new StringBuilder();
		try
		{
			UserService service = factory.createUserService();
			List<User> users = service.findAllUsers();
			
			for (User user : users)
			{
				html.append("<tr>");
				
				html.append("<td width=\"150\">").append(user.getFirstName()).append("</td>");
				html.append("<td width=\"150\">").append(user.getLastName()).append("</td>");
				html.append("<td width=\"150\">").append(user.getUsername()).append("</td>");
				html.append("<td width=\"150\">").append(user.getPassword()).append("</td>");				
				
				html.append("<td width=\"100\" align=\"center\"><form method = \"GET\" action = \"controller\"><input type = \"hidden\" name = \"id\" value = \"");
				html.append(user.getId()).append("\"><input type = \"submit\" name = \"action\" value = \"Delete\" /></form></td>");
				html.append("</tr>").append("\n");
			}
		} 
		catch (Exception e)
		{
			// TODO: generate message
		}
		return html.toString();
	}

	
	public String getTimeStamp()
	{
		Date timeStamp = new Date();
		return timeStamp.toString();
	}
}
