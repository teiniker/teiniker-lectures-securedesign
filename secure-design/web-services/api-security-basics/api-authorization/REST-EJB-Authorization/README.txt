How add a new user to the Wildfly configuration?
-------------------------------------------------------------------------------

$ cd JBOSS_HOME/bin
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

How to use the resources from curl?
-------------------------------------------------------------------------------

$ curl -i -k -X GET https://localhost:8443/REST-EJB-Authorization/v1/products/1
HTTP/1.1 401 Unauthorized
Expires: 0
Connection: keep-alive
WWW-Authenticate: Basic realm="ApplicationRealm"
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
Content-Type: text/html;charset=UTF-8
Content-Length: 71
Date: Tue, 10 Nov 2020 20:30:23 GMT

<html><head><title>Error</title></head><body>Unauthorized</body></html>

$ curl -i -k -u student:student https://localhost:8443/REST-EJB-Authorization/v1/products/1
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/xml;charset=UTF-8
Content-Length: 144
Date: Tue, 10 Nov 2020 20:31:13 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<product id="103">
    <description>Design Patterns</description>
    <price>5280</price>
</product>

$ curl -i -k -u admin:admin https://localhost:8443/REST-EJB-Authorization/v1/products/1
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/xml;charset=UTF-8
Content-Length: 144
Date: Tue, 10 Nov 2020 20:32:04 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<product id="103">
    <description>Design Patterns</description>
    <price>5280</price>
</product>


$ curl -i -k https://localhost:8443/REST-EJB-Authorization/v1/users/1
HTTP/1.1 401 Unauthorized
Expires: 0
Connection: keep-alive
WWW-Authenticate: Basic realm="ApplicationRealm"
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
Content-Type: text/html;charset=UTF-8
Content-Length: 71
Date: Tue, 10 Nov 2020 20:00:05 GMT

$ curl -i -k -u student:student https://localhost:8443/REST-EJB-Authorization/v1/users/1
HTTP/1.1 403 Forbidden
Expires: 0
Connection: keep-alive
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
Content-Type: text/html;charset=UTF-8
Content-Length: 68
Date: Tue, 10 Nov 2020 20:24:01 GMT

$ curl -i -k -u admin:admin https://localhost:8443/REST-EJB-Authorization/v1/users/1
HTTP/1.1 200 OK
Expires: 0
Connection: keep-alive
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
Content-Type: application/xml;charset=UTF-8
Content-Length: 150
Date: Tue, 10 Nov 2020 20:40:09 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user id="1">
    <username>homer</username>
    <password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>
</user>


How to use the resources from SoapUI?
-------------------------------------------------------------------------------

URL: https://localhost:8443/REST-EJB-Authorization/v1/users
URL: https://localhost:8443/REST-EJB-Authorization/v1/products

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
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> 
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>admin resources</web-resource-name>
			<url-pattern>/v1/users</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> 
		<auth-constraint>
			<role-name>admin</role-name>
		</auth-constraint>
	</security-constraint>

	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>ApplicationRealm</realm-name>
	</login-config>
