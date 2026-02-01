package org.se.lab;

import java.util.List;

public interface ArticleServiceConnector
{
    void insert(Article article);
    void update(Article article);
    void delete(int id);
    Article findById(int id);
    List<Article> findAll();
}