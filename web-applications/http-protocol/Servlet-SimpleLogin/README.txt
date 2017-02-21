Servlet: Simple Login
-------------------------------------------------------------------------------


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-SimpleLogin/


How to access the Web application from cURL?
-------------------------------------------------------------------------------

$ curl -i -X GET http://localhost:8080/Servlet-SimpleLogin/

$ curl --proxy localhost:8010 -i -X POST -H 'Content-Type: application/x-www-form-urlencoded' --data-binary $'username=student&password=student&usergroup=Guest&action=Login' http://localhost:8080/Servlet-SimpleLogin/controller

$ curl -i -X GET "http://localhost:8080/Servlet-SimpleLogin/controller?username=student&password=student&usergroup=Guest&action=Login"
    
    
				
How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
	