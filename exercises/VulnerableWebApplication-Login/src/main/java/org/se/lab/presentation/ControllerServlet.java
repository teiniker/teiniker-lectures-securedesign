package org.se.lab.presentation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.owasp.encoder.Encode;
import org.se.lab.presentation.commands.WebCommand;
import org.se.lab.util.Validator;


public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = -1L;
	private final static Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
        response.setContentType("text/html");
        String action = request.getParameter("action");        
        LOG.debug("doPost(\"" + action + "\")");
        if(Validator.isKnownAction(action))
        {           
            try
            {
                // Translate the incoming action parameter into the appropriate command
                WebCommand command = getCommand(action);
                command.init(getServletContext(), request, response);
                command.process();
            }
            catch(Exception e)
            {               
                String value = Encode.forHtmlContent(e.getMessage());
                LOG.error("Can't execute Web command!", e);
                request.setAttribute("message", value);     
                forward("/index.jsp", request, response);
            }           
        }
        else
        {
            LOG.error("Unknown action: " + action);
            request.setAttribute("message", "Unknown action!");     
            forward("/index.jsp", request, response);
        }
	}

		
    /*
     * This Command Factory Method uses the given string to instantiate a 
     * given WebCommand object.
     */
    protected WebCommand getCommand(String action) 
    		throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Class<?> result = null;
		final String commandClassName = 
			"org.se.lab.presentation.commands." + action + "Command";
		
		result = Class.forName(commandClassName);
		return (WebCommand) result.newInstance();
	}


    /**
     * We use the RequestDispatcher interface to forward requests to another page.
     */
    protected void forward(String page, HttpServletRequest request, HttpServletResponse response)
		throws ServletException,
		IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
}
