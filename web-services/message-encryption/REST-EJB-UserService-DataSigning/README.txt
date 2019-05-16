
How to verify the signature within the DTO?
-------------------------------------------------------------------------------

If we include the signature as an additional element or attribute within a DTO,
we can verify it during the DTO <=> Entity transformation:

Example:
	public UserDTO(User u)
	{
		this(u.getId(), u.getUsername(), u.getPassword());
		setSign(MessageSignature.generateHMacString(u.toString()));
	}
	
	public User toUser()
	{
	    User u = new User(getId(), getUsername(), getPassword());
	    if(getSign().equals(MessageSignature.generateHMacString(u.toString())))
	        return u;
	    else
	        throw new IllegalStateException("Invalid MAC signature!");
	}

Note that we can implement the sign property as a calculated property (which is
recalculated every time we invoke getSign()). In that case we have to change the
JAXB annotations:

@XmlAccessorType(XmlAccessType.FIELD) => @XmlAccessorType(XmlAccessType.Property)

And we have to move the @XmlAttribute and @XmlElement annotation next to the 
getter methods (away from the private fields).


How to include the Signature as an HTTP header?
-------------------------------------------------------------------------------
To read the content of an HTTP header we can use the @HeaderParam annotation.

Example:
	public Response insert(UserDTO userDTO, @HeaderParam("Signature") String sig)
	
The benefit here is that we don't change the content of a REST request or response
and the used data types.

In this case, we would implement the check directly within the Remote Facade EJB.	



How to report a verification failure?
-------------------------------------------------------------------------------
Following the HTTP specification, we report a status code:

Example: 
	Response.status(Status.NOT_ACCEPTABLE).build(); // status => 406

Alternatively, we could also throw a specific exception which would be converted
into a corresponding status code.

Example:
	throw new BadRequestException(); // status => 400
	
Note that the Response type can also include the content entity!

Example:
	return Response.ok(dto).build();
	
		