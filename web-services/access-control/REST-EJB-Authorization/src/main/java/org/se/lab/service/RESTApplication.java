package org.se.lab.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/*")
public class RESTApplication 
	extends Application
{
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public RESTApplication()
	{
		classes.add(UserResourceEJB.class);
		classes.add(ProductResourceEJB.class);
	}

	@Override
	public Set<Class<?>> getClasses()
	{
		return classes;
	}

	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
