package org.se.lab.data;

import java.util.List;

public interface UserDAO
{
    User findById(String id);

    List<User> findByUsername(String name);

    List<User> findAll();
}