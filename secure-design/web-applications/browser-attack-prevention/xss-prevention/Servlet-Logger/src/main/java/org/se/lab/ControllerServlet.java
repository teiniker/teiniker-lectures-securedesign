package org.se.lab;

import java.io.IOException;
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
	private final static Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	public ControllerServlet()
	{
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request parameters
        LOG.info("Parameters from " + request.getRemoteAddr());
        Enumeration<String> parameterNames = request.getParameterNames();	
        while(parameterNames.hasMoreElements())
        {
        	String name = parameterNames.nextElement();
        	String value = request.getParameter(name);
        	LOG.info("    " + name + "  = " + value);
        }               

		// Generate response 
        response.setContentType("text/html");
        response.setBufferSize(1024);
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	
}
