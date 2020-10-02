package org.se.lab.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

class UserDAOImpl // package private
	implements UserDAO
{
	@PersistenceContext
	private EntityManager em;

	
	/*
	 * CRUD methods
	 */	
	
	@Override
	public User insert(User entity)
	{
		em.persist(entity);
		return entity;
	}

	@Override
	public User update(User entity)
	{
		return em.merge(entity);
	}

	@Override
	public void delete(User entity)
	{
		em.remove(entity);
	}

	@Override
	public User findById(int id)
	{
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll()
	{
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	
	/*
	 * Factory methods
	 */

	@Override
	public User createUser(String username, String password)
	{
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);		
		insert(u);
		return u;
	}
}
