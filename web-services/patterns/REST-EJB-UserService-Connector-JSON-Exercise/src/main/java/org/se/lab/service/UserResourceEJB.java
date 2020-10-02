package org.se.lab.service;

import java.net.URI;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

@Path("/v1/users")
@Stateless
public class UserResourceEJB
{
	private final Logger LOG = Logger.getLogger(UserResourceEJB.class);
	
	@Inject
	private UserDAO dao;
		
	public UserResourceEJB()
	{
		LOG.debug(UserResourceEJB.class.getName() + " created");
	}
	
	
	@POST
	@Consumes({"application/xml", "application/json"})
	public Response insert(UserDTO user)
	{
		LOG.debug("insert: " + user);

		User u = dao.createUser(user.getUsername(), user.getPassword());
		return Response.created(URI.create("/users/" + u.getId())).build();
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
		
		List<User> list = dao.findAll();
		List<UserDTO> result = UserDTO.toUserDTOList(list);
		return result;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public UserDTO findById(@PathParam("id") int id) 
		throws WebApplicationException
	{
		LOG.debug("find User with id=" + id);

		User user = dao.findById(id);

		if(user == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		else
			return new UserDTO(user);
	}
}
