package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

class ArticleDAOImpl
	implements ArticleDAO
{
	private final Logger LOG = Logger.getLogger(ArticleDAOImpl.class);
		
	@PersistenceContext
	private EntityManager em;

	
	/*
	 * CRUD Operations
	 */	
	
	@Override
	public Article insert(Article article)
	{
		LOG.info("insert(" + article + ")");
		em.persist(article);
		return article;
	}

	@Override
	public Article update(Article article)
	{
		LOG.info("update(" + article + ")");
		return em.merge(article);
	}

	@Override
	public void delete(Article article)
	{
		LOG.info("delete(" + article + ")");
		em.remove(article);
	}

	@Override
	public Article findById(int id)
	{
		LOG.info("findById(" + id + ")");
		return em.find(Article.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> findAll()
	{
		LOG.info("findAll()");
		final String hql = "SELECT u FROM " + Article.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	
	/*
	 * Factory methods
	 */

	@Override
	public Article createArticle(String description, long price)
	{
		LOG.info("createArticle(\"" + description + "\"," + price +")");
		
		Article u = new Article();
		u.setDescription(description);
		u.setPrice(price);	
		insert(u);
		return u;
	}
}
