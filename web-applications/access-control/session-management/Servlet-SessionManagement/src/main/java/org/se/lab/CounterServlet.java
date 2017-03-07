package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/counter.html")
public class CounterServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private AtomicInteger counter = new AtomicInteger();
	
	public CounterServlet()
	{
		super();
		counter.set(0);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
       
        // Controller 
        
        String action = request.getParameter("action");
        if(action == null)
        {
        	// do nothing
        }
        else if(action.equals("Increment"))
        {
        	counter.incrementAndGet();        	
        }
        else if(action.equals("Reset"))
        {
        	counter.set(0);
        }        
        else if(action.equals("Decrement"))
        {
        	counter.decrementAndGet();
        }
        
        // View
        
        StringBuilder s = new StringBuilder();       
        s.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
        s.append("<html>\n");
        s.append("  <head>\n");
        s.append("  	<title>Session Servlet</title>\n");
        s.append("  </head>\n");
        s.append("  <body>\n");
        s.append("      <h1>Counter = ").append(counter.get()).append(" </h1>\n");
        
        s.append("		<form method=\"POST\" action=\"counter.html\" >");
		s.append("			<table border=\"0\" cellspacing=\"1\" cellpadding=\"5\">");
		s.append("				<colgroup>");
		s.append("					<col width=\"150\"> <col width=\"150\"> <col width=\"150\">");
		s.append("				</colgroup>");
		s.append("				<tr>");
		s.append("					<th>");
		s.append("						<input type=\"submit\"  name=\"action\" value=\"Increment\">");  
		s.append("					</th>");
		s.append("					<th>");
		s.append("						<input type=\"submit\"  name=\"action\" value=\"Reset\">");  
		s.append("					</th>");
		s.append("					<th>");  
		s.append("						<input type=\"submit\" name=\"action\" value=\"Decrement\">");
		s.append("					</th>");
		s.append("				</tr>");	
		s.append("			</table>");
		s.append("		</form>");
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
