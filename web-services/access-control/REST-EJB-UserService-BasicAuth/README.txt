How to configure Basic Authentication?
-------------------------------------------------------------------------------

How to setup the database?
-------------------------------------------------------------------------------
$ mysql -u student -p
  Enter password: student
MariaDB [(none)]> use testdb;

copy/paste SQL statements from src/main/resources/sql/


How to access the REST service?
-------------------------------------------------------------------------------

URL: https://localhost:8443/REST-EJB-UserService-BasicAuth/v1/users

curl -i -X GET http://localhost:8080/REST-EJB-UserService-BasicAuth/v1/users

$ curl -i -k -X GET https://localhost:8443/REST-EJB-UserService-BasicAuth/v1/users
HTTP/2 401
expires: 0
www-authenticate: Basic realm="ApplicationRealm"
cache-control: no-cache, no-store, must-revalidate
pragma: no-cache
content-type: text/html;charset=UTF-8
content-length: 71
date: Fri, 06 Nov 2020 09:45:32 GMT

<html><head><title>Error</title></head><body>Unauthorized</body></html>

$ curl -i -k --user student:student -X GET https://localhost:8443/REST-EJB-UserService-BasicAuth/v1/users
HTTP/2 200
expires: 0
cache-control: no-cache, no-store, must-revalidate
pragma: no-cache
content-type: application/xml;charset=UTF-8
content-length: 458
date: Fri, 06 Nov 2020 09:46:48 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<collection>
    <user id="1">
        <username>homer</username>
        <password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>
    </user>
    <user id="2">
        <username>marge</username>
        <password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password>
    </user>
    <user id="3">
        <username>bart</username>
        <password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
    </user>
    <user id="4">
        <username>lisa</username>
        <password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password>
    </user>
</collection>



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
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
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
