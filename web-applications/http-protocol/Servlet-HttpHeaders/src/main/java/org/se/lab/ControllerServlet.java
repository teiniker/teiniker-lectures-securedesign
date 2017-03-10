package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/controller")
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
		
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Login Servlet</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h1> Request Headers: </h1><p>");
        html.append("    <pre>");
        
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) 
        {
          String headerName = (String)headerNames.nextElement();
          html.append("      ").append(headerName).append(": ").append(request.getHeader(headerName)).append("<br/>");
        }
        
        html.append("    </pre>");
        html.append("  </body>");
        html.append("</html>");
        
        response.setContentType("text/html");
        response.setBufferSize(1024);                
        response.setHeader("Refresh", "5");
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
