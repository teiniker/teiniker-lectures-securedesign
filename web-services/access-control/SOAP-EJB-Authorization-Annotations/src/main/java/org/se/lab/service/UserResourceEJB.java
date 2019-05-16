package org.se.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService(name="UserService")
@Stateless
public class UserResourceEJB
{
	private final Logger LOG = Logger.getLogger(UserResourceEJB.class);
	
	public UserResourceEJB()
	{
		LOG.info(UserResourceEJB.class.getName() + " created");
	}
	
	@RolesAllowed({"admin"})
	@WebMethod
	public void insert(UserDTO user)
	{
		LOG.info("insert: " + user);

	}
	
	@RolesAllowed({"admin"})
	@WebMethod
	public void update(int id, UserDTO user)
	{
		LOG.info("update to " + user);

		// TODO
	}

	@RolesAllowed({"admin"})
	@WebMethod
	public void delete(int id)
	{
		LOG.info("delete: " + id);
		
		// TODO
	}
	
	@RolesAllowed({"user", "admin"})
	@WebMethod
	public List<UserDTO> findAll(int index, int size)
	{
		LOG.info("find all Users [from: " + index + " with size: " + size + "]");
		
		List<UserDTO> result = new ArrayList<>();
		result.add(new UserDTO(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg="));
		result.add(new UserDTO(2, "marge", "xCSuPDv2U6I5jEO1wqvEQ/jPYhY="));
		result.add(new UserDTO(2, "bart", "Ls4jKY8G2ftFdy/bHTgIaRjID0Q="));
		result.add(new UserDTO(2, "lisa", "xO0U4gIN1F7bV7X7ovQN2TlSUF4="));
		
		return result;
	}
	
	@RolesAllowed({"user", "admin"})
	@WebMethod
	public UserDTO findById(int id) 
	{
		LOG.info("find User with id=" + id);

		UserDTO user = new UserDTO(1, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg=");
		return user;
	}
}
