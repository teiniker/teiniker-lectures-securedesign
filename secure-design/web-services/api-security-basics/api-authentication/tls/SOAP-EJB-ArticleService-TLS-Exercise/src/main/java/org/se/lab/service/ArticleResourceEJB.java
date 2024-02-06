package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.se.lab.data.Article;
import org.se.lab.data.ArticleDAO;

@WebService(name="ArticleService")
@Stateless
public class ArticleResourceEJB
{
	private final Logger LOG = Logger.getLogger(ArticleResourceEJB.class);
	
	@Inject
	private ArticleDAO dao;
		
	public ArticleResourceEJB()
	{
		LOG.debug(ArticleResourceEJB.class.getName() + " created");
	}
	
	
	@WebMethod
	public void insert(ArticleDTO user)
	{
		LOG.debug("insert: " + user);

		dao.createArticle(user.getDescription(), user.getPrice());
	}
	
	
	@WebMethod
	public void update(ArticleDTO dto)
	{
		LOG.debug("update to " + dto);
		
		dao.update(dto.toArticle());
	}

	
	@WebMethod
	public void delete(ArticleDTO dto)
	{
		LOG.debug("delete: " + dto);
		
		dao.delete(dto.toArticle());
	}
	
	
	@WebMethod
	public List<ArticleDTO> findAll()
	{
		LOG.debug("findAll()");
		
		List<Article> list = dao.findAll();
		List<ArticleDTO> result = ArticleDTO.toArticleDTOList(list);
		LOG.info("size = " + result.size());
		return result;
	}
	
	
	@WebMethod
	public ArticleDTO findById(int id) 
	{
		LOG.debug("findById(" + id + ")");

		Article article = dao.findById(id);
		return new ArticleDTO(article);
	}
}
