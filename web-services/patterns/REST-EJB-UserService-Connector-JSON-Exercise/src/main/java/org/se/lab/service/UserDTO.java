package org.se.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.se.lab.data.User;


@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO
{
	/*
	 * A default constructor is needed to make the JAXB marshaler happy.
	 */
	protected UserDTO()
	{
		this(0, "", "");
	}
	
	public UserDTO(int id, String username, String password)
	{
		setId(id);
		setUsername(username);
		setPassword(password);
	}
	
	
	/*
	 * Converter methods: User <=> UserDTO
	 */
	
	public UserDTO(User u)
	{
		this(u.getId(), u.getUsername(), u.getPassword());
	}
	
	public User toUser()
	{
		return new User(getId(), getUsername(), getPassword());
	}
	
	public static List<User> toUserList(List<UserDTO> dtoList)
	{
		if(dtoList == null)
			throw new IllegalArgumentException("Parameter List<UserDTO> is null!");
		
		List<User> result = new ArrayList<User>();
		for(UserDTO  dto : dtoList)
		{
			result.add(dto.toUser());
		}
		return result;
	}
	
	public static List<UserDTO> toUserDTOList(List<User> list)
	{
		List<UserDTO> result = new ArrayList<UserDTO>();
		for(User u : list)
		{
			result.add(new UserDTO(u));
		}
		return result;
	}
	
	
	/*
	 * Property: id:int
	 */
	@XmlAttribute
	private int id;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	
	/*
	 * Property: username:String
	 */
	@XmlElement
	private String username;
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
	/*
	 * Property: password:String
	 */
	@XmlElement
	private String password;
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
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
		UserDTO other = (UserDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
