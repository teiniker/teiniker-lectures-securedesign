package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String action = request.getParameter("action");        
        
        String html = null;
        if(action != null && action.equals("Change Password"))
        {
        	if(password1.equals(password2))
        	{
        		html = changedPage();        		
        	}
        	else
        	{
        		html = errorPage();
        	}
        }
        else
        {
        	html = ""; 
        }
               
        // Generate response
        response.setContentType("text/html");
        response.setBufferSize(1024);
        PrintWriter out = response.getWriter();
        out.println(html.toString());
        out.close();
	}

	
	public String changedPage()
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Change Password</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Password has been changed!</h2>");
        html.append("  </body>");
        html.append("</html>");

        return html.toString();
	}


	public String errorPage()
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Change Password</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Entered passwords do not match!</h2>");
        html.append("  </body>");
        html.append("</html>");

        return html.toString();
	}

}
