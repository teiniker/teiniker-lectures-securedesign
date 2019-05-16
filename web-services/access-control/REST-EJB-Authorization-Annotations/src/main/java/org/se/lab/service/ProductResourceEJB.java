package org.se.lab.service;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

@Path("/v1/products")
@Stateless
public class ProductResourceEJB
{
	private final Logger LOG = Logger.getLogger(ProductResourceEJB.class);
	
	public ProductResourceEJB()
	{
		LOG.debug(ProductResourceEJB.class.getName() + " created");
	}
	
	
	@POST
	@Consumes({"application/xml", "application/json"})
	public Response insert(ProductDTO product)
	{
		LOG.debug("insert: " + product);

		return Response.created(URI.create("/products/" + product.getId())).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void update(@PathParam("id") int id, ProductDTO product)
	{
		LOG.debug("update to " + product);

		// TODO
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
	public List<ProductDTO> findAll(@Context SecurityContext context)
	{
		LOG.debug("find all Products");
		
		LOG.info("isSecure()              = " + context.isSecure());
		LOG.info("isUserInRole(\"user\")  = " + context.isUserInRole("user"));
		LOG.info("isUserInRole(\"admin\") = " + context.isUserInRole("admin"));
		
		Principal principal = context.getUserPrincipal();		
		LOG.info("principal.getName()     = " + principal.getName());
		
		List<ProductDTO> result = new ArrayList<>();
		result.add(new ProductDTO(102, "Effective Java", 3336));
		result.add(new ProductDTO(103, "Design Patterns", 5280));
		return result;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public ProductDTO findById(@PathParam("id") int id) 
		throws WebApplicationException
	{
		LOG.debug("find Product with id=" + id);

		ProductDTO product = new ProductDTO(103, "Design Patterns", 5280);
		return product;
	}
}
