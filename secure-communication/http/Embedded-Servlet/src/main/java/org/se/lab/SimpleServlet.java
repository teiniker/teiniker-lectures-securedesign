package org.se.lab;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SimpleServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public SimpleServlet()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Simple Servlet</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h1> This is a simple Servlet...</h1><p>");
        html.append("  </body>");
        html.append("</html>");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(html);
        out.flush();
        out.close();
    }
}