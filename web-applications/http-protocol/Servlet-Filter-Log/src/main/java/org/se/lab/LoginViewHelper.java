package org.se.lab;

public class LoginViewHelper
{

	public String generateLoginParameters(String username, String password, String usergroup)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Simple Login Example</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Request parameters:</h2>");
        html.append("    <pre>");
        html.append("      username  = \"").append(username).append("\"<br/>");
        html.append("      password  = \"").append(password).append("\"<br/>");
        html.append("      usergroup = \"").append(usergroup).append("\"<br/>");
        html.append("    </pre>");
        html.append("  </body>");
        html.append("</html>");

        return html.toString();
	}


	public String generateLoginSuccess(String username, String password, String usergroup)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Simple Login Example</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Welcome, ").append(username).append("! </h2>");
        html.append("  </body>");
        html.append("</html>");

        return html.toString();
	}

	public String generateLoginRejected(String username, String password, String usergroup)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Simple Login Example</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Sorry, either your username or password is wrong... </h2>");
        html.append("  </body>");
        html.append("</html>");

        return html.toString();
	}

}
