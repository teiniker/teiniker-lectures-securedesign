package org.se.lab.presentation;

import org.apache.log4j.Logger;
import org.se.lab.business.Factory;
import org.se.lab.business.UserService;
import org.se.lab.data.User;

import java.util.Date;
import java.util.List;


public class UserBean
{
	private final Logger LOG = Logger.getLogger(UserBean.class);
	private final Factory factory = new Factory();
	
	
	/*
	 * View-Helper Methods
	 */

	public String getUserTable()
	{
		LOG.debug("getUserTable()");
		
		StringBuilder html = new StringBuilder();
		try
		{
			UserService service = factory.createUserService();
			List<User> users = service.findAllUsers();			
			html.append("<table border=\"0\">");
			for (User user : users)
			{
				html.append("    <tr>");
				html.append("        <td width=\"150\">").append(user.getFirstName()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getLastName()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getUsername()).append("</td>");
				html.append("        <td width=\"150\">").append(user.getPassword()).append("</td>");
				html.append("        <td width=\"100\" align=\"center\"><form method = \"post\" action = \"controller\"><input type = \"hidden\" name = \"id\" value = \"");
				html.append(user.getId()).append("\"><input type = \"submit\" name = \"action\" value = \"Delete\" /></form></td>");
				html.append("    </tr>").append("\n");
			}
			html.append("</table>");
		} 
		catch (Exception e)
		{
			// TODO: generate HTML message
			LOG.error("Can't create user HTML table", e);
		}
		return html.toString();
	}

	
	public String getTimeStamp()
	{
		LOG.debug("getTimeStamp()");
		
		Date timeStamp = new Date();
		return timeStamp.toString();
	}
}
