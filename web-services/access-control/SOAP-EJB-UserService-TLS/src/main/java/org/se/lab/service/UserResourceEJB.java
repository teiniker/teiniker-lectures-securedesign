package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

@WebService(name="UserService")
//@SOAPBinding(style=Style.RPC)
@SOAPBinding(style=Style.DOCUMENT) // default setting
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
	
	
	@WebMethod
	public User insert(UserDTO user)
	{
		LOG.debug("insert: " + user);

		User u = dao.createUser(user.getUsername(), user.getPassword());
		return u;
	}
	
	
	@WebMethod
	public void update(int id, UserDTO user)
	{
		LOG.debug("update to " + user);

		// TODO
	}

	
	@WebMethod
	public void delete(int id)
	{
		LOG.debug("delete: " + id);
		
		// TODO
	}
	
	
	@WebMethod
	public List<UserDTO> findAll(int index, int size)
	{
		LOG.debug("find all Users [from: " + index + " with size: " + size + "]");
		
		List<User> list = dao.findAll();
		List<UserDTO> result = UserDTO.toUserDTOList(list);
		LOG.info("size = " + result.size());
		return result;
	}
	
	
	@WebMethod
	public UserDTO findById(int id) 
	{
		LOG.debug("find User with id=" + id);

		User user = dao.findById(id);

		return new UserDTO(user);
	}
}
