package org.se.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService(name="ProductService")
@Stateless
public class ProductResourceEJB
{
	private final Logger LOG = Logger.getLogger(ProductResourceEJB.class);
	
	public ProductResourceEJB()
	{
		LOG.debug(ProductResourceEJB.class.getName() + " created");
	}
	
	
	@WebMethod	
	public void insert(ProductDTO product)
	{
		LOG.debug("insert: " + product);

	}
	
	
	@WebMethod
	public void update(int id, ProductDTO product)
	{
		LOG.debug("update to " + product);

		// TODO
	}

	
	@WebMethod
	public void delete(int id)
	{
		LOG.debug("delete: " + id);
		
		// TODO
	}
	
	
	@WebMethod
	public List<ProductDTO> findAll()
	{
		LOG.debug("find all Products");
		
		List<ProductDTO> result = new ArrayList<>();
		result.add(new ProductDTO(102, "Effective Java", 3336));
		result.add(new ProductDTO(103, "Design Patterns", 5280));
		return result;
	}
	
	
	@WebMethod
	public ProductDTO findById(int id) 
	{
		LOG.debug("find Product with id=" + id);

		ProductDTO product = new ProductDTO(103, "Design Patterns", 5280);
		return product;
	}
}
