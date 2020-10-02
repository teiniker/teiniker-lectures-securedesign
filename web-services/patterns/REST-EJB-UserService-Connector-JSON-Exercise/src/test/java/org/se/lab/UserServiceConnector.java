package org.se.lab;

import java.util.List;

public interface UserServiceConnector
{
    void insert(User user);
    void update(User user);
    void delete(int id);
    User findById(int id);
    List<User> findAll();
}