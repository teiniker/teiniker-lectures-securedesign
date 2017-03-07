package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/session.html")
public class SessionServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public SessionServlet()
	{
		super();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
        // Controller 

        HttpSession session = request.getSession(false);
        Integer counter = new Integer(0);
        
        String action = request.getParameter("action");
        if(action == null)
        {
        	// do nothing
        }
        else if(action.equals("Login"))
        {
        	session = request.getSession();
        	session.setMaxInactiveInterval(1*60);  // set timeout 
        	counter = new Integer(0);
        	session.setAttribute("counter", counter);		
        }
        else if(action.equals("Logout"))
        {
        	session.invalidate();
        }
        else if(action.equals("Increment"))
        {        	
        	// getAttribute()
        	// Returns the object bound with the specified name in this 
        	// session, or null if no object is bound under the name.
            if(session != null)
            {
            	counter = (Integer)session.getAttribute("counter");
	            if(counter != null)
	            {
	            	counter = new Integer(counter.intValue() + 1);
	            	session.setAttribute("counter", counter);            	
	            }
            }
        }
        else if(action.equals("Reset"))
        {
        	if(session != null)
        	{
        		counter = new Integer(0);
        		session.setAttribute("counter", counter);
        	}
        }        
        else if(action.equals("Decrement"))
        {
        	if(session != null)
        	{
        		counter = (Integer)session.getAttribute("counter");
        		if(counter != null)
        		{
        			counter = new Integer(counter.intValue() - 1);
        			session.setAttribute("counter", counter);
        		}
        	}
        }

        
        // View
        
        StringBuilder s = new StringBuilder();       
        s.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
        s.append("<html>\n");
        s.append("  <head>\n");
        s.append("  	<title>Session Servlet</title>\n");
        s.append("  </head>\n");
        s.append("  <body>\n");
        s.append("      <h1>Counter = ").append(counter).append(" </h1>\n");
        
        s.append("<hr/>");
        
        s.append("		<form method=\"POST\" action=\"session.html\" >");
		s.append("			<table border=\"0\" cellspacing=\"1\" cellpadding=\"5\">");
		s.append("				<colgroup>");
		s.append("					<col width=\"150\"> <col width=\"150\"> <col width=\"150\">");
		s.append("				</colgroup>");
		s.append("				<tr>");
		s.append("					<th>");
		s.append("						<input type=\"submit\"  name=\"action\" value=\"Login\">");  
		s.append("					</th>");
		s.append("					<th>");
		s.append("					</th>");
		s.append("					<th>");  
		s.append("						<input type=\"submit\" name=\"action\" value=\"Logout\">");
		s.append("					</th>");
		s.append("				</tr>");	
		
		s.append("				<tr>");
		s.append("					<th>");
		s.append("						<input type=\"submit\"  name=\"action\" value=\"Decrement\">");  
		s.append("					</th>");
		s.append("					<th>");
		s.append("						<input type=\"submit\"  name=\"action\" value=\"Reset\">");  
		s.append("					</th>");
		s.append("					<th>");  
		s.append("						<input type=\"submit\" name=\"action\" value=\"Increment\">");
		s.append("					</th>");
		s.append("				</tr>");	
		s.append("			</table>");
		s.append("		</form>");
		
		if(request.isRequestedSessionIdValid())
		{
			// getId()
			// Returns a string containing the unique identifier assigned to this session.
			s.append("<hr/>");
			s.append("Session ID = " + session.getId() + "<br/>");
			s.append("isRequestedSessionIdFromCookie = ").append(request.isRequestedSessionIdFromCookie()).append("<br/>");
			s.append("isRequestedSessionIdFromURL    = ").append(request.isRequestedSessionIdFromURL()).append("<br/>");
			// getAttributeNames()
	        // Returns an Enumeration of String objects containing the 
			// names of all the objects bound to this session.
			Enumeration<String> e = session.getAttributeNames();
			while(e.hasMoreElements())
			{
				String name = e.nextElement();
				s.append(name).append(" = ").append(session.getAttribute(name)).append("<br/>");
			}
		}
        s.append("	</body>\n");
        s.append("</html>\n");
       
        out.println(s.toString());
        out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
