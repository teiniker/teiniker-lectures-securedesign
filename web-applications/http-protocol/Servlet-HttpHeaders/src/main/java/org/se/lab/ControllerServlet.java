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
		
        StringBuilder m = new StringBuilder(); 
        m.append("<html>");
        m.append("  <head>");
        m.append("    <title>Login Servlet</title>");
        m.append("  </head>");
        m.append("  <body>");
        m.append("    <h1> Request Headers: </h1><p>");
        m.append("    <pre>");
        
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) 
        {
          String headerName = (String)headerNames.nextElement();
          m.append("      ").append(headerName).append(": ").append(request.getHeader(headerName)).append("<br/>");
        }
        
        m.append("    </pre>");
        m.append("  </body>");
        m.append("</html>");
        
        response.setContentType("text/html");
        response.setBufferSize(1024);                
        response.setHeader("Refresh", "5");
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
