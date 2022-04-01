package org.se.lab;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter("/*")
public class CSRFFilter
        implements Filter
{
    private final Logger LOG = Logger.getLogger(CSRFFilter.class);
    private String CSRF_TOKEN;

    public void init(FilterConfig fConfig) throws ServletException
    {
        // Generate a new CSRF token with every init of the filter
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        CSRF_TOKEN = Hex.encodeHexString(bytes);
        LOG.info("Init CSRF_TOKEN: " + CSRF_TOKEN);
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        LOG.debug("doFilter:");
        HttpServletRequest in = (HttpServletRequest) request;

        // Read CSRF-Token from request
        String token = request.getParameter("csrf_token");

        // Read Session
        HttpSession session = in.getSession(false);

        if (session != null)
        {
            // Valid session
            LOG.info("Request CRSF_TOKEN = " + token);
            LOG.info("        CRSF_TOKEN = " + CSRF_TOKEN);

            if(!CSRF_TOKEN.equals(token))
            {
                // Invalid CSRF token
                String content = generateErrorPage();
                PrintWriter writer = response.getWriter();
                writer.println(content);
                writer.close();
                return;
            }
        }

        // Delegate to the Web application
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrapper);

        // Write CSRF-Token to response
        String content = wrapper.toString().replaceAll("CSRF_TOKEN", CSRF_TOKEN);
        PrintWriter writer = response.getWriter();
        writer.println(content);
        writer.close();
    }

    private String generateErrorPage()
    {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
        html.append("<html>\n");
        html.append("  <head>\n");
        html.append("  	<title>CSRF Filter</title>\n");
        html.append("  </head>\n");
        html.append("  <body>\n");
        html.append("		<h2>Rejected request!!</h2>\n");
        html.append("  </body>\n");
        html.append("</html>\n");
        return html.toString();
    }
}