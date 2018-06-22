package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String cartList = "";
        HttpSession session = request.getSession(false);
        
        String action = request.getParameter("action");
        if(action == null)
        {
        	// do nothing
        }
        else if(action.equals("Add"))
        {   
  
        	String name = request.getParameter("name");
        	String quantity = request.getParameter("quantity");
        	Product product = new Product(name, quantity);
        	LOG.debug("> add " + product);
            if(session != null)
            {
            	@SuppressWarnings("unchecked")
				List<Product> cart = (List<Product>)session.getAttribute("cart");
	            if(cart == null)
	            {
	            	cart = new ArrayList<Product>();
	            	session.setAttribute("cart", cart);
	            }
            	cart.add(product);
            	cartList= generateProductList(cart);
            	LOG.debug("> cart: " + cart);
            	message = "added: " + product.getQuantity() + " times " + product.getName() + " to the cart";
            }
        }

        // generate response page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String html = generateWebPage(message, cartList);        
        out.println(html);
        out.close();
	}

	
	private String generateWebPage(String message, String cartList)
	{
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		html.append("<html>\n");
		html.append("  <head>\n");
		html.append("  	<title>Simple Shopping Cart</title>\n");
		html.append("  </head>\n");
		html.append("  <body>\n");
				
		html.append("		<h2>Shopping Cart:</h2>\n");        
		html.append("		<form method=\"post\" action=\"controller\">");
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
		html.append("       <hr/>");
		html.append(cartList);
		html.append("       <hr/>");
		Date now = new Date();
		html.append("		<h6>" + now + "</h6>");
		html.append("	</body>");
		html.append("</html>");
		return html.toString();
	}
	
	private String generateProductList(List<Product> products)
	{
		StringBuilder html = new StringBuilder();
		
		html.append("    <ul>\n");
		for(Product p : products)
		{
			html.append("        <li>").append(p.getQuantity()).append(" x ").append(p.getName()).append("</li>\n");
		}
		html.append("    </ul>\n");
		return html.toString();		
	}
}
