package org.se.lab.service;

import java.util.List;

import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

public class SearchServiceImpl implements SearchService
{	
	public SearchServiceImpl(UserDAO dao)
	{
		if(dao == null)
			throw new IllegalArgumentException("Invalid reference of type UserDAO!");
		this.dao = dao;
	}
	
	/*
	 * Dependency: --[1]-> UserDAO
	 */
	private UserDAO dao;
	

	@Override
	public List<User> search(String name)
	{
		List<User> list = dao.findByUsername(name);
		return list;
	}
}
