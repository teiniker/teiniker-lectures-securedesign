package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.se.lab.data.Article;
import org.se.lab.data.ArticleDAO;

@Path("/v1/articles")
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
	
	
	@POST
	@Consumes({"application/xml", "application/json"})
	public void insert(ArticleDTO user)
	{
		LOG.debug("insert: " + user);
		dao.createArticle(user.getDescription(), user.getPrice());
	}
	
	
	@PUT
	@Consumes("application/xml")
	public void update(ArticleDTO dto)
	{
		LOG.debug("update to " + dto);
		dao.update(dto.toArticle());
	}

	
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id)
	{
		LOG.debug("delete: " + id);
		
		// TODO
	}
	
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<ArticleDTO> findAll()
	{
		LOG.debug("findAll()");
		
		List<Article> list = dao.findAll();
		List<ArticleDTO> result = ArticleDTO.toArticleDTOList(list);
		LOG.info("size = " + result.size());
		return result;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public ArticleDTO findById(@PathParam("id") int id) 
	{
		LOG.debug("findById(" + id + ")");
		Article article = dao.findById(id);
		return new ArticleDTO(article);
	}
}
