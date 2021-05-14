package org.se.lab;

public class LoginViewHelper
{
	public String generateTranslation(String language, String word, String translatedWord)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Translator</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>");
        html.append("'").append(word).append("'").append(" is '").append(translatedWord).append("' in ");
        html.append(language);
        html.append("    </h2>");
        html.append("    </p>");
        html.append("    <a href=\"index.html\"> back</a>");
        html.append("  </body>");
        html.append("</html>");
        return html.toString();
	}


	public String generateUnknown(String language, String word)
	{
        StringBuilder html = new StringBuilder(); 
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Translator</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>Unknown translation for '").append(word).append("' in ").append(language).append(" </h2>");
        html.append("    </p>");
        html.append("    <a href=\"index.html\"> back</a>");
        html.append("  </body>");
        html.append("</html>");
        return html.toString();
	}

    public String generateNotSupportedLanguage(String language)
    {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("  <head>");
        html.append("    <title>Servlet Translator</title>");
        html.append("  </head>");
        html.append("  <body>");
        html.append("    <h2>The following language is not supported: ").append(language).append(" </h2>");
        html.append("    </p>");
        html.append("    <a href=\"index.html\"> back</a>");
        html.append("  </body>");
        html.append("</html>");
        return html.toString();
    }
}
