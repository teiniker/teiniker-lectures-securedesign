package org.se.lab;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;

@WebServlet("/cookies.html")
public class CookieServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(CookieServlet.class);

	public CookieServlet()
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
        html.append("    <title>Servlet Cookies</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2> Cookies: </h2><p>");
        html.append("    <pre>");
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for(Cookie c : cookies)
            {
                html.append("      ").append(c.getName()).append(": ").append(c.getValue()).append("<br/>");
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        html.append("    </pre>");
        html.append("  </body>");
        html.append("</html>");

        response.setContentType("text/html");
        response.setBufferSize(1024);
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
