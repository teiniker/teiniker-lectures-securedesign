package org.se.lab;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/logout")
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(ControllerServlet.class); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		LOG.debug("doGet: " + request.getQueryString());
		
		response.setContentType("text/html");
        response.setBufferSize(1024);
      
        HttpSession session = request.getSession(false);
        
        LOG.info("> logout");
        session.invalidate();
        forward("/public/index.html", request, response);
	}

	 protected void forward(String page, HttpServletRequest request, HttpServletResponse response)
             throws ServletException,IOException
     {
		 LOG.debug("forward to: " + page);
         RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
         dispatcher.forward(request, response);
     }
}
