package org.se.lab.data;

import java.util.List;


public interface UserDAO
{
	User insert(User user);
	User update(User user);
	void delete(User user);
	
	User findById(int id);
	List<User> findAll();
	
	User createUser(String username, String password);
}
