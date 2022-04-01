package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private final Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        
        // Input Validation

        if(action != null && action.equals("Add"))
        {
        		LOG.info("DEBUG Log - Add: " + firstName + "," + lastName + "," + username);

        	// TODO: implement some action
        }
               
        String html = generateUserForm();

        // Generate response 
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(html);
        out.close();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String html = generateUserForm();

		// Generate response
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(html);
		out.close();
	}
	
	
	/*
	 * View Helper
	 */
	
	protected String generateUserForm()
	{
		StringBuilder html = new StringBuilder();
		html.append("<html>\n");
		html.append("    <head>\n");
		html.append("    	<title>Servlet Client-Side Controls</title>\n");
		html.append("    </head>\n");
		
		html.append("    <body>\n");
		html.append("    	<h2>Capture user data:</h2>\n");

		html.append("    		<form method=\"POST\" action=\"controller\">\n");
		html.append("    	    	<table border=\"0\">\n");
		html.append("    	        	<tr>\n");
		html.append("    	        		<th width=\"50\">Id</th>\n");
		html.append("    	            	<th width=\"150\">FirstName</th>\n");
		html.append("    	            	<th width=\"150\">LastName</th>\n");
		html.append("	                	<th width=\"150\">Username</th>\n");
		html.append("    	            	<th width=\"150\">Password</th>\n");   
		html.append("    	            	<th width=\"100\">Actions</th>\n"); 
		html.append("    	        	</tr>\n");
		html.append("    	        	<tr>\n");
		html.append("    	        		<td/>\n");
		html.append("    	            	<td><input type=\"text\" name=\"firstName\" maxlength=\"16\"/></td>\n");
		html.append("    	            	<td><input type=\"text\" name=\"lastName\" maxlength=\"16\"/></td>\n");
		html.append("    	            	<td><input type=\"text\" name=\"username\" pattern=\"[A-Za-z0-9_.]{5,16}\"/></td>\n");
		html.append("    	            	<td><input type=\"password\" name=\"password\" pattern=\"[A-Za-z0-9_!#]{12,}\"/></td>\n");
		html.append("  	<td align=\"center\"><input type=\"submit\" name=\"action\" value=\"Add\" /></td>\n"); 
		html.append("    	        	</tr>\n");
		html.append("    	    	</table>\n");
		html.append("        </form>\n");	
		
		html.append("    </body>\n");
		html.append("</html>\n");
		
		return html.toString();
	}
}
