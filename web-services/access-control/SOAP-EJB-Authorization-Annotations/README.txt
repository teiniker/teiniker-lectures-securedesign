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

	URL: http://localhost:8080/SOAP-EJB-Authorization-Annotations/UserService?wsdl
	Username: student
	Password: student
		
	URL: http://localhost:8080/SOAP-EJB-Authorization-Annotations/ProductService?wsdl
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
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<!-- turn off TLS (for testing only) 
		<user-data-constraint> 
			<transport-guarantee>CONFIDENTIAL</transport-guarantee> 
		</user-data-constraint> -->
		<auth-constraint>
			<role-name>admin</role-name>
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
	
	http://localhost:8080/SOAP-EJB-Authorization-Annotations/UserService
	
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
	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
	   <soapenv:Header/>
	   <soapenv:Body>
	      <ser:delete>
	         <arg0>1</arg0>
	      </ser:delete>
	   </soapenv:Body>
	</soapenv:Envelope>	
	
	student/student => HTTP/1.1 500 Internal Server Error
		Caused by: javax.ejb.EJBAccessException: WFLYEJB0364: 
			Invocation on method: public void org.se.lab.service.UserResourceEJB.delete(int) of bean: 
			UserResourceEJB is not allowed
	teacher/teacher => HTTP/1.1 200 OK
	
	Note that the Apache CXF Framework does not wrap the EJBAccessException into a propper
	response...
	
References:
https://docs.jboss.org/author/display/WFLY9/Webservices+reference+guide