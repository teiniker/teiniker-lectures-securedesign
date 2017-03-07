package org.se.lab.service;

import java.util.List;

import org.se.lab.data.User;

public interface SearchService
{
	List<User> search(String name);
	
	// ...
}
