How to access the REST resource?
-------------------------------------------------------------------------------
URL: http://localhost:8080/REST-EJB-UserService-UUID/v1/users


How to create a new user?
-------------------------------------------------------------------------------
<user>
	<username>burns</username><password>YDYg2ELvjAWIllkU7wbECt/lr6w=</password>
</user>


How to implement UUIDs for your service?
-------------------------------------------------------------------------------
1. Change the SQL script for creating the table:
	id VARCHAR(64) NOT NULL PRIMARY KEY,

2. Change the Entity class:
	@Id
	@Column(name="ID")
	private String id;
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		if(id == null)
			throw new IllegalArgumentException();
		this.id = id;
	}
	
3. Change the DAO factory method:
	@Override
	public User createUser(String username, String password)
	{
		User u = new User();
		u.setId(UUID.randomUUID().toString());
		u.setUsername(username);
		u.setPassword(password);		
		insert(u);
		return u;
	}	
			
4. Also we have to change all of the id parameters to String...	

