package org.se.lab;

public class ArticleNotFoundException extends RuntimeException
{
    ArticleNotFoundException(Long id)
    {
        super("Could not find article " + id);
    }
}