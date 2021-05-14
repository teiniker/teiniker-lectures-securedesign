package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(ControllerServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		// Handling request
        String word = request.getParameter("word");
        String language = request.getParameter("language");
        String action = request.getParameter("action");
        LOG.info("POST request: " + action + "," + word + "," + language);

        // Input Validation

		// Process commands
        String html = null;
        if(action != null && action.equals("Translate"))
        {
        	html = translate(language, word);
        }
        else
        {
        	html = ""; // TODO: generate error page
        }
               
        // Generate response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(html);
        out.close();
	}

	
	/*
	 * Action handlers
	 */
	
	protected String translate(String language, String word)
	{
		LoginViewHelper helper = new LoginViewHelper();
		TranslatorService service = null;
		switch(language)
		{
			case "Deutsch":
				service = new TranslatorServiceGerman();
				break;

			case "Francais":
				service = new TranslatorServiceFrench();
				break;

			default:
				return helper.generateNotSupportedLanguage(language);
		}

		String translatedWord = service.translate(word);
		if(translatedWord.length() > 0)
		{
			return helper.generateTranslation(language, word, translatedWord);
		}
		else
		{
			return helper.generateUnknown(language, word);
		}
	}
}
