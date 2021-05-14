package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
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

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		LOG.debug("GET " + request.getQueryString());

        String username = request.getParameter("username");

        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Login</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h1> Welcome " + username + " ! </h1><p>");
        html.append("<br/>");
        html.append("<a href=\"cookies.html\">Show cookies!</a>");
        html.append("  </body>");
        html.append("</html>");
        
        response.setContentType("text/html");
        response.setBufferSize(1024);

        // Add cookie to the response
        Cookie cookie = new Cookie("id", generateId());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);  // should be true in a real application
        response.addCookie(cookie);
        PrintWriter out = response.getWriter();
        out.println(html);
        out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		LOG.debug("POST " + request.getQueryString());
		doGet(request, response);
	}


    private String generateId()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String hexString = Hex.encodeHexString(bytes);
        return hexString;
    }
}
