package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/login.html")
public class ControllerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	public ControllerServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		LOG.debug("GET " + request.getQueryString());
		
        response.setContentType("text/html");
        response.setBufferSize(1024);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usergroup = request.getParameter("usergroup");
        String action = request.getParameter("action");
        
        StringBuilder m = new StringBuilder(); 
        m.append("<html>");
        m.append("  <head>");
        m.append("    <title>Login Servlet</title>");
        m.append("  </head>");
        m.append("  <body>");
        m.append("    <h1> Request Parameters: </h1><p>");
        m.append("    <pre>");
        m.append("      username  = \""+ username + "\"<br/>");
        m.append("      password  = \"" + password + "\"<br/>");
        m.append("      usergroup = \"" + usergroup + "\"<br/>");
        m.append("      action    = \"" + action + "\"<br/>");
        m.append("    </pre>");
        m.append("  </body>");
        m.append("</html>");
        
        PrintWriter out = response.getWriter();
        out.println(m.toString());
        out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		LOG.debug("POST " + request.getQueryString());
		doGet(request, response);
	}
}
