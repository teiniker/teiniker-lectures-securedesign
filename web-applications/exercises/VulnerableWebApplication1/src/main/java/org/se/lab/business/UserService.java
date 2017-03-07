package org.se.lab.business;

import java.util.List;

import org.se.lab.data.User;


public interface UserService
{
    void addUser(String firstName, String lastName, String username, String password);

    void removeUser(String idString);

    List<User> findUsers(String username);

    List<User> findAllUsers(); 

    boolean login(String username, String password);
}