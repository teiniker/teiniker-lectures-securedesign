package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request parameters
        String role = request.getParameter("role");
		String username = request.getParameter("username");
                
        String html = generateUserPage(username, role);
        
        // Add cookie to the response
        Cookie cookie = new Cookie("id",Base64.encodeBase64String("1234567890".getBytes("UTF-8")));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        
        // Generate response 
        response.setContentType("text/html");
        response.setBufferSize(1024);
        PrintWriter out = response.getWriter();
        out.println(html);
        out.close();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	
	/*
	 * View Helper
	 */
	
	protected String generateUserPage(String username, String role)
	{
		StringBuilder html = new StringBuilder();
		html.append("<html>\n");
		html.append("    <head>\n");
		html.append("    	<title>Cross-Site Scripting</title>\n");
		html.append("    </head>\n");		
		html.append("    <body>\n");
		
		html.append("    	<h2>Created user: ");
		html.append(HTMLEncoder.encodeForHTML(username)).append(" as ");
		html.append(HTMLEncoder.encodeForHTML(role));
		html.append("</h2>\n");
		
		html.append("<br>");
		html.append("<a href=\"index.html\">back</a>");
		
		html.append("    </body>\n");
		html.append("</html>\n");
		
		return html.toString();
	}
}
