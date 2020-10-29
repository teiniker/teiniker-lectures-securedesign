package org.se.lab.data;

import java.util.List;


public interface ArticleDAO
{
	Article insert(Article user);
	Article update(Article user);
	void delete(Article user);
	
	Article findById(int id);
	List<Article> findAll();
	
	Article createArticle(String description, long price);
}
