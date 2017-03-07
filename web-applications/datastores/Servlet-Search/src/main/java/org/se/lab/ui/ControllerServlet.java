package org.se.lab.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAOMySQLImpl;
import org.se.lab.service.SearchService;
import org.se.lab.service.SearchServiceImpl;


public class ControllerServlet extends HttpServlet
{
	private final static String DATA_SOURCE_NAME = "java:jboss/datasources/MySqlDS";
	private final Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request
        String name = request.getParameter("lastname");
        String action = request.getParameter("action");        
        LOG.info("request: " + action + "," + name );
        
        String html = null;
        if(action != null && action.equals("Search"))
        {
        	html = handleSearch(name);
        }
        else
        {
        	html = ""; // TODO: generate error page
        }
               
        // Generate response
        response.setContentType("text/html");
        response.setBufferSize(1024);
        PrintWriter out = response.getWriter();
        out.println(html.toString());
        out.close();
	}

	
	/*
	 * Action handlers
	 */
	
	protected String handleSearch(String name)
	{   
		try
		{
			UserDAOMySQLImpl dao = new UserDAOMySQLImpl();
			Connection c = createConnection();
			dao.setConnection(c);
			SearchService service = new SearchServiceImpl(dao);
			SearchViewHelper helper = new SearchViewHelper();
			
			List<User> list = service.search(name);
			String html = helper.generateSearchResults(name, list);
		
			c.close();
			return html;
		}
		catch (SQLException e)
		{
			throw new IllegalStateException(e);
		}
	}
	
    protected Connection createConnection()
    {
        try
        {
			Context initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(DATA_SOURCE_NAME); 
            return ds.getConnection();
        } 
        catch (NamingException e)
        {
            throw new IllegalStateException(e);
        }
        catch (SQLException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
