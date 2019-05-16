How to test client permissions?
-------------------------------------------------------------------------------
Using SoapUI we can simulate different clients:

	user: http://localhost:8080/REST-EJB-Authorization/v1/products
		student	=> HTTP/1.1 200 OK
		admin	=> HTTP/1.1 200 OK

	admin: http://localhost:8080/REST-EJB-Authorization/v1/users
		student => HTTP/1.1 403 Forbidden
		admin   => HTTP/1.1 200 OK


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
        admin=207b6e0cc556d7084b5e2db7d822555c
        student=82364171bad00c2c933b216cac1001d4
	
	=> JBOSS_HOME/standalone/configuration/application-roles.properties
		admin=user,admin


How to use the resources from SoapUI?
-------------------------------------------------------------------------------

	URL: http://localhost:8080/REST-EJB-Authorization/v1/users
	URL: http://localhost:8080/REST-EJB-Authorization/v1/products
		
	Authorization : Basic	
	Username: student
	Password: student
	Authenticate pre-emptively
	
	
REST Application Configuration: web.xml
-------------------------------------------------------------------------------

	<security-role>
		<role-name>user</role-name>
	</security-role>

	<security-role>
		<role-name>admin</role-name>
	</security-role>
	
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>user resources</web-resource-name>
			<url-pattern>/v1/products</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> 
		-->
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>admin resources</web-resource-name>
			<url-pattern>/v1/users</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> 
		-->
		<auth-constraint>
			<role-name>admin</role-name>
		</auth-constraint>
	</security-constraint>

	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>ApplicationRealm</realm-name>
	</login-config>
		

