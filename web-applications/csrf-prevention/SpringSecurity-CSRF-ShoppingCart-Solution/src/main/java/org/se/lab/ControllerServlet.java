package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
            	LOG.debug("> cart: " + cart);
            }
        }

        // generate response page
        forward(request, response, "/cart.jsp");	
	}
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException	
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
}
