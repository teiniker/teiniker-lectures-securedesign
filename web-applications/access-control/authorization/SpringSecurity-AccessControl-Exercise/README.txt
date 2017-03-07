SpringSecurity: Access Control
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SpringSecurity-AccessControl-Exercise/


How to define users and roles?
-------------------------------------------------------------------------------
To the spring-security.xml file, add:

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
	

How to define access control rules?
-------------------------------------------------------------------------------
To the existing <intercept-url> add some more of them:

	<http auto-config="true" use-expressions="true">
		<intercept-url pattern="/public/login.html" access="isAnonymous()"/>
		<intercept-url pattern="/user/**" access="hasAnyRole('USER','ADMIN')" requires-channel="https"/>
		<intercept-url pattern="/admin/**" access="hasRole('ADMIN') and hasIpAddress('127.0.0.1')" requires-channel="https"/>
		...
	</http>		
	
	
How to specify your own access denied page?
-------------------------------------------------------------------------------
Within the <http> element we can specify a corresponding Web page:
 
	<http auto-config="true" use-expressions="true">
		<access-denied-handler error-page="/public/access-denied.html" />		
		...
	</http>				
