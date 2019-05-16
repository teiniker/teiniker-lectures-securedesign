
How to use the resources from SoapUI?
-------------------------------------------------------------------------------

	URL: http://localhost:8080/REST-EJB-Authorization-Annotations/v1/users
	URL: http://localhost:8080/REST-EJB-Authorization-Annotations/v1/products

	Authorization : Basic
	Username: admin
	Password: admin
	Authenticate pre-emptively


How to test client permissions?
-------------------------------------------------------------------------------
Using SoapUI we can simulate different clients accessing different resources:

	http://localhost:8080/REST-EJB-Authorization-Annotations/v1/users
	student	=> HTTP/1.1 403 Forbidden
	admin	=> HTTP/1.1 200 OK

	http://localhost:8080/REST-EJB-Authorization-Annotations/v1/users/1
	student	=> HTTP/1.1 200 OK
	admin	=> HTTP/1.1 200 OK

	homer	=> HTTP/1.1 401 Unauthorized


How add a new user to the Wildfly configuration?
-------------------------------------------------------------------------------

	cd JBOSS_HOME/bin
	$ ./add-user.sh
	- b (Application user)
	- username: admin
	- password: admin
	- roles: admin,user
	- new user used for AS to AS communication: no 		

	=> JBOSS_HOME/standalone/configuration/application-users.properties
		admin=de1d9228df96c2d1049a8381797e5da0
	
	=> JBOSS_HOME/standalone/configuration/application-roles.properties
		admin=admin,user
	
	
REST Application Configuration: web.xml
-------------------------------------------------------------------------------

	<context-param>
		<param-name>resteasy.role.based.security</param-name>
		<param-value>true</param-value>
	</context-param>
	
	<!-- Handle javax.ws.rs.FormiddenException -->
	<security-role>
		<role-name>*</role-name>
	</security-role>
	
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>annotated resources</web-resource-name>
			<url-pattern>/v1/*</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> 
		-->
		<auth-constraint>
			<role-name>*</role-name>
		</auth-constraint>
	</security-constraint>

	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>ApplicationRealm</realm-name>
	</login-config>


How to use Annotations for fine-grained authorization?	
-------------------------------------------------------------------------------

The JEE platform offers the possibility of using annotations for security checks:

	@RolesAllowed({"admin"})
	@GET
	@Produces({"application/xml", "application/json"})
	public List<UserDTO> findAll()
	{
		//...
	}
	
	@RolesAllowed({"user", "admin"})
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public UserDTO findById(@PathParam("id") int id) 
		throws WebApplicationException
	{
		//...
	}	

@RolesAllowed
The @RolesAllowed annotation can be applied at the method or class level.
With this annotation we can define a set of roles that are allowed to use
the annotated resource. As a parameter, we write all allowed roles.

@DenyAll
The DenyAll annotation allows us to define operations that cannot be invoked
regardless of whether the user is authenticated or the roles are related to
the user.

@PermitAll
When we use the @PermitAll annotation, we tell the container that the annotated 
resource (a method or all methods of a class) can be invoked by any user who
has been authenticated to the application.  
		

Programmatically implementation of fine-grained permissions.
-------------------------------------------------------------------------------
Within the operations of Web services, we can add an additional parameter to
the method. This allows access to the security context (without altering the
way clients invoke the method).

	@GET
	@Produces({"application/xml", "application/json"})
	public List<ProductDTO> findAll(@Context SecurityContext context)
	{
		LOG.info("isSecure()              = " + context.isSecure());
		LOG.info("isUserInRole(\"user\")  = " + context.isUserInRole("user"));
		LOG.info("isUserInRole(\"admin\") = " + context.isUserInRole("admin"));
		
		Principal principal = context.getUserPrincipal();		
		LOG.info("principal.getName()     = " + principal.getName());
		
		// ...
	}

isUserInRole()
The functionality of the method isUserInRole() is similar to the annotation 
@RolesAllowed, its goal is to perform a check in order to determine if a 
authenticated user belongs to a specified role.

getUserPrincipal()
The getUserPrincipal() method obtains the authenticated user. We can ask for 
information such as the username of the user.
This is useful in scenarios in which we want to generate audit trails.

isSecure()
The method isSecure() determines whether the invocation is being made through 
a secure communication like HTTPS.


Using SoapUI we can simulate different clients and analyze the output of these
methods:

	/REST-EJB-Authorization-Annotations/v1/products
	student =>
		 isSecure()             = false
		 isUserInRole("user")  	= true
		 isUserInRole("admin") 	= false
		 principal.getName()    = student	

	teacher =>
		 isSecure()             = false
		 isUserInRole("user")  	= true
		 isUserInRole("admin") 	= true
		 principal.getName()    = teacher
			 
		 