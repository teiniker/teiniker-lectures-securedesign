package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		response.setContentType("text/html");
        response.setBufferSize(1024);
        
        StringBuilder m = new StringBuilder(); 
        m.append("<html>");
        m.append("  <head>");
        m.append("    <title>Controller Servlet</title>");
        m.append("  </head>");
        m.append("  <body>");
        m.append("    <h1> Request Parameters: </h1><p>");
        m.append("    <pre>");
        
        Enumeration<String> parameterNames = request.getParameterNames();	
        while(parameterNames.hasMoreElements())
        {
        	String name = parameterNames.nextElement();
        	String value = request.getParameter(name);
        	m.append("         ").append(name).append("  = ").append(value).append("<br/>");
        }
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
		doGet(request, response);
	}
}
