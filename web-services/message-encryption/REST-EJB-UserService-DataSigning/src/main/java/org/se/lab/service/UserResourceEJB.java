package org.se.lab.service;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public Response insert(UserDTO userDTO, @HeaderParam("Signature") String sig)
	{
		LOG.info("insert: " + userDTO + " [" + sig + "]");
		try
		{
		    User u = userDTO.toUser();
		    User user = dao.createUser(u.getUsername(), u.getPassword());
		    return Response.created(URI.create("/users/" + user.getId())).build();
		}
		catch(IllegalStateException e)
		{
		    return Response.status(Status.NOT_ACCEPTABLE).build(); // status => 406
		}
	}
	
		
	@GET
	@Produces({"application/xml", "application/json"})
	public Response findAll()
	{
		LOG.debug("find all Users");
		
		List<User> list = dao.findAll();
		List<UserDTO> result = UserDTO.toUserDTOList(list);
		GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(result) {};
	    return Response.ok(entity).build();
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public Response findById(@PathParam("id") int id) 
	{
		LOG.debug("find User with id=" + id);

		User user = dao.findById(id);
		UserDTO dto = new UserDTO(user);
		
		if(user == null)
			throw new NotFoundException(); // => status 404
		
		return Response.ok(dto).build();
	}
}
