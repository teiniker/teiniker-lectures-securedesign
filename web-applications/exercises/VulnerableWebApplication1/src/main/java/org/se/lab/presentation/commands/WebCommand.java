package org.se.lab.presentation.commands;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.se.lab.business.Factory;


public abstract class WebCommand
{
	protected ServletContext ctx;
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	
	protected final Factory factory = new Factory();
	
	
	public void init(final ServletContext ctx, final HttpServletRequest req, final HttpServletResponse res)
	{
	    if(ctx == null || req == null || res == null)
	        throw new NullPointerException();
	    this.ctx = ctx;
		this.req = req;
		this.res = res;
	}

	
	protected void forward(final String page)
		throws ServletException, IOException	
	{
		RequestDispatcher dispatcher = ctx.getRequestDispatcher(page);
		dispatcher.forward(req, res);
	}
	
	
	public abstract void process()
		throws ServletException, IOException;
}
