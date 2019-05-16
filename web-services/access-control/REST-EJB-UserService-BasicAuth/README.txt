How to configure Basic Authentication?
-------------------------------------------------------------------------------

How to access the REST service?
-------------------------------------------------------------------------------

URL: http://localhost:8080/REST-EJB-UserService-BasicAuth/v1/users


How to use Authentication in SoapUI?
-------------------------------------------------------------------------------
- Click the [Auth] bottom of the request form.
- Add Authentication / Basic
- Enter username and password
- check Authenticate pre-emptively


Wildfly AS Configuration
-------------------------------------------------------------------------------
	see: https://docs.jboss.org/author/display/WFLY8/Security+Realms

	cd JBOSS_HOME/bin
	$ ./add-user.sh
	- Application user
	- username: student
	- password: student
	- roles: user
	- new user used for AS to AS communication: no 		

	=> JBOSS_HOME/standalone/configuration/application-users.properties
		student=82364171bad00c2c933b216cac1001d4
	
	=> JBOSS_HOME/standalone/configuration/application-roles.properties
		student=user
		
	standalone.xml // already configured by default
	<management>
        <security-realms>
            <security-realm name="ApplicationRealm">
                <authentication>
                    <local default-user="$local" allowed-users="*" skip-group-loading="true"/>
                    <properties path="application-users.properties" relative-to="jboss.server.config.dir"/>
                </authentication>
                <authorization>
                    <properties path="application-roles.properties" relative-to="jboss.server.config.dir"/>
                </authorization>
            </security-realm>
        </security-realms>			
		...
	</management>

	
*) REST Application Configuration: web.xml

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>All resources</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>
	
	<security-role>
		<role-name>user</role-name>
	</security-role>

	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>ApplicationRealm</realm-name>
	</login-config>
	

How to use Basic Authentication?
-------------------------------------------------------------------------------
see RFC 2617 HTTP Authentication

The client sends the userid and password, separated by a single colon (":") 
character, within a base64 encoded string in the credentials.

Example: 
	base64(student:student) = c3R1ZGVudDpzdHVkZW50

	HTTP Header:
		Authorization: Basic c3R1ZGVudDpzdHVkZW50
	

	HTTP Request:
		GET /REST-EJB-UserService-BasicAuth/v1/users/3 HTTP/1.1
		Authorization: Basic c3R1ZGVudDpzdHVkZW50
		Accept: application/xml
		User-Agent: Java/1.8.0_60
		Host: localhost:8080
 	       
 	       
	HTTP Response: (right credentials)
		HTTP/1.1 200 OK
		Expires: 0
		Cache-Control: no-cache, no-store, must-revalidate
		X-Powered-By: Undertow/1
		Server: WildFly/9
		Pragma: no-cache
		Date: Tue, 10 Nov 2015 15:23:33 GMT
		Connection: keep-alive
		Content-Type: application/xml
		Content-Length: 149
		
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<user id="3">
			<username>bart</username>
			<password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
		</user> 	       
 	       
	HTTP Response: (wrong password)
		HTTP/1.1 401 Unauthorized
		Expires: 0
		Cache-Control: no-cache, no-store, must-revalidate
		X-Powered-By: Undertow/1
		Server: WildFly/9
		Pragma: no-cache
		Date: Tue, 10 Nov 2015 15:23:34 GMT
		Connection: keep-alive
		WWW-Authenticate: Basic realm="ApplicationRealm"
		Content-Type: text/html;charset=UTF-8
		Content-Length: 71
		
		<html><head><title>Error</title></head><body>Unauthorized</body></html>
