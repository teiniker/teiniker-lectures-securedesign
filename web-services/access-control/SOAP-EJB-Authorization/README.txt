How add a new user to the Wildfly configuration?
-------------------------------------------------------------------------------

	cd JBOSS_HOME/bin
	$ ./add-user.sh
	- b (Application user)
	- username: teacher
	- password: teacher
	- roles: admin,user
	- new user used for AS to AS communication: no 		

	=> JBOSS_HOME/standalone/configuration/application-users.properties
		teacher=de1d9228df96c2d1049a8381797e5da0
	
	=> JBOSS_HOME/standalone/configuration/application-roles.properties
		teacher=admin,user


How to use the services from SoapUI?
-------------------------------------------------------------------------------

	URL: http://localhost:8080/SOAP-EJB-Authorization/UserService?wsdl
	Username: teacher
	Password: teacher
		
	URL: http://localhost:8080/SOAP-EJB-Authorization/ProductService?wsdl
	Username: student
	Password: student
	
	
SOAP application configuration: web.xml
-------------------------------------------------------------------------------
	
	<security-role>
		<role-name>user</role-name>
	</security-role>

	<security-role>
		<role-name>admin</role-name>
	</security-role>

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>admin service</web-resource-name>
			<url-pattern>/UserService/*</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> -->
		<auth-constraint>
			<role-name>admin</role-name>
		</auth-constraint>
	</security-constraint>

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>user service</web-resource-name>
			<url-pattern>/ProductService/*</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> -->
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>
	
	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>ApplicationRealm</realm-name>
	</login-config>
	
		
How to test client permissions?
-------------------------------------------------------------------------------
Using SoapUI we can simulate different clients:
	
	http://localhost:8080/SOAP-EJB-Authorization/ProductService
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
	   <soapenv:Header/>
	   <soapenv:Body>
	      <ser:findById>
	         <arg0>1</arg0>
	      </ser:findById>
	   </soapenv:Body>
	</soapenv:Envelope>

	student/student => HTTP/1.1 200 OK
	teacher/teacher => HTTP/1.1 200 OK
	
	
	http://localhost:8080/SOAP-EJB-Authorization/UserService
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
	   <soapenv:Header/>
	   <soapenv:Body>
	      <ser:findById>
	         <arg0>1</arg0>
	      </ser:findById>
	   </soapenv:Body>
	</soapenv:Envelope>
	
	student/student	=> HTTP/1.1 403 Forbidden
	teacher/teacher	=> HTTP/1.1 200 OK
	
Note that we can use the same web.xml based authorization mechanism which is
specified in JAAS.
	