SpringSecurity: Access Control
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SpringSecurity-AccessControl/


How to define users and their roles?
-------------------------------------------------------------------------------
In spring-security.xml we specify the different users (note that it is also 
possible to read these data out of a database):

	<authentication-manager>
		<authentication-provider>
			<password-encoder hash="bcrypt"/>
			<user-service>
				<user name="student" password="$2a$10$rw4FZiFAGyZcwpQYFl0PA.B0WiY3dLoO2/5ypZ5VNF.sUyiYN8TZm" authorities="ROLE_USER" />
				<user name="bart" password="$2a$10$H3y5wnm0gJ2GdL5T5lEH6ev/IroK3bcXMPcGKlsXSFoPwM7.1ts5q" authorities="ROLE_USER" />
				<user name="lisa" password="$2a$10$KUloDm3rBTIFdvItsVZ0aezVPBi73Bq3tU9opT0eS6jbdPJ5Vi52i" authorities="ROLE_USER, ROLE_ADMIN" />
				<user name="burns" password="$2a$10$FpDXVTqXlriWLh3yjoPwO.8kFJDGsiaATcH923v4JY8PN6h98pdai" authorities="ROLE_ADMIN" />
			</user-service>
		</authentication-provider>
	</authentication-manager>
	
For each user, we store username and the password hash value (using bcrypt) as 
well as his roles.

Note that a user can be member of different roles, like "lisa".
	 

How to define access rules for pages within a Web application?
-------------------------------------------------------------------------------
In spring-security.xml we define:

	<http auto-config="true" use-expressions="true">
		<access-denied-handler error-page="/public/access-denied.html" />		
		<intercept-url pattern="/public/login.html" access="isAnonymous()"/>
		<intercept-url pattern="/user/**" access="hasAnyRole('USER','ADMIN')" requires-channel="https"/>
		<intercept-url pattern="/admin/**" access="hasRole('ADMIN') and hasIpAddress('127.0.0.1')" requires-channel="https"/>		
		...
	</http>

We put pages into sub-folders and specify the roles which are allowed to access 
these resources.

We use Spring EL expressions as an authorization mechanism. There are many
built-in expressions we can use:

	hasRole([role])	
		Returns true if the current principal has the specified role.
	
	hasAnyRole([role1,role2])	
		Returns true if the current principal has any of the supplied roles 
		(given as a comma-separated list of strings)
			
	permitAll	
		Always evaluates to true
	
	denyAll	
		Always evaluates to false
	
	isAnonymous()	
		Returns true if the current principal is an anonymous user
	
	isAuthenticated()	
		Returns true if the user is not anonymous
	
