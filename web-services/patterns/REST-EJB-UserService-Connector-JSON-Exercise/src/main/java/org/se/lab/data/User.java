package org.se.lab.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	public User(int id, String username, String password)
	{		
		setId(id);
		setUsername(username);
		setPassword(password);
	}
	
	protected User()
	{
	}

	
	@Id
	@Column(name="ID")
	private int id;
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		if(id < 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	
	@Column(name="PASSWORD")
	private String password;	
	public String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		if(password == null)
			throw new IllegalArgumentException();
		this.password = password;
	}

	
	@Column(name="USERNAME")	
	private String username;
	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		if(username == null)
			throw new IllegalArgumentException();
		this.username = username;
	}

	
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return getId() + "," + getUsername() + "," + getPassword();
	}
		
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}