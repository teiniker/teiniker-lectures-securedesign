package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.Article;
import org.se.lab.data.ArticleDAO;

@Stateless
public class ArticleService
{
	private final Logger LOG = Logger.getLogger(ArticleService.class);
	
	@Inject
	private ArticleDAO dao;
	
	/*
	 * API Operations
	 */

	public void insert(Article article)
	{
		LOG.info("insert: " + article);

		try
		{
			dao.insert(article);
		}
		catch(Exception e)
		{
			LOG.error("Can't insert article " + article, e);
			throw new ServiceException("Can't insert article " + article);
		}
	}
	
	
	public void update(Article article)
	{
		LOG.info("update to " + article);

		// TODO
	}

	
	public void delete(int id)
	{
		LOG.info("delete: " + id);
		
		// TODO
	}
	
	
	public List<Article> findAll()
	{
		LOG.info("find all articles");

		try
		{
			List<Article> list = dao.findAll();
			return list;
		}
		catch(Exception e)
		{
			LOG.error("Can't find all articles!", e);
			throw new ServiceException("Can't find all articles!");
		}
	}
	
	
	public Article findById(int id) 
	{
		LOG.info("find User with id=" + id);

		try
		{
			Article article = dao.findById(id);
			return article;
		}
		catch(Exception e)
		{
			LOG.error("Can't find article with id " + id, e);
			throw new ServiceException("Can't find article with id " + id);
		}
	}
}
