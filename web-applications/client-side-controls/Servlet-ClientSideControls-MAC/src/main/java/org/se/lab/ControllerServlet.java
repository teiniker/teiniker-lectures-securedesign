package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
    private final static String KEY_PROPERTY = System.getProperty("hmac.key");
    private final static String EXPECTED_ROLE = "user";
    
    private final Logger LOG = Logger.getLogger(ControllerServlet.class);
	
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Handling request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String role = request.getParameter("role");
		String username = request.getParameter("username");
//        String password = request.getParameter("password");
        String action = request.getParameter("action");
        
        LOG.info("POST: process " + action);
        
        String html = null;
        if(action != null && action.equals("Add"))
        {
        	LOG.info("Add: "+ firstName + "," + lastName + "," + username);
        	
        	String expectedValue = encryptHiddenField(EXPECTED_ROLE);
        	if(expectedValue.equals(role))
        	{
        	    LOG.info("     "+ username + " is in role");
        	}
        	else
        	{
        	    LOG.info("     "+ username + " is NOT in role");
        	}
        	// TODO: implement some action
        }
               
        // Generate response 
        String hiddenValue = encryptHiddenField(EXPECTED_ROLE);
        html = generateUserForm(hiddenValue);        
        response.setContentType("text/html");
        response.setBufferSize(1024);
        PrintWriter out = response.getWriter();
        out.println(html.toString());
        out.close();
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	
	protected String encryptHiddenField(String value)
	{	    
		try 
		{
		    byte[] keyBytes;
			keyBytes = Hex.decodeHex(KEY_PROPERTY.toCharArray());
			Key key = new SecretKeySpec(keyBytes, "HmacSHA1");
			
			Mac hmac = Mac.getInstance("HmacSHA1");      
	        hmac.init(key);
	        hmac.update(value.getBytes("UTF-8"));
	        byte[] macBytes = hmac.doFinal();

	        return Hex.encodeHexString(macBytes);
		} 
		catch (DecoderException | NoSuchAlgorithmException | InvalidKeyException | IllegalStateException | UnsupportedEncodingException e) 
		{
		    throw new IllegalStateException("Can't encrypt value!", e);
		}
	}
	
	/*
	 * View Helper
	 */
	
	protected String generateUserForm(String role)
	{
		StringBuilder html = new StringBuilder();
		html.append("<html>\n");
		html.append("    <head>\n");
		html.append("    	<title>Servlet Client-Side Controls</title>\n");
		html.append("    </head>\n");
		
		html.append("    <body>\n");
		html.append("    	<h2>Create a new user:</h2>\n");

		html.append("    		<form method=\"POST\" action=\"controller\">\n");
		html.append("    	        <input type=\"hidden\" name=\"role\" value=\"").append(role).append("\"/>\n"); 
		html.append("    	    	<table border=\"1\">\n");
		html.append("    	        	<tr>\n");
		html.append("    	        		<th width=\"50\">Id</th>\n");
		html.append("    	            	<th width=\"150\">FirstName</th>\n");
		html.append("    	            	<th width=\"150\">LastName</th>\n");
		html.append("	                	<th width=\"150\">Username</th>\n");
		html.append("    	            	<th width=\"150\">Password</th>\n");   
		html.append("    	            	<th width=\"100\">Actions</th>\n"); 
		html.append("    	        	</tr>\n");
		html.append("    	        	<tr>\n");
		html.append("    	        		<td/>\n");
		html.append("    	            	<td><input type=\"text\" name=\"firstName\" maxlength=\"16\"/></td>\n");
		html.append("    	            	<td><input type=\"text\" name=\"lastName\" maxlength=\"16\"/></td>\n");
		html.append("    	            	<td><input type=\"text\" name=\"username\" maxlength=\"16\"/></td>\n");
		html.append("    	            	<td><input type=\"password\" name=\"password\" pattern=\"[A-Za-z0-9_!]{4,}\"/></td>\n");
		html.append("  						<td align=\"center\"><input type=\"submit\" name=\"action\" value=\"Add\" /></td>\n"); 
		html.append("    	        	</tr>\n");
		html.append("    	    	</table>\n");
		html.append("        </form>\n");	
		
		html.append("    </body>\n");
		html.append("</html>\n");
		
		return html.toString();
	}
}
