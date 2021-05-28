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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		LOG.debug("GET " + request.getQueryString());
		
        response.setContentType("text/html");
        response.setBufferSize(1024);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usergroup = request.getParameter("usergroup");
        String action = request.getParameter("action");
        
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Login Servlet</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h1> Welcome " + username + " ! </h1><p>");
        html.append("    </pre>");
        html.append("  </body>");
        html.append("</html>");
        
        PrintWriter out = response.getWriter();
        out.println(html);
        out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		LOG.debug("POST " + request.getQueryString());
		doGet(request, response);
	}
}
