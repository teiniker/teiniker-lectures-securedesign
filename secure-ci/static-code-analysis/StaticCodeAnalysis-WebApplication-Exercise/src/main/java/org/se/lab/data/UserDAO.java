package org.se.lab.data;

import java.util.List;

public interface UserDAO
{
    boolean isValidUser(String username, String password);

    //...
}