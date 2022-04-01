package org.se.lab.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
    private Validator() {}
    
    private final static Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-_]{4,255}$");
    public static void checkUsername(String username)
    {
        if(username == null)
            throw new IllegalArgumentException("Username is null!");
        
        String s = Normalizer.normalize(username, Form.NFKC);
        Matcher m = USERNAME_PATTERN.matcher(s);
        if(!m.matches())
            throw new IllegalArgumentException("Invalid username format!");
    }
    
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9.-_!$%&]{7,}$");
    public static void checkPassword(String password)
    {
        if(password == null)
            throw new IllegalArgumentException("Password is null!");
        
        String s = Normalizer.normalize(password, Form.NFKC);
        Matcher m = PASSWORD_PATTERN.matcher(s);
        if(!m.matches())
            throw new IllegalArgumentException("Invalid password format!");
        
        // Some more password policy checks can be implemented...
    }
    
    private final static List<String> KNOWN_ACTIONS = Arrays.asList("Login");
    public static boolean isKnownAction(String action)
    {
        if(action == null)
            return false;
        
        return KNOWN_ACTIONS.contains(action);
    }
}
