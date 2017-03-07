package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login.html")
public class LoginServlet 
    extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
    {
    	doPost(request, response);
    }
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        response.setContentType("text/html");
        response.setBufferSize(1024);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        
        if(action.equals("Login"))
        {
        	handleLogin(response.getWriter(), username, password);
        }
        else
        {
        	sendPage(response.getWriter(), "Unknown action!");
        }
    }
    
    
    protected void handleLogin(PrintWriter out, String username, String password)
    {
    	UserService servcie = new UserService();
    	switch(servcie.login(username, password))
    	{
    		case UNKNOWN_USER:
    			sendPage(out, "Unknown user " + username);
    			break;
    			
    		case LOCKED_USER:
    			sendPage(out, "User " + username + " is locked!");
    			break;
    			
    		case LOGIN_FAILED:
    			sendPage(out, "Login for user " + username + " failed!");    		    			
    			break;
    			
    		case LOGIN_SUCCESSFUL:
    			sendPage(out, "Welcome, " + username + " !");
    			break;	
    	}
    	out.close();
    }
    
    
    protected void sendPage(PrintWriter out, String msg)
    {
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Login</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h1>").append(msg).append("</h1>");
        html.append("  </body>");
        html.append("</html>");
        
    	out.println(html.toString());
    	out.close();
    }    
}
