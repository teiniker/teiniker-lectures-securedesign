package org.se.lab.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.se.lab.data.Article;
import org.se.lab.service.ArticleService;

@Named
@RequestScoped
public class DataBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Logger LOG = Logger.getLogger(DataBean.class);
	
	@Inject
	private ArticleService service;
	
	
	/*
	 * Property: users
	 */
	private List<Article> articles;
	public List<Article> getArticles()
	{
		return articles;
	}
	public void setArticles(List<Article> articles)
	{
		this.articles = articles;
	}


	/*
	 * Actions
	 */
	
	public String refresh()
	{
		LOG.info("update");		
		articles = service.findAll();
		LOG.info(articles);
		
		return "";
	}
}
