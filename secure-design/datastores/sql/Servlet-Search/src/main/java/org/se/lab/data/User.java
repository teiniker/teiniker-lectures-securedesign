package org.se.lab.data;



public class User
{
	/*
	 * Constructors
	 */
	public User(final long id, final String firstName, final String lastName, 
			final String username, final String password)
	{
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
	}

	public User(final String firstName, final String lastName, 
			final String username, final String password)
	{
		this(0, firstName, lastName, username, password);
	}

	
	/*
	 * Property: id:long
	 */
	private long id;
	public final long getId()
	{
		return id;
	}

	public final void setId(final long id)
	{
		this.id = id;
	}

	
	/*
	 * Property: firstName:String
	 */
	private String firstName;
	public final String getFirstName()
	{
		return firstName;
	}
	public final void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	
	/*
	 * Property: lastName:String
	 */
	private String lastName;
	public final String getLastName()
	{
		return lastName;
	}
	public final void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	
	/*
	 * Property: username:String
	 */
	private String username;
	public final String getUsername()
	{
		return username;
	}
	public final void setUsername(final String username)
	{
		this.username = username;
	}

	
	/*
	 * Property: password:String
	 */
	private String password;
	public final String getPassword()
	{
		return password;
	}
	public final void setPassword(final String password)
	{
		this.password = password;
	}

	
	
	/*
	 * Object methods
	 */

	@Override
	public String toString()
	{
		return id + "," + firstName + "," + lastName + "," + username;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
