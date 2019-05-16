package org.se.lab.service;

import java.net.URI;
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
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/v1/users")
@Stateless
public class UserResourceEJB
{
	private final Logger LOG = Logger.getLogger(UserResourceEJB.class);
	
	public UserResourceEJB()
	{
		LOG.debug(UserResourceEJB.class.getName() + " created");
	}
	
	
	@POST
	@Consumes({"application/xml", "application/json"})
	public Response insert(UserDTO user)
	{
		LOG.debug("insert: " + user);

		return Response.created(URI.create("/users/" + user.getId())).build();
	}
	
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void update(@PathParam("id") int id, UserDTO user)
	{
		LOG.debug("update to " + user);

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
	public List<UserDTO> findAll()
	{
		LOG.debug("find all Users");
		
		List<UserDTO> result = new ArrayList<>();
		result.add(new UserDTO(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg="));
		result.add(new UserDTO(2, "marge", "xCSuPDv2U6I5jEO1wqvEQ/jPYhY="));
		result.add(new UserDTO(3, "bart", "Ls4jKY8G2ftFdy/bHTgIaRjID0Q="));
		result.add(new UserDTO(4, "lisa", "xO0U4gIN1F7bV7X7ovQN2TlSUF4="));
		return result;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public UserDTO findById(@PathParam("id") int id) 
		throws WebApplicationException
	{
		LOG.debug("find User with id=" + id);

		UserDTO user = new UserDTO(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg=");
		return user;
	}
}
