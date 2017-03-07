package org.se.lab.ui;

import java.util.List;

import org.se.lab.data.User;

public class SearchViewHelper
{

	public String generateSearchResults(String name, List<User> results)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>\n");
        html.append("  <head>\n");
        html.append("    <title>Servlet Simple Search</title>\n");
        html.append("  </head>\n");
        html.append("  <body>\n");
        html.append("    <h2>Results for ").append(name).append(":</h2>\n");

        for(User u : results)
        {
        	html.append("<p>").append(u).append("</p>\n");
        }
        html.append("  </body>\n");
        html.append("</html>\n");

        return html.toString();
	}

}
