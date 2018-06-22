package org.se.lab.data;

import java.util.List;

public interface UserDAO
{
    void insert(User user);
    void update(User user);    
    void delete(long id);
    User findById(long id);

    List<User> findByLastname(String name);
    User findByUsername(final String name); 
    List<User> findAll();
}