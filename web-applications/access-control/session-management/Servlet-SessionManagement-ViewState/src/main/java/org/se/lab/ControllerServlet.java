package org.se.lab;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private final Logger LOG = Logger.getLogger(ControllerServlet.class);  
	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
        // Controller 
		String message = "";
		String state = "";
        
        String action = request.getParameter("action");
        if(action == null)
        {
        	// do nothing
        }
        else if(action.equals("Login"))
        {
        	LOG.info("> login");       	
        	state = listToString(new ArrayList<Product>());		
        	message = "You logged in successfully!";
        }
        else if(action.equals("Logout"))
        {
        	LOG.info("> logout");
        	state = "";	
        	message = "You logged out successfully!";
        }
        else if(action.equals("Add"))
        {   
        	String name = request.getParameter("name");
        	String quantity = request.getParameter("quantity");
        	ArrayList<Product> cart = listFromString(request.getParameter("state"));
        	
        	Product product = new Product(name, quantity);
        	LOG.info("> add " + product);
        	cart.add(product);
        	state = listToString(cart);
        	message = "added: " + product.getQuantity() + " " + product.getName() + " to the cart";
	        LOG.info("> cart: " + cart);
        }

        // generate response page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String html = generateWebPage(state, message);        
        out.println(html);
        out.close();
	}

	
	private String generateWebPage(String state, String message)
	{
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		html.append("<html>\n");
		html.append("  <head>\n");
		html.append("  	<title>Simple Shopping Cart</title>\n");
		html.append("  </head>\n");
		html.append("  <body>\n");
		
		html.append("		<h2>Session Management</h2>\n");        
		html.append("		<form method=\"post\" action=\"controller\">");
		html.append("    	    <input type=\"hidden\" name=\"state\" value=\"" + state + "\"/>\n");
		html.append("			<table border=\"0\" cellspacing=\"1\" cellpadding=\"5\">");
		html.append("				<colgroup>");
		html.append("					<col width=\"150\"> <col width=\"150\"> <col width=\"100\">");
		html.append("				</colgroup>");
		html.append("				<tr>");
		html.append("					<th>");
		html.append("						<input type=\"submit\"  name=\"action\" value=\"Login\">");  
		html.append("					</th>");
		html.append("					<th>");
		html.append("					</th>");
		html.append("					<th>");  
		html.append("						<input type=\"submit\" name=\"action\" value=\"Logout\">");
		html.append("					</th>");
		html.append("				</tr>");	
		html.append("			</table>");
		html.append("		</form>");
		html.append("		<p/>");
		
		html.append("		<h2>Your Shopping Cart:</h2>\n");        
		html.append("		<form method=\"post\" action=\"controller\">");
		html.append("    	    <input type=\"hidden\" name=\"state\" value=\"" + state + "\"/>\n");
		html.append("			<table border=\"0\" cellspacing=\"1\" cellpadding=\"5\">");
		html.append("				<colgroup>");
		html.append("					<col width=\"150\"> <col width=\"150\"> <col width=\"100\">");
		html.append("				</colgroup>");
		html.append("				<tr>");
		html.append("					<th>Product</th>");
		html.append("					<th>Quantity</th>");
		html.append("					<th></th>");
		html.append("				</tr>");
		html.append("				<tr>");
		html.append("					<td><input type = \"text\" name = \"name\" /></td>");
		html.append("					<td><input type = \"text\" name = \"quantity\" /></td>");
		html.append("					<td><input type = \"submit\" name = \"action\" value = \"Add\" /></td>");
		html.append("				</tr>");	
		html.append("			</table>");
		html.append("		</form>");
		html.append("		<p/>");
		
		html.append("		<p style=\"color:blue\"><i>" + message + "</i></p>");
		html.append("		<p/>");
		Date now = new Date();
		html.append("		<h6>" + now + "</h6>");
		return html.toString();
	}
	
	private String listToString(ArrayList<Product> list)
	{
		LOG.debug("listToString() " + list);
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oos= new ObjectOutputStream(bout);
			oos.writeObject(list);
			oos.close();
			
			byte[] bytes = bout.toByteArray();
			return Base64.encodeBase64String(bytes);
		}
		catch (IOException e)
		{
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Product> listFromString(String base64String)
	{
		LOG.debug("listFromString() " + base64String); 
		try
		{
			byte[] bytes = Base64.decodeBase64(base64String);		
			ArrayList<Product> list = new ArrayList<Product>(); 
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			ObjectInputStream ois;
			ois = new ObjectInputStream(bin);
			list = (ArrayList<Product>) ois.readObject();
			ois.close();
			return list;
		}
		catch (IOException | ClassNotFoundException e)
		{
			return new ArrayList<Product>();
		}
	}
}
